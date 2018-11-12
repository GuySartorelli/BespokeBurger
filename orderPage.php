<!DOCTYPE html>

<?php

	// <!--create socket connect-->
	$host    = "127.0.0.1";
	$port    = 9090;
	$socket = socket_create(AF_INET, SOCK_STREAM, 0);
	$result = socket_connect($socket, $host, $port);
	if ($result === true) {
    	$message1 = "RGSTR,WEB\r\n";
    	socket_write($socket, $message1, strlen($message1)) or die("Could not send data to server\n");
    	//<!--request categories & ingredients, call getIngredients and parse into array(5), for each item check quantity greater than 0 and display if so-->
    
    
    	$message2 = "REQ_CAT\r\n";
    	socket_write($socket, $message2, strlen($message2)) or die("Could not send data to server\n");
    	$categoriesRaw = socket_read ($socket, 1024, PHP_NORMAL_READ) or die("Could not read server response\n");
    	$categoriesRaw = str_replace("SEND_CAT,", "", $categoriesRaw);
    	$categories = explode(",", $categoriesRaw);
    
    
    	$message3 = "REQ_INGR\r\n";
    	socket_write($socket, $message3, strlen($message3)) or die("Could not send data to server\n");
    	$ingredientsRaw = socket_read ($socket, 1024, PHP_NORMAL_READ) or die("Could not read server response\n");
    	$ingredientsRaw = str_replace("SEND_INGR,", "", $ingredientsRaw);
    	$ingredients1D = explode(",", "$ingredientsRaw");
    	
    	//parse ingredients string to multidimensional associative array
    	$ingredients = array();
    	$category = "NONE";
    	$ingredient = "NONE";
    	for ($i = 0; $i < sizeof($ingredients1D); $i++){
    		switch ($i % 5) {
    		    case 0:
        	        $category = $ingredients1D[$i];
        	        $ingredients[$category] = array();
        	        break;
            	case 1:
            	    $ingredient = str_replace(" ", "_", $ingredients1D[$i]);
            	    $ingredients[$category][$ingredient] = array();
            	    $ingredients[$category][$ingredient]["name"] = $ingredient;
                	break;
        		case 2:
        		    $ingredients[$category][$ingredient]["price"] = doubleval($ingredients1D[$i]);
        			break;
        		case 3:
        		    $ingredients[$category][$ingredient]["quantity"] = intval($ingredients1D[$i]);
        			break;
    		}
    	}
    	
    	//send ingredients to JS
    	echo "<script>";
    	   echo"var ingredients = "; echo json_encode($ingredients, JSON_HEX_TAG);
    	echo "</script>";
    
    
    	//<!--DRGSTR and close the socket. -->
    	$message3 = "DERGSTR\r\n";
    	socket_write($socket, $message3, strlen($message3));
    	socket_close($socket);
	} else {
	    echo "Error: Could not connect to ingredients server";
	}

?>

<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="style.css">
</head>
<body>
	<!-- HTML for homepage of a website about flowers -->
	<div class="header">
		<h1>BESPOKE BURGERS</h1>
	</div>
	<div class="content">
			<div class="title">
				<h2>THE ULTIMATE BURGER</h2>
				<?php 
				session_start();
				if(!empty($_SESSION['order_status']) && $_SESSION['order_status'] === 'failed') {
				    echo "Failed to submit order; insufficient ingredients at store.<br>";
				    unset($_SESSION['order_status']);
				}
				?>
				<form name="orderform" onsubmit="event.preventDefault(); validate();">
					<div>
						<div><label for="bun" id="mainlabel">Choose Bun: </label>
    						<select id="bunType" name="bun_type" onchange="onBunChange();">
    							<option value=""></option>
    							<?php
    							foreach ($ingredients["sauce"] as $ingredient) {
    							    echo("<option value = \"$ingredient[name]\">$ingredient[name]</option>");
    							}
    							?>
    						</select>
						<input type="text" id="bunCost" class="cost" name="bunCost" value="$0.00" disabled></input>
						</div>
						<div>
						<label for="ingredients" id="mainlabel">Choose Ingredients:</label>
						</div>
						<?php 
// 						NOT patty, bread, sauce
						foreach ($categories as $category){
						    if ($category != "patty" && $category != "bread" && $category != "bg"){
						        if (array_key_exists("$category", $ingredients)){
    						        foreach($ingredients["$category"] as $ingredient){
    						            if ($ingredient["quantity"] > 0){
    						            ?>
    						            <div>
    						              <div class="input-group" id="<?=$ingredient["name"]?>">
    						                  <label for="<?=$ingredient["name"]?>"><?=$ingredient["name"]?>:</label>
    						                  <input type="button" value="-" class="button-minus" data-field="quantity">
    						                  <input type="number" step="1" max="" value="0" name="quantity" id="<?=$ingredient["name"]?>_qty" class="quantity-field">
    						                  <input type="button" value="+" class="button-plus" data-field="quantity">
    						                  <input type="text" class="cost" name="tomatoCost" value="$<?=$ingredient["price"]?>" disabled></input>
    						              </div>
    						            </div>
    						            <?php
    						            }
    						        }
						        }
						    }
						}
						?>
						
						<div>
							<label for="sauce" id="mainlabel">Choose Sauce:</label> 
							<select id="sauceType"
								name="sauce_type">
								<option value=""></option>
								<?php
    							foreach ($ingredients["sauce"] as $ingredient) {
    							    echo("<option value = \"$ingredient[name]\">$ingredient[name]</option>");
    							}
    							?>
							</select>
							<input type="text" class="cost" name="sauceCost" value="$0.00" disabled></input>
						</div>
						<div>
							<label for="patty" id="mainlabel">Choose Patty:</label> <select id="pattyType"
								name="patty_type">
								<option value=""></option>
								<?php
    							foreach ($ingredients["patty"] as $ingredient) {
    							    echo("<option value = \"$ingredient[name]\">$ingredient[name]</option>");
    							}
    							?>
							</select>
							<input type="text" class="cost" name="pattyCost" value="$0.00" disabled></input>
						</div>
						<div>
							<label for="name" id="mainlabel">Customer Name:</label> 
							<input type="text" id="name" name="order_name" onkeydown="onNameChange();" onpaste="onNameChange();" oninput="onNameChange();">
						</div>
						<div>
						<label for="cost" id="totalCost">Total Cost:</label>
						<input type="text" class="cost" name="totalCost" value="$0.00" disabled></input>
						</div>
					<div class="button">
						<button type="submit" id="orderButton">Submit Order</button>
					</div>
					</div>
				</form>
			</div>
			<div class="textMain"></div>
			<div class="footer">
				<div class="navbar">
					<a class="active" href="index.html">Contact Us</a> <a
						href="factpage.html">Sign In</a> <a href="daisyguessing.html">Menu</a>
					<a href="form.html">Delivery Options</a>
				</div>
			</div>
			
			<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="script.js"></script>
</body>
</html>