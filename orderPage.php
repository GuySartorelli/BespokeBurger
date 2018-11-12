<!DOCTYPE html>

//<!-- <?php

	// <!--create socket connect-->
	$host    = "127.0.0.1";
	$port    = 9090;
	$socket = socket_create(AF_INET, SOCK_STREAM, 0);
	$result = socket_connect($socket, $host, $port);
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
	$ingredients = array();
	$mainIndex = 0;
	for ($i = 1; $i < sizeof($ingredients1D); $i++){
		switch ($i % 5) {
    	case 0:
			$ingredients[$mainIndex] = array(
											 "name" => $ingredients1D[$i],
											);
        	//$ingredients[$mainIndex]["name"] = $ingredients1D[$i];
        	break;
		case 1:
			$ingredients[$mainIndex]["price"] = doubleval($ingredients1D[$i]);
			break;
		case 2:
			$ingredients[$mainIndex]["quantity"] = intval($ingredients1D[$i]);
			break;
		case 3:
        	$ingredients[$mainIndex]["category"] = $ingredients1D[$i];
			$mainIndex++;
		}
	}

	print_r($categoriesRaw);
	echo("\n");
	print_r($categories);
	echo("\n");
	print_r($ingredientsRaw);
	echo("\n");
	print_r($ingredients);


	//<!--DRGSTR and close the socket. -->
	$message3 = "DERGSTR\r\n";
	socket_write($socket, $message3, strlen($message3));
	socket_close($socket);

?> -->

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="style.css">
<link href="https://fonts.googleapis.com/css?family=Fira+Sans" rel="stylesheet">
</head>
<body>
<div class="container">
	<div class="header">
		<h1>BESPOKE BURGERS</h1>
	</div>
	<div class="content">
			<h2>THE ULTIMATE BURGER</h2>
			<form name="orderform" action="successfulOrderPage.php" method="post">
				<div>
					<div>
					<div class="row"
						<div class="column1">
							<label for="bun" id="mainlabel">Choose Bun: </label> 
						</div>
						<div class="column2">
							<select
								id="bunType" name="bun_type">
								<option value=""></option>
								<option value="sesame">Sesame</option>
								<option value="plain">Plain</option>
								<option value="wholemeal">Wholemeal</option>
							</select> 
						</div>
						<div class="column3">
							<input type="text" class="cost" name="bunCost" value="$2.00"
							disabled></input>
						</div>
					</div>
					<div class="row">
						<div class="column1">
						<label for="ingredients" id="mainlabel">Choose
							Ingredients:</label>
					</div>
					<div class="row">
						<div class="column1">
							<label for="tomato">Tomato:</label> 
						</div>
						<div class="column2">
							<div class="input-group" id="tomato">
							<input type="button"
								value="-" class="button-minus" data-field="quantity"> 
								<input
								type="number" step="1" max="" value="0" name="quantity"
								id="tomato_qty" class="quantity-field"> <input
								type="button" value="+" class="button-plus"
								data-field="quantity"> 
							</div>
							</div>
								<div class="column3"
								<input type="text" class="cost"
								name="tomatoCost" value="$0.20" disabled></input>
							</div>
					</div>
					<div>
						<div class="input-group" id="lettuce">
							<label for="lettuce">Lettuce:</label> <input type="button"
								value="-" class="button-minus" data-field="quantity"> <input
								type="number" step="1" max="" value="0" name="quantity"
								id="lettuce_qty" class="quantity-field"> <input
								type="button" value="+" class="button-plus"
								data-field="quantity"> <input type="text" class="cost"
								name="lettuceCost" value="$0.20" disabled></input>
						</div>
					</div>
					<div>
						<div class="input-group" id="cheese">
							<label for="cheese">Cheese:</label> <input type="button"
								value="-" class="button-minus" data-field="quantity"> <input
								type="number" step="1" max="" value="0" name="quantity"
								id="cheese_qty" class="quantity-field"> <input
								type="button" value="+" class="button-plus"
								data-field="quantity"> <input type="text" class="cost"
								name="cheeseCost" value="$0.20" disabled></input>
						</div>
					</div>
					<div>
						<div class="input-group" id="beetroot">
							<label for="beetroot">Beetroot:</label> <input type="button"
								value="-" class="button-minus" data-field="quantity"> <input
								type="number" step="1" max="" value="0" name="quantity"
								id="beetroot_qty" class="quantity-field"> <input
								type="button" value="+" class="button-plus"
								data-field="quantity"> <input type="text" class="cost"
								name="beetrootCost" value="$0.20" disabled></input>
						</div>
					</div>
					<div>
						<div class="input-group" id="gherkin">
							<label for="gherkin">Gherkin:</label> <input type="button"
								value="-" class="button-minus" data-field="quantity"> <input
								type="number" step="1" max="" value="0" name="quantity"
								id="gherkin_qty" class="quantity-field"> <input
								type="button" value="+" class="button-plus"
								data-field="quantity"> <input type="text" class="cost"
								name="gherkinCost" value="$0.20" disabled></input>
						</div>
					</div>
					<div>
						<div class="input-group" id="pineapple">
							<label for="pineapple">Pineapple:</label> <input type="button"
								value="-" class="button-minus" data-field="quantity"> <input
								type="number" step="1" max="" value="0" name="quantity"
								id="pineapple_qty" class="quantity-field"> <input
								type="button" value="+" class="button-plus"
								data-field="quantity"> <input type="text" class="cost"
								name="pineappleCost" value="$0.20" disabled></input>
						</div>
					</div>
					<div>
						<label for="sauce" id="mainlabel">Choose Sauce:</label> <select
							id="sauceType" name="sauce_type">
							<option value=""></option>
							<option value="ketchup">Ketchup</option>
							<option value="mustard">Mustard</option>
							<option value="bbq">BBQ</option>
							<option value="mayo">Mayo</option>
							<option value="chipotle">Chipotle</option>
						</select> <input type="text" class="cost" name="sauceCost" value="$0.20"
							disabled></input>
					</div>
					<div>
						<label for="patty" id="mainlabel">Choose Patty:</label> <select
							id="pattyType" name="patty_type">
							<option value=""></option>
							<option value="beef">Beef</option>
							<option value="chicken">Chicken</option>
							<option value="pork">Pork</option>
							<option value="tofu">Tofu</option>
						</select> <input type="text" class="cost" name="pattyCost" value="$2.00"
							disabled></input>
					</div>
					<div>
						<label for="name" id="mainlabel">Customer Name:</label> <input
							type="text" id="name" name="order_name">
					</div>
					<div>
						<label for="cost" id="totalCost">Total Cost:</label> <input
							type="text" class="cost" name="totalCost" value="$10.00" disabled></input>
					</div>
					<div class="button">
						<button type="submit" id="orderButton">Submit Order</button>
					</div>
				</div>
			</form>
		</div>
		<div class="footer">
			<div class="navbar">
				<a class="active" href="index.html">Contact Us</a> <a
					href="factpage.html">Sign In</a> <a href="daisyguessing.html">Menu</a>
				<a href="form.html">Delivery Options</a>
			</div>
		</div>
</div>
		<script
			src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"
			type="text/javascript"></script>
		<script src="script.js" type="text/javascript"></script>
	</div>
</body>
</html>