package client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class OrderPane extends VBox {
	
	//Attributes
	Order order;
	VBox header;
	VBox ingredients;
	
	/**
	 * Constructor
	 * @param order Order: order from customer that needs to be displayed
	 */
	public OrderPane(Order order) {
		
		this.order = order;
		
		//Add VBoxes to the pane.
		this.header = new VBox();
		this.ingredients = new VBox();
		this.getChildren().addAll(this.header,this.ingredients);
		
		createOrderPane(order);
	}
	
	public void setHeaderPaneColour() {
		
		String status = order.getStatus();
		switch (status) {
		case Order.PENDING: header.setStyle("-fx-background-color:  orange;");
		break;
		case Order.IN_PROGRESS: header.setStyle("-fx-background-color:  blue;");
		break;
		case Order.COMPLETE: header.setStyle("-fx-background-color:  green;");
		break;
		case Order.COLLECTED: header.setStyle("-fx-background-color:  black;");
		break;
		}
		
//		System.out.println("order status: "+ order.getStatus());

		
	}
	
	
	public void createOrderPane(Order order) {
				
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
		int width = 400;
		int height = 800;
		this.setMinWidth(width);
		this.setMaxWidth(width);
		this.setMinHeight(height);
		this.setMaxHeight(height);
		
		//Change size of text for header.
		int headFontSize = 24;
		number.setStyle("-fx-font: " + headFontSize + " arial;");
		customer.setStyle("-fx-font: " + headFontSize + " arial;");
		
		//Change size of text for ingredients.
		int ingredFontSize = 20;
		ingredients.setStyle("-fx-font: " + ingredFontSize + " arial;");
		
		//Add border and drop shadow to orderPane.
		this.setStyle("-fx-border-color: #4C1130");
        this.setEffect(new DropShadow(10, Color.BLACK));

		//Change the background colour of header.
		setHeaderPaneColour();
		
		//Change the background colour of the order pane.
		this.setStyle("-fx-background-color:  white;");
	}
	
	

}



