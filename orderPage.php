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
    	//echo $categoriesRaw;
    	$categoriesRaw = str_replace("SEND_CAT,", "", $categoriesRaw);
    	$categories = explode(",", $categoriesRaw);
    	
    	$message3 = "DERGSTR\r\n";
    	socket_write($socket, $message3, strlen($message3));
    	socket_close($socket);
    	$socket = socket_create(AF_INET, SOCK_STREAM, 0);
		$result = socket_connect($socket, $host, $port);
    	socket_write($socket, $message1, strlen($message1)) or die("Could not send data to server\n");
    	//<!--request categories & ingredients, call getIngredients and parse into array(5), for each item check quantity greater than 0 and display if so-->
    
    
    	$message3 = "REQ_INGR\r\n";
    	socket_write($socket, $message3, strlen($message3)) or die("Could not send data to server\n");
    	$ingredientsRaw = socket_read($socket, 1024, PHP_NORMAL_READ) or die("Could not read server response\n");
    	//echo"GOT: $ingredientsRaw";
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
    	echo "<script>var ingredients = {'sauce':{'tomato_sauce':{'name':'tomato_sauce','price':5.2,'quantity':100},'BBQ':{'name':'BBQ','price':4,'quantity':100},'BBQ-Tomato':{'name':'BBQ-Tomato','price':2,'quantity':100},'Ranch-Tomato':{'name':'Ranch-Tomato','price':2.5,'quantity':100}},'salad':{'Onions':{'name':'Onions','price':4.5,'quantity':101},'Lettuce':{'name':'Lettuce','price':2,'quantity':100},'Coleslaw':{'name':'Coleslaw','price':3,'quantity':100},'Tomato':{'name':'Tomato','price':4,'quantity':100},'Olives':{'name':'Olives','price':3.5,'quantity':100}},'bread':{'Sesame':{'name':'Sesame','price':3,'quantity':100},'Wholemeal':{'name':'Wholemeal','price':3.5,'quantity':100},'Plain':{'name':'Plain','price':2.5,'quantity':100}},'patty':{'Beef':{'name':'Beef','price':5,'quantity':100},'Falafel':{'name':'Falafel','price':4,'quantity':100},'Chicken':{'name':'Chicken','price':4.5,'quantity':100},'Fish':{'name':'Fish','price':6,'quantity':100}},'cheese':{'Cheese':{'name':'Cheese','price':3,'quantity':100}}}</script>";
    	
    	//for debug purposes only
//     	print_r($categoriesRaw);
//     	echo"<br>";
//     	print_r($categories);
//     	echo"<br>";
//     	print_r($ingredientsRaw);
//     	echo"<br>";
//     	print_r($ingredients);
//     	echo"<br>";
    
    
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
<link href="https://fonts.googleapis.com/css?family=Fira+Sans" rel="stylesheet">
</head>
<body>
	<div class="container">
<<<<<<< HEAD
<<<<<<< HEAD
	<div class="header">
		<h1>BESPOKE BURGERS</h1>
	</div>
	<div class="content">
				<h2>THE ULTIMATE BURGER</h2>
				<form name="orderform" onsubmit="event.preventDefault(); validate();">
=======
		<div class="header">
			<h1>BESPOKE BURGERS</h1>
		</div>
		<div class="content">
			<h2>THE ULTIMATE BURGER</h2>
			<form name="orderform" onsubmit="event.preventDefault(); validate();"
				action="">

				<div>
>>>>>>> branch 'index.html' of git@gitlab.ecs.vuw.ac.nz:sartorguy/bespokeburgersweb.git
=======
		<div class="header">
			<h1>BESPOKE BURGERS</h1>
		</div>
		<div class="content">
			<h2>THE ULTIMATE BURGER</h2>
			<form name="orderform" onsubmit="event.preventDefault(); validate();"
				action="">

				<div>
