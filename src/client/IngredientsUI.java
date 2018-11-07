package client;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.Tab;

/**
 * GUI layout for ingredients tab.<br>
 * Sends information about changes regarding categories and ingredients to ClientConnection object.<br>
 * Receives instructions from server via ClientConnection object and forwards them to the appropriate Category and Ingredient objects.
 * @author Bespoke Burgers
 *
 */
public class IngredientsUI extends Tab {
    private ClientConnection client;
    private volatile Map<String, Category> categories;

    /**
     * Constructor
     * @param client ClientConnection: connection through which information can be sent to other clients
     */
    public IngredientsUI(ClientConnection client) {
        this.client = client;
        this.categories = new HashMap<String, Category>();
        setupIngredientsTab();
    }
    
   
    /**
     * Sets up the format of the ingredients tab.
     */
	public void setupIngredientsTab() {
		
		this.setText("Ingredients");
		

	}
    
    /**
     * Adds a new category to the categories map and UI and sends that instruction through the client connection (unless the instruction originated from the server)
     * @param category Category: the category to be added
     * @param fromServer boolean: true if this method is being called from the ClientConnection object
     */
    public void addCategory(Category category, boolean fromServer) {
      //TODO
    }
    
    /**
     * Removes a category from the categories map and sends that instruction through the client connection (unless the instruction originated from the server)
     * @param category String: ingredient category to be removed (e.g. salad)
     * @param fromServer boolean: true if this method is being called from the ClientConnection object
     */
    public synchronized void removeCategory(String category, boolean fromServer) {
      //TODO
    }
    
    /**
     * Updates the price of an ingredient and sends that instruction through the client connection (unless the instruction originated from the server)
     * @param category String: ingredient category (e.g. salad)
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     * @param price double: cost to customer per unit
     * @param fromServer boolean: true if this method is being called from the ClientConnection object
     */
    public void updatePrice(String category, String ingredient, double price, boolean fromServer) {
      //TODO
    }
    
    /**
     * Increases the quantity of an ingredient and sends that instruction through the client connection (unless the instruction originated from the server)
     * @param category String: ingredient category (e.g. salad)
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     * @param byAmount int: number by which to increase the quantity
     * @param fromServer boolean: true if this method is being called from the ClientConnection object
     */
    public synchronized void increaseQty(String category, String ingredient, int byAmount, boolean fromServer) {
      //TODO
    }
    
    /**
     * Decreases the quantity of an ingredient and sends that instruction through the client connection (unless the instruction originated from the server)
     * @param category String: ingredient category (e.g. salad)
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     * @param byAmount int: number by which to decrease the quantity
     * @param fromServer boolean: true if this method is being called from the ClientConnection object
     */
    public synchronized void decreaseQty(String category, String ingredient, int byAmount, boolean fromServer) {
      //TODO
    }
    
    /**
     * Sets the minimum acceptable quantity for an ingredient and sends that instruction through the client connection (unless the instruction originated from the server)
     * @param category String: ingredient category (e.g. salad)
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     * @param threshold int: minimum number in stock before shop is notified to restock
     * @param fromServer boolean: true if this method is being called from the ClientConnection object
     */
    public synchronized void setMinThreshold(String category, String ingredient, int threshold, boolean fromServer) {
      //TODO
    }
    
    /**
     * Adds a new ingredient to the appropriate category and UI and sends that instruction through the client connection (unless the instruction originated from the server)
     * @param ingredient Ingredient: the ingredient to be added
     * @param fromServer boolean: true if this method is being called from the ClientConnection object
     */
    public void addIngredient(Ingredient ingredient, boolean fromServer) {
      //TODO
    }
    
    /**
     * Removes an ingredient from the appropriate category and sends that instruction through the client connection (unless the instruction originated from the server)
     * @param category String: ingredient category (e.g. salad)
     * @param ingredient String: name of the ingredient to be removed (e.g. lettuce)
     * @param fromServer boolean: true if this method is being called from the ClientConnection object
     */
    public synchronized void removeIngredient(String category, String ingredient, boolean fromServer) {
      //TODO
    }
    
    /**
     * Returns the specified category
     * @param category String: ingredient category (e.g. salad)
     * @return Category: the specified category
     */
    public synchronized Category getCategory(String category) {
      //TODO
    }
    
    /**
     * Returns the specified ingredient from the specified category
     * @param category String: ingredient category (e.g. salad)
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     * @return Ingredient: the specified ingredient
     */
    public synchronized Ingredient getIngredient(String category, String ingredient) {
      //TODO
    }
    
}
