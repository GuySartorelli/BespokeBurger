package client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class OrderPane extends VBox {
	
	//Attributes
	Order order;
	VBox header;
	VBox ingredients;
	OrdersUI parent;
	Label statusLabel;
	
	/**
	 * Constructor
	 * @param order Order: order from customer that needs to be displayed
	 */
	public OrderPane(Order order, OrdersUI parent) {
		
		this.order = order;
		this.parent = parent;
		
		//Add VBoxes to the pane.
		this.header = new VBox();
		this.ingredients = new VBox();
		this.getChildren().addAll(this.header,this.ingredients);
		
		setupOrderPane(order);
	}
	
	/**
	 * Updates the header to match the order's current status. This is reflected in colour and statusLabel text.
	 */
	public void updateHeader() {
		
		String status = order.getStatus();
		
		//List of the possible header style classes and then removes them from the header.
		List<String> styles = Arrays.asList("headerPending","headerInProgress","headerComplete","headerCollected");
		header.getStyleClass().removeAll(styles);
		
		//Set the text on the status label.
		statusLabel.setText("Status: " + status);
		
		switch (status) {
			case Order.PENDING: header.getStyleClass().add("headerPending"); setHeaderLabelColourDark(true); 
			break;
			case Order.IN_PROGRESS: header.getStyleClass().add("headerInProgress"); setHeaderLabelColourDark(false);
			break;
			case Order.COMPLETE: header.getStyleClass().add("headerComplete");setHeaderLabelColourDark(true);
			break;
			case Order.COLLECTED: header.getStyleClass().add("headerCollected");setHeaderLabelColourDark(false);
			break;
		}		
	}
		
	/**
	 * Sets the header labels text to a dark colour when true, or a light colour when false.
	 * @param dark Boolean: whether text should use a dark or light coloured styling.
	 */
	public void setHeaderLabelColourDark(Boolean dark) {
		
		//List of the possible headerLabel style classes which are then removed from the label.
		List<String> styles = Arrays.asList("headerLabelDark","headerLabelLight");
		
		List<Node> headerLabels = header.getChildren();

		for (Node child : headerLabels) {
			try {
				Label label = (Label) child;
				label.getStyleClass().removeAll(styles);				
				if (dark) {
					label.getStyleClass().add("headerLabelDark");
				} else {
					label.getStyleClass().add("headerLabelLight");
				}
			}
			catch(ClassCastException exc) {}
		}
	}
	
	/**
	 *Sets up the layout of the whole orderPane object. Creates labels to match the order.
	 * @param order Order: The order object associated with this orderPane.
	 */
	public void setupOrderPane(Order order) {
				
		//Order number, customer name labels. 
		statusLabel = new Label("Status: " + order.getStatus());
		Label number = new Label("Order # " + order.getId());
		Label customer = new Label("Name: " + order.getCustomer());		
				
		//Add header labels to the header VBox.
		header.getChildren().addAll(statusLabel,number,customer);
		
		//Add labels for each ingredient. The map is sorted using Comparable interface.
		Map<Ingredient,Integer> ingredientMap = order.getIngredients();
		List<Ingredient> sortedIngredientList = new ArrayList<Ingredient>(ingredientMap.keySet());
		Collections.sort(sortedIngredientList);
		
		//Change size of the orderPane..
		int width = 400;
		int height = 800;
		this.setMinWidth(width);
		this.setMaxWidth(width);
		this.setMinHeight(height);
		this.setMaxHeight(height);
		
		for (int i = 0; i < sortedIngredientList.size(); i++) {
			
			//Create label with the name of the ingredient and quantity required. 
			Ingredient item = sortedIngredientList.get(i);
			String ingredient = item.getName() + " x " + ingredientMap.get(item);
			Label ingredientLabel = new Label(ingredient);
			
			//Add relevant style class.
			switch (item.getCategory().getName()) {
				case "Bun" : ingredientLabel.getStyleClass().add("labelBun"); break;
				case "Patty" : ingredientLabel.getStyleClass().add("labelPatty"); break;
				case "Salad" : ingredientLabel.getStyleClass().add("labelSalad"); break;
				case "Sauce" : ingredientLabel.getStyleClass().add("labelSauce"); break;
			}
			
			//Add label to the ingredients VBox.
			ingredients.getChildren().add(ingredientLabel);
			
			//Set the width of the label to match the width of the order pane.
			ingredientLabel.setMinWidth(width);
		}
		
		//Add done button to the end.
		Button doneButton = new Button("DONE");
		ingredients.getChildren().add(doneButton);
		

		
		//Add style class to the header,ingredients,and order panes. Refer to the CSS file styleIngredients..
		header.getStyleClass().add("headerPane");
		ingredients.getStyleClass().add("ingredientsPane");
		this.getStyleClass().add("orderPane");

//
//		//Change size of text for ingredients.
//		int ingredFontSize = 20;
//		ingredients.setStyle("-fx-font: " + ingredFontSize + " arial;");
		
		
		//Change the background colour of header.
		updateHeader();
		
		//Change the background colour of the order pane.
		//this.setStyle("-fx-background-color:  white;");
		//Add event handler to header. Got from: 
		//https://stackoverflow.com/questions/25550518/add-eventhandler-to-imageview-contained-in-tilepane-contained-in-vbox
		header.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			
			//Currently toggles between pending and in-progress. Need to adjust for complete/collected for cashiers.
			if (order.getStatus().equals(Order.PENDING)) {
				parent.updateStatus(order.getId(), Order.IN_PROGRESS, false);

			}else if (order.getStatus().equals(Order.IN_PROGRESS)) {
				parent.updateStatus(order.getId(), Order.PENDING, false);
			}
			updateHeader();
			event.consume();
		});



		
	}
	
	

}



