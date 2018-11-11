<!DOCTYPE html>

<?php
$bun = ($_POST['bun_type']);
$pineapple    = ($_POST['pineapple_qty']);
$sauce   = ($_POST['sauce_type']);
$patty = ($_POST['patty_type']);
$name   = ($_POST['order_name']);

$host    = "127.0.0.1";
$port    = 5444;
$message1 = "RGSTR,WEB\r\n";

$message2 = "ORDER,22,order_name, $pineapple,lettuce,2\r\n"; 
echo "Message To server :".$message;

// create socket
$socket = socket_create(AF_INET, SOCK_STREAM, 0) or die("Could not create socket\n");

// connect to server
$result = socket_connect($socket, $host, $port) or die("Could not connect to server\n"); 

// send string to server
socket_write($socket, $message1, strlen($message1)) or die("Could not send data to server\n");
socket_write($socket, $message2, strlen($message2)) or die("2 Could not send data to server\n");


// get server response
$result = socket_read ($socket, 1024) or die("Could not read server response\n");
echo "Reply From Server  :".$result;

$message3 = "DERGTR\r\n";
socket_write($socket, $message3, strlen($message3));
// close socket
socket_close($socket);

  ?>      


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
					CONGRATS!
					<?php echo $name; ?>
					!
				</p>
				<p>
					Your order for
					<?php echo $bun;?>
		<p>Pinapple: <?php echo $pineapple;?></p>
<?php echo $sauce;?>
<?php echo $patty;?>
					was successful
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