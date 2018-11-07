package client;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.Tab;

/**
 * GUI layout for Orders tab.<br>
 * Sends completed orders to server for storage in database.<br>
 * Receives incoming orders from server via ClientConnection object.
 * @author Bespoke Burgers
 *
 */
public class OrdersUI extends Tab {
    private ClientConnection client;
    private volatile Map<String, Order> orders;
    private Order currentOrder;

    /**
     * Constructor
     * @param client ClientConnection: connection through which information can be sent to other clients
     */
    public OrdersUI(ClientConnection client) {
        this.client = client;
        this.orders = new HashMap<Integer, Order>();
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

}
