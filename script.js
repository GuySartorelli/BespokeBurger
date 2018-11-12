//use ingredients passed from php to alter price etc
console.log(ingredients);

//fired when any dropdown changes and updates the corresponding price field
function onDropdownChange(dropdown) {
	if (dropdown === 'bun') dropdown = 'bread';
	let ingredient = document.getElementById(dropdown+"Type").value;
	let priceField = document.getElementById(dropdown+"Cost");
	let price = ingredients[dropdown][ingredient].price;
	priceField.value = formatCurrency(price);
}

//fired when any quantity changes and updates the corresponding price field
function onQuantityChange(oldValue, category, ingredient){
	let priceField = document.getElementById(ingredient+"Cost");
	let quantity = parseInt(document.getElementById(ingredient+"_qty").value);
	let price = parseFloat(ingredients[category][ingredient].price) * quantity;
	priceField.value = formatCurrency(price.toString());
	
	let totalPriceField = document.getElementById("totalCost");
	totalPriceField.value = getNewTotal(totalPriceField.value, priceField.value, priceField.oldValue);
	
	nameField.oldValue = priceField.value;
}

//helper function for the above
function getNewTotal(totalValue, newValue, oldValue){
	totalPrice = parseFloat(totalValue.slice(1, totalValue.length));
	newPrice = parseFloat(newValue.slice(1, totalValue.length));
	oldPrice = parseFloat(oldValue.slice(1, totalValue.length));
	return totalPrice + (newPrice - oldPrice);
}

// validates characters in namefield as data entered or pasted
function onNameChange() {
	let nameField = document.getElementById('name');
	let name = nameField.value;
	name = name.replace(/NONUM/g, "");// removes all instances of the string "NONUM"
	name = name.replace(/\W/g, ""); // removes all non alphanumeric characters
	nameField.value = name;
}

// validates and then posts the form on button click
async function validate() {
	let isValid = true;
	let bun = document.getElementById('bunType').value;
	let name = document.getElementById('name').value;
	if (bun === "") {
		isValid = false;
		// alert user that it's invalid 'cause no bun
	}
	if (name === "") {
		isValid = false;
		// alert user that they must have a name
	}
	let sauce = document.getElementById('sauceType').value;
	let patty = document.getElementById('pattyType').value;
	
	//orderNumber,customerName,ingredientCategory,ingredientName,num,ingredientCategory,ingredientName,num etc
	let order = "NONUM,"+name+",bread,"+bun+",1,sauce,"+sauce+",1,patty,"+patty+",1";	

	// ingredients.category.ingredient
	let notMiscCategories = ["sauce", "patty", "bread"];
	for (let category of Object.keys(ingredients)) {
		if (!notMiscCategories.includes(category)){
			for (let ingredient of Object.keys(ingredients[category])){
				let quantity = document.getElementById(ingredients[category][ingredient].name+'_qty').value;
				if (quantity > 0){
					order += ","+category+","+ingredients[category][ingredient].name+","+quantity;
				}
			}
		}
	}

	let response = await fetch("submitOrder.php", {
		 				 method: 'POST',
						 headers: { 'Content-type': 'application/x-www-form-urlencoded', },
						 body: 'order=' + encodeURIComponent(order)
						 });
	let result = await response.text();
	
	if (result.startsWith("0,")){
		//TODO
		//failed. Display a message and refresh the ingredients
	} else if (result.startsWith("1,")){
		//TODO
		//success. Forward to successfulOrderPage with the order number.
	} 
}

function incrementValue(e) {
	e.preventDefault();
	let fieldName = $(e.target).data('field');
	let parent = $(e.target).closest('div');
	let currentVal = parseInt(parent.find('input[name=' + fieldName + ']')
			.val(), 10);
	
	if (!isNaN(currentVal)) {
		let ingredient = parent.find('input[name=' + fieldName + ']').attr('id').replace("_qty", "");
		if (findIngredient(ingredient).quantity > currentVal){
			parent.find('input[name=' + fieldName + ']').val(currentVal + 1);
		}
	} else {
		parent.find('input[name=' + fieldName + ']').val(0);
	}
}

function decrementValue(e) {
	e.preventDefault();
	let fieldName = $(e.target).data('field');
	let parent = $(e.target).closest('div');
	let currentVal = parseInt(parent.find('input[name=' + fieldName + ']')
			.val(), 10);

	if (!isNaN(currentVal) && currentVal > 0) {
		parent.find('input[name=' + fieldName + ']').val(currentVal - 1);
	} else {
		parent.find('input[name=' + fieldName + ']').val(0);
	}
}

function findIngredient(toFind){
	for (let category of Object.keys(ingredients)) {
		for (let ingredient of Object.keys(ingredients[category])){
			if (ingredients[category][ingredient].name === toFind) {
				return ingredients[category][ingredient];
			}
		}
	}
}

//formats a string representation of a double as a currency
function formatCurrency(price){
	price = "$"+price;
	let tokens = price.split(".");
	if (tokens.length < 0 || tokens.length > 2){
		//oh no!
	} else if (tokens.length === 1) {
		price += ".00";
	} else {
		if (tokens[1].length > 2){
			//reduce to just 2 chars after decimal
			price = price.slice(0, price.length-(tokens[1].length-2));
		} else if (tokens[1].length === 1){
			price += "0";
		} else if (tokens[1].length === 0){
			price += "00";
		}
	}
	return price;
}

$('.input-group').on('click', '.button-plus', function(e) {
	incrementValue(e);
});

$('.input-group').on('click', '.button-minus', function(e) {
	decrementValue(e);
});