<?php
$bun = ($_POST['bunType']);
$ingredients    = ($_POST['ingredients']);
$sauce   = ($_POST['sauceType']);
$patty = ($_POST['pattyType']);
$name   = ($_POST['order_name']);

$emailmessage   = "

Name: $name
Email: $email
Phone: $phone
Message: $message
Add to newsletter?:

";

mail("you@domain.com", $subject, $emailmessage);

?>