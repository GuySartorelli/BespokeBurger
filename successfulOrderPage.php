

<!DOCTYPE html>
<?php
$bun = ($_POST['bun_type']);
$pineapple    = ($_POST['pineapple_qty']);
$sauce   = ($_POST['sauce_type']);
$patty = ($_POST['patty_type']);
$name   = ($_POST['order_name']);
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
	<?php echo $pineapple;?>
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