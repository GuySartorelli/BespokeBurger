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
    private HBox ordersHBox; //Where the orderPane objects are displayed.
    
    private Map<Integer, OrderPane> orderPanes; //Key is orderID, value is an orderPane object.
    
    

    /**
     * Constructor
     * @param client ClientConnection: connection through which information can be sent to other clients
     */
    public OrdersUI(ClientConnection client) {
        this.client = client;
        this.orders = new HashMap<Integer, Order>();
        this.orderPanes = new HashMap<Integer, OrderPane>();
        
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
        ordersHBox = new HBox();
        scrollPane = new ScrollPane(ordersHBox);

        this.setContent(scrollPane);
        
        //Setting the format of the ordersPane.
        ordersHBox.setSpacing(20);
        ordersHBox.setStyle("-fx-padding: 10 10 10 15");
        
        
        /////TESTING/////
        createTestOrders();
        refreshOrders();
	}
	
	public void refreshOrders() {
		
		//Creates a treeMap from the orders map so that it is ordered by the keys (order ID).
		Map<Integer, Order> sortedTreeMap = new TreeMap<Integer, Order>(orders);
		
		//Iterate through the treeMap to create each order pane.
		for (int key : sortedTreeMap.keySet()) {
			
			OrderPane orderPane = new OrderPane(sortedTreeMap.get(key),this);
			ordersHBox.getChildren().add(orderPane);
			orderPanes.put(key, orderPane);
		}
		
	}
	
	
	public void createTestOrders() {
		
		//Test categories
		Category bun = new Category("Bun",1);
		Category patty = new Category("Patty",2);
		Category salad = new Category("Salad",3);
		Category sauce = new Category("Sauce",4);
		
		//Test ingredients.
		Ingredient lettuce = new Ingredient(salad,"Lettuce",300,10,1.00);
		Ingredient tomato = new Ingredient(salad,"Tomato",300,10,1.00);
		Ingredient beef = new Ingredient(patty,"Beef",300,10,1.00);
		Ingredient sesame = new Ingredient(bun,"Sesame",300,10,1.00);
		
		Ingredient bbqSauce = new Ingredient(sauce,"BBQ",300,10,1.00);
		Ingredient tomatoSauce = new Ingredient(sauce,"Tomato",300,10,1.00);

		//Test ingredient map.
		Map<Ingredient, Integer> ingredients = new HashMap();
		ingredients.put(lettuce, 1);
		ingredients.put(tomato, 2);
		ingredients.put(beef, 1);
		ingredients.put(sesame, 1);
		ingredients.put(bbqSauce, 1);
		ingredients.put(tomatoSauce, 1);
		
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