>>>>>>> branch 'index.html' of git@gitlab.ecs.vuw.ac.nz:sartorguy/bespokeburgersweb.git
					<div>
						<label for="bun" id="mainlabel">Choose Bun: </label> <select
							id="bunType" name="bun_type"
							onchange="onDropdownChange(this.oldValue, 'bun');"
							onfocus="this.oldValue = this.value;">
							<option value=""></option>
							<?php
    					foreach ($ingredients["bread"] as $ingredient) {
    						echo("<option value = \"$ingredient[name]\">$ingredient[name]</option>");
    					}
    				?>
						</select> <input type="text" id="bunCost" class="cost" name="bunCost"
							value="$0.00" disabled></input>
					</div>
					<div>
						<label for="ingredients" id="mainlabel">Choose
							Ingredients:</label>
					</div>
					<?php 
				//NOT patty, bread, sauce
				foreach ($categories as $category){
					if ($category != "patty" && $category != "bread" && $category != "sauce"){
						if (array_key_exists("$category", $ingredients)){
    						foreach($ingredients["$category"] as $ingredient){
    							if ($ingredient["quantity"] > 0){
 			?>
					<div>
						<div class="input-group" id="&lt;?=$ingredient["name"]?>
							"> <label for="&lt;?=$ingredient["name"]?>"><?=$ingredient["name"]?>:
							</label> <input type="button" value="-" class="button-minus"
								data-field="quantity"> <input type="number" step="1"
								max="" value="0" name="quantity" id="&lt;?=$ingredient["name"]?>_qty"
							class="quantity-field");">
							<!--  onChange="onQuantityChange('<?=$category?>', '<?=$ingredient["name"]?>' onfocus="this.oldValue = this.value;" -->
							<input type="button" value="+" class="button-plus"
								data-field="quantity"> <input type="text" class="cost"
								name="&lt;?=$ingredient["name"]?>Cost" id="
							<?=$ingredient["name"]?>
							Cost" value="$0.00" disabled><input></input>
						</div>
					</div>
					<?php
    						     }
    						  }
						   }
						}
<<<<<<< HEAD
<<<<<<< HEAD
						?>
						
						<div>
							<label for="sauce" id="mainlabel">Choose Sauce:</label> 
							<select id="sauceType" name="sauce_type" onchange="onDropdownChange('sauce');">
								<option value=""></option>
								<?php
    							foreach ($ingredients["sauce"] as $ingredient) {
    							    echo("<option value = \"$ingredient[name]\">$ingredient[name]</option>");
    							}
    							?>
							</select>
							<input type="text" class="cost" id="sauceCost" name="sauceCost" value="$0.00" disabledonchange="onDropdownChange(this.oldValue, 'sauce');" onfocus="this.oldValue = this.value;">></input>
						</div>
						<div>
							<label for="patty" id="mainlabel">Choose Patty:</label>
							<select id="pattyType" name="patty_type" onchange="onDropdownChange('patty');">
								<option value=""></option>
								<?php
    							foreach ($ingredients["patty"] as $ingredient) {
    							    echo("<option value = \"$ingredient[name]\">$ingredient[name]</option>");
    							}
    							?>
							</select>
							<input type="text" class="cost" id="pattyCost" name="pattyCost" value="$0.00" disabledonchange="onDropdownChange(this.oldValue, 'patty');" onfocus="this.oldValue = this.value;">></input>
						</div>
						<div>
							<label for="name" id="mainlabel">Customer Name:</label> 
							<input type="text" id="name" name="order_name" onkeydown="onNameChange(this.oldValue);" onpaste="onNameChange();" oninput="onNameChange();">
						</div>
						<div>
						<label for="cost" id="totalCostLabel">Total Cost:</label>
						<input type="text" class="cost" name="totalCost" id="totalCost" value="$0.00" disabled></input>
						</div>
=======
					}
				?>
					<div>
						<label for="sauce" id="mainlabel">Choose Sauce:</label> <select
							id="sauceType" name="sauce_type"
							onchange="onDropdownChange('sauce');">
							<option value=""></option>
							<?php
    					foreach ($ingredients["sauce"] as $ingredient) {
    						echo("<option value = \"$ingredient[name]\">$ingredient[name]</option>");
    					}
    				?>
						</select> <input type="text" class="cost" id="sauceCost" name="sauceCost"
							value="$0.00" disabled></input>
					</div>
					<div>
						<label for="patty" id="mainlabel">Choose Patty:</label> <select
							id="pattyType" name="patty_type"
							onchange="onDropdownChange('patty');">
							<option value=""></option>
							<?php
    						foreach ($ingredients["patty"] as $ingredient) {
    							echo("<option value = \"$ingredient[name]\">$ingredient[name]</option>");
    						}
    					?>
						</select> <input type="text" class="cost" id="pattyCost" name="pattyCost"
							value="$0.00" disabled></input>
					</div>
					<div>
						<label for="name" id="mainlabel">Customer Name:</label> <input
							type="text" id="name" name="order_name"
							onkeydown="onNameChange(this.oldValue);"
							onpaste="onNameChange();" oninput="onNameChange();">
					</div>
					<div>
						<label for="cost" id="totalCostLabel">Total Cost:</label> <input
							type="text" class="cost" name="totalCost" id="totalCost"
							value="$0.00" disabled></input>
					</div>
>>>>>>> branch 'index.html' of git@gitlab.ecs.vuw.ac.nz:sartorguy/bespokeburgersweb.git
=======
					}
				?>
					<div>
						<label for="sauce" id="mainlabel">Choose Sauce:</label> <select
							id="sauceType" name="sauce_type"
							onchange="onDropdownChange('sauce');">
							<option value=""></option>
							<?php
    					foreach ($ingredients["sauce"] as $ingredient) {
    						echo("<option value = \"$ingredient[name]\">$ingredient[name]</option>");
    					}
    				?>
						</select> <input type="text" class="cost" id="sauceCost" name="sauceCost"
							value="$0.00" disabled></input>
					</div>
					<div>
						<label for="patty" id="mainlabel">Choose Patty:</label> <select
							id="pattyType" name="patty_type"
							onchange="onDropdownChange('patty');">
							<option value=""></option>
							<?php
    						foreach ($ingredients["patty"] as $ingredient) {
    							echo("<option value = \"$ingredient[name]\">$ingredient[name]</option>");
    						}
    					?>
						</select> <input type="text" class="cost" id="pattyCost" name="pattyCost"
							value="$0.00" disabled></input>
					</div>
					<div>
						<label for="name" id="mainlabel">Customer Name:</label> <input
							type="text" id="name" name="order_name"
							onkeydown="onNameChange(this.oldValue);"
							onpaste="onNameChange();" oninput="onNameChange();">
					</div>
					<div>
						<label for="cost" id="totalCostLabel">Total Cost:</label> <input
							type="text" class="cost" name="totalCost" id="totalCost"
							value="$0.00" disabled></input>
					</div>
>>>>>>> branch 'index.html' of git@gitlab.ecs.vuw.ac.nz:sartorguy/bespokeburgersweb.git
					<div class="button">
						<button type="submit" id="orderButton">Submit Order</button>
					</div>
<<<<<<< HEAD
<<<<<<< HEAD
					</div>
				</form>
			</div>
			<div class="footer">
=======
=======
>>>>>>> branch 'index.html' of git@gitlab.ecs.vuw.ac.nz:sartorguy/bespokeburgersweb.git
				</div>
			</form>
		</div>
		<div class="footer">
<<<<<<< HEAD
>>>>>>> branch 'index.html' of git@gitlab.ecs.vuw.ac.nz:sartorguy/bespokeburgersweb.git
=======
>>>>>>> branch 'index.html' of git@gitlab.ecs.vuw.ac.nz:sartorguy/bespokeburgersweb.git
			<div class="navbar">
				<a href="index.html">Homepage</a> 
				<a class="active" href="orderPage.php">Order</a> 
				<a href="menuPage.html">Menu</a>
				<a href="contactPage.html">Contact Us</a>
			</div>
		</div>
<<<<<<< HEAD
<<<<<<< HEAD
			</div>
			<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="script.js"></script>
=======
	</div>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"
		type="text/javascript"></script>
	<script src="script.js" type="text/javascript"></script>
>>>>>>> branch 'index.html' of git@gitlab.ecs.vuw.ac.nz:sartorguy/bespokeburgersweb.git
=======
	</div>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"
		type="text/javascript"></script>
	<script src="script.js" type="text/javascript"></script>
>>>>>>> branch 'index.html' of git@gitlab.ecs.vuw.ac.nz:sartorguy/bespokeburgersweb.git
</body>
</html>
