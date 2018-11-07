package client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


public class OrderPane extends VBox {
	
	//Attributes
	Order order;
	
	/**
	 * Constructor
	 * @param order Order: order from customer that needs to be displayed
	 */
	public OrderPane(Order order) {
		
		this.order = order;
		createOrderPane(order);
	
	}
	
	
	public void createOrderPane(Order order) {
		
		//Add elements to the orderPane
		VBox header = new VBox();
		VBox ingredients = new VBox();
		this.getChildren().addAll(header,ingredients);
		
		//Order number, customer name labels.
		Label number = new Label("Order #: " + order.getId());
		Label customer = new Label("Name: " + order.getCustomer());
		
		//Add header labels to the header VBox.
		header.getChildren().addAll(number,customer);
		
		//Add labels for each ingredient. The map is sorted using Comparable interface.
		Map<Ingredient,Integer> ingredientMap = order.getIngredients();
		List<Ingredient> sortedIngredientList = new ArrayList<Ingredient>(ingredientMap.keySet());
		Collections.sort(sortedIngredientList);
		
		for (int i = 0; i < sortedIngredientList.size(); i++) {
			
			//Create label with the name of the ingredient and quantity required.
			Ingredient item = sortedIngredientList.get(i);
			String ingredient = item.getName() + " x " + ingredientMap.get(item);
			Label ingredientLabel = new Label(ingredient);
			
			//Add label to the ingredients VBox.
			ingredients.getChildren().add(ingredientLabel);
		}
		
		//Add done button to the end.
		Button doneButton = new Button("DONE");
		ingredients.getChildren().add(doneButton);
		
		//Change size of the orderPane.
		this.setMinWidth(200);
		this.setMaxWidth(200);
		this.setMinHeight(400);
		this.setMaxHeight(400);
		
	}
	
	

}



