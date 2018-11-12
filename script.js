//use ingredients passed from php to alter price etc
console.log(ingredients);

function onBunChange() {
	let bun = document.getElementById('bunType').value;
	let priceField = document.getElementById('bunCost');
	// change priceField according to price in ingredients
}

// validates characters in namefield as data entered or pasted
function onNameChange() {
	let nameField = document.getElementById('name');
	let name = nameField.value;
	name = name.replace(/NONUM/g, "");// removes all instances of the string
										// "NONUM"
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
		//failed. Display a message and refresh the ingredients
	} else if (result.startsWith("1,")){
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

$('.input-group').on('click', '.button-plus', function(e) {
	incrementValue(e);
});

$('.input-group').on('click', '.button-minus', function(e) {
	decrementValue(e);
});