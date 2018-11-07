package client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.TimelineBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * GUI layout for Orders tab.<br>
 * Sends completed orders to server for storage in database.<br>
 * Receives incoming orders from server via ClientConnection object.
 * @author Bespoke Burgers
 *
 */
public class OrdersUI extends Tab {
	
	//Attributes
    private ClientConnection client;
    private volatile Map<Integer, Order> orders;
    private Order currentOrder;
    
    private HBox ordersPane;
    

    /**
     * Constructor
     * @param client ClientConnection: connection through which information can be sent to other clients
     */
    public OrdersUI(ClientConnection client) {
        this.client = client;
        this.orders = new HashMap<Integer, Order>();
        
        setupOrdersTab();
    }
    

    
    /**
     * Adds a new order to the orders map and displays it in the ui
     * @param order Order: the order to be added
     */
    public void add(Order order) {
        //TODO
    }
    
    /**
     * Marks an order as complete and sends it to the server to be added to the database
     * @param order int: The identification of the order
     */
    private synchronized void complete(int order) {
        //TODO
    }
    
    /**
     * Sets the new status of the order
     * @param order int: The identification of the order
     * @param status String: the new status of the order
     * @param fromServer boolean: true if this method is being called from the ClientConnection object
     */
    public void updateStatus(int order, String status, boolean fromServer) {
       //TODO 
    }
    
    /**
     * Returns the specified order
     * @param order int: The identification of the order
     * @return Order: the specified order
     */
    public synchronized Order getOrder(int order) {
        return orders.get(order);
    }
    
    /**
     * Sets up the format of the orders tab.
     */
	public void setupOrdersTab() {
		
		//Set the text on the tab.
        this.setText("Orders");
        
        //Sets the HBox which will display the orders.
        ordersPane = new HBox();
        
        /////TESTING/////
        createTestOrders();
        refreshOrders();
        

	}
	
	public void refreshOrders() {
		
		
		
	}
	
	public VBox createOrderPane(Order order) {
		
		//Create VBox to hold all the elements of the order.
		VBox orderPane = new VBox();
		
		//Add elements to the orderPane
		VBox header = new VBox();
		VBox ingredients = new VBox();
		orderPane.getChildren().addAll(header,ingredients);
		
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
		
		return orderPane;
		
	}
	
	public void createTestOrders() {
		
		//Test ingredients.
		Ingredient lettuce = new Ingredient("Salad","Lettuce",300,10,1.00);
		Ingredient tomato = new Ingredient("Salad","Tomato",300,10,1.00);
		Ingredient patty = new Ingredient("Patty","Beef",300,10,1.00);

		//Test ingredient map.
		Map<Ingredient, Integer> ingredients = new HashMap();
		ingredients.put(lettuce, 1);
		ingredients.put(tomato, 2);
		ingredients.put(patty, 1);
		
		for (int i = 0; i < 6; i++) {
			Order order = new Order(i,"John",ingredients,5.50);
			orders.put(i, order);
		}
		
	}
	


}
