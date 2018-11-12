<!DOCTYPE html>

// <?php
// //this handy function from https://stackoverflow.com/a/834355
// function startsWith($haystack, $needle)
// {
//     $length = strlen($needle);
//     return (substr($haystack, 0, $length) === $needle);
// }

// // $bun = ($_POST['bun_type']);
// // $sauce = ($_POST['sauce_type']);
// // $patty = ($_POST['patty_type']);
// //qty is ingredientName_qty
// $cost = ($_POST['totalCost']);
// $name = ($_POST['order_name']);


// $ingredients = ($_POST['ingredients']);


// $host = "127.0.0.1";
// $port = 9090;
// $socket = socket_create(AF_INET, SOCK_STREAM, 0) or die("Could not create socket\n");
// $result = socket_connect($socket, $host, $port) or die("Could not connect to server\n"); 

// $message1 = "RGSTR,WEB\r\n";
// socket_write($socket, $message1, strlen($message1)) or die("Could not send data to server\n");

// $message2 = "ORDER,NONUM,$name,$ingredients\r\n";
// socket_write($socket, $message2, strlen($message2)) or die("Could not send data to server\n");

// // get server response
// $result = socket_read ($socket, 1024) or die("Could not read server response\n");
// if (startsWith($result, "0,")){ //failed. Redirect to order page.
//     session_start();
//     $_SESSION['order_status'] = 'failed';
//     header('Location: orderPage.php', true, 303); die();
// } elseif (startsWith($result, "1,")){
//     $orderNumber = intval(explode($result)[1]);
// }

// $message3 = "DERGTR\r\n";
// socket_write($socket, $message3, strlen($message3));
// socket_close($socket);
//  ?>


<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="style.css">
</head>
<body>
	
	<div class="header">
		<h1>BESPOKE BURGERS</h1>
	</div>
	<div class="content">
		
			<div class="title">
				<h2>THE ULTIMATE BURGER</h2>
			</div>
			<div class="textMain" id= "successfulOrder">
				<p>
					CONGRATS!<?= $name ?>!
				</p>
				<p>
				<!--  ADD ORDER DETAILS BACK IN -->
					Your order for was successful! Your order number is <?= $orderNumber ?>
				</p>
				<p>We have received your order and it will be ready shortly.</p>

			</div>
		
		<button type="button" id="orderAgain"
			onclick="window.location.href='orderPage.html'">Order Here!</button>
	</div>
	<div class="footer">
		<div class="navbar">
			<a class="active" href="index.html">Contact Us</a> <a
				href="factpage.html">Sign In</a> <a href="daisyguessing.html">Menu</a>
			<a href="form.html">Delivery Options</a>
		</div>
	</div>
</body>