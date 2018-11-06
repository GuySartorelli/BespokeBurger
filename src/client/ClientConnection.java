package client;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * Processes incoming data and calls appropriate methods on IngredientsUI, OrdersUI, Ingredient, Category, and Order objects.<br>
 * Receives information from IngredientsUI, OrdersUI, Ingredient, Category, and Order objects and forwards that information through the server to the database and web server.

 * @author Guy Sartorelli
 *
 */
public class ClientConnection implements Runnable {
    private final String SERVER_IP = ;
    private final int SERVER_PORT = 9090;
    private IngredientsUI ingredientsUI;
    private OrdersUI ordersUI;
    private PrintWriter serverOut;
    private BufferedReader serverIn;

    public ClientConnection(IngredientsUI ingredientsUI, OrdersUI ordersUI) {
        this.ingredientsUI = ingredientsUI;
        this.ordersUI = ordersUI;
        new Thread(this);
    }

    /**
     * Runs on a separate thread to the rest of the client.
     * Incoming data will be caught and processed here.
     */
    @Override
    public void run() {
        //TODO Add listener while loop here
    }
    
    /**
     * Sends a update of order status for other store client displays.
     * @param orderID String: identifier of the order
     * @param status String: new status
     * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
     */
    public short updateStatus(String orderID, String status) {
      //TODO
    }
    
    /**
     * Sends an updated price for a specific ingredient.
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     * @param price double: cost to customer per unit
     * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
     */
    public short updatePrice(String ingredient, double price) {
      //TODO
    }
    
    /**
     * Sends a request to increase the number of a specific ingredient held in stock
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     * @param byAmount int: number by which to increase the quantity
     * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
     */
    public short increaseQty(String ingredient, int byAmount) {
      //TODO
    }
    
    /**
     * Sends a request to increase the number of a specific ingredient held in stock
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     * @param byAmount int: number by which to decrease the quantity
     * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
     */
    public short decreaseQty(String ingredient, int byAmount) {
      //TODO
    }
    
    /**
     * Sends an update of the minimum acceptable quantity for a specific ingredient
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     * @param threshold int: minimum number in stock before shop is notified to restock
     * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
     */
    public short setMinThreshold(String ingredient, int threshold) {
      //TODO
    }
    
    /**
     * Adds a new ingredient to the system
     * @param ingredient Ingredient: the ingredient object to be added
     * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
     */
    public short addIngredient(Ingredient ingredient) {
      //TODO
    }
    
    /**
     * Removes an ingredient from the system
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
     */
    public short removeIngredient(String ingredient) {
      //TODO
    }
    
    /**
     * Sends an update of the order for a specific category
     * @param category String: ingredient category (e.g. salad)
     * @param newOrder int: number representing the order for this category (e.g 1 is the first category to be displayed)
     * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
     */
    public short reorderCategory(String category, int newOrder) {
      //TODO
    }
    
    /**
     * Add a new category to the system
     * @param category String: ingredient category (e.g. salad)
     * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
     */
    public short addCategory(Category category) {
      //TODO
    }
    
    /**
     * Remove a category from the system
     * @param category String: ingredient category (e.g. salad)
     * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
     */
    public short removeCategory(String category) {
      //TODO
    }

}
