package client;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.Tab;

public class OrdersUI extends Tab {
    private ClientConnection client;
    private volatile Map<String, Order> orders;
    private Order currentOrder;

    public OrdersUI(ClientConnection client) {
        this.client = client;
        this.orders = new HashMap<String, Order>();
    }
    
    public void add(Order order) {
        //TODO
    }
    
    private synchronized void complete(String order) {
        //TODO
    }
    
    public synchronized Order getOrder(String order) {
        return orders.get(order);
    }

}
