package client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javafx.animation.KeyFrame;
import javafx.animation.TimelineBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
    
    private ScrollPane scrollPane;
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
    	
    	//For updating not from server.
    	if (!fromServer) {
    		orders.get(order).setStatus(status);
    	}
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
        
        //Sets the HBox which will display the orders and adds it to the tab. Adds it to a ScrollPane.
        ordersPane = new HBox();
        scrollPane = new ScrollPane(ordersPane);

        this.setContent(scrollPane);
        
        //Setting the format of the ordersPane.
        ordersPane.setSpacing(20);
        ordersPane.setStyle("-fx-padding: 10 10 10 15");
        
        
        /////TESTING/////
        createTestOrders();
        refreshOrders();
	}
	
	public void refreshOrders() {
		
		//Creates a treeMap from the orders map so that it is ordered by the keys (order ID).
		Map<Integer, Order> sortedTreeMap = new TreeMap<Integer, Order>(orders);
		
		//Iterate through the treeMap to create each order pane.
		for (int key : sortedTreeMap.keySet()) {
			
			ordersPane.getChildren().add(new OrderPane(sortedTreeMap.get(key)));

<<<<<<< Upstream, based on master
=======
//			System.out.println("created order pane for order: "+ key);
>>>>>>> 311b984 Can't remember what I changed in here but it would have been super minor
		}
		
	}
	
	
	public void createTestOrders() {
	    
	    //Test categories
	    Category salad = new Category("Salad", 0);
        Category patty = new Category("Patty", 1);
		
		//Test categories
		Category salad = new Category("Salad",2);
		Category patty = new Category("Patty",1);
		
		//Test ingredients.
<<<<<<< Upstream, based on master
		Ingredient lettuce = new Ingredient(salad,"Lettuce",300,10,1.00);
		Ingredient tomato = new Ingredient(salad,"Tomato",300,10,1.00);
		Ingredient beef = new Ingredient(patty,"Beef",300,10,1.00);
=======
		Ingredient lettuce = new Ingredient(salad, "Lettuce",300,10,1.00);
		Ingredient tomato = new Ingredient(salad, "Tomato",300,10,1.00);
		Ingredient beef = new Ingredient(patty, "Beef",300,10,1.00);
>>>>>>> 2844983 Minor alterations to remove errors so that it would run safely

		//Test ingredient map.
		Map<Ingredient, Integer> ingredients = new HashMap();
		ingredients.put(lettuce, 1);
		ingredients.put(tomato, 2);
		ingredients.put(beef, 1);
		
		for (int i = 0; i <= 16; i++) {
			Order order = new Order(i,"John",ingredients,5.50);
			orders.put(i, order);
			
			if (i <= 3) { }
			else if (i <= 8) updateStatus(order.getId(),Order.IN_PROGRESS,false);
			else if (i <= 12) updateStatus(order.getId(),Order.COMPLETE,false);
			else if (i <= 16) updateStatus(order.getId(),Order.COLLECTED,false);
			
		}
		
		
	}
	


}
