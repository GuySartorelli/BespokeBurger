package client;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

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
		GridPane mainLayout = new GridPane();
		this.setContent(mainLayout);

	}
    
    /**
     * Adds a new category to the categories map and UI and sends that instruction through the client connection (unless the instruction originated from the server)
     * @param category Category: the category to be added
     * @param fromServer boolean: true if this method is being called from the ClientConnection object
     */
    public void addCategory(Category category, boolean fromServer) {
        if (categories.containsKey(category.getName())){
            categories.get(category.getName()).setOrder(category.getOrder());
        } else {
            categories.put(category.getName(), category);
        }
        if (!fromServer) client.addCategory(category);
    }
    
    /**
     * Removes a category from the categories map and sends that instruction through the client connection (unless the instruction originated from the server)
     * @param category String: ingredient category to be removed (e.g. salad)
     * @param fromServer boolean: true if this method is being called from the ClientConnection object
     */
    public synchronized void removeCategory(String category, boolean fromServer) {
        if (!categories.get(category).isEmpty()){
            //TODO add "are you sure?" if category has ingredients
        }
        categories.remove(category);
        if (!fromServer) client.removeCategory(category);
    }
    
    /**
     * Updates the price of an ingredient and sends that instruction through the client connection (unless the instruction originated from the server)
     * @param category String: ingredient category (e.g. salad)
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     * @param price double: cost to customer per unit
     * @param fromServer boolean: true if this method is being called from the ClientConnection object
     */
    public void updatePrice(String category, String ingredient, double price, boolean fromServer) {
        categories.get(category).getIngredient(ingredient).setPrice(price);
        if (!fromServer) client.updatePrice(category, ingredient, price);
    }
    
    /**
     * Increases the quantity of an ingredient and sends that instruction through the client connection (unless the instruction originated from the server)
     * @param category String: ingredient category (e.g. salad)
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     * @param byAmount int: number by which to increase the quantity
     * @param fromServer boolean: true if this method is being called from the ClientConnection object
     */
    public synchronized void increaseQty(String category, String ingredient, int byAmount, boolean fromServer) {
        Ingredient ing = categories.get(category).getIngredient(ingredient);
        ing.setQuantity(ing.getQuantity() + byAmount);
        if (!fromServer) client.increaseQty(category, ingredient, byAmount);
    }
    
    /**
     * Decreases the quantity of an ingredient and sends that instruction through the client connection (unless the instruction originated from the server)
     * @param category String: ingredient category (e.g. salad)
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     * @param byAmount int: number by which to decrease the quantity
     * @param fromServer boolean: true if this method is being called from the ClientConnection object
     */
    public synchronized void decreaseQty(String category, String ingredient, int byAmount, boolean fromServer) {
        Ingredient ing = categories.get(category).getIngredient(ingredient);
        ing.setQuantity(ing.getQuantity() - byAmount);
        if (!fromServer) client.decreaseQty(category, ingredient, byAmount);
    }
    
    /**
     * Sets the minimum acceptable quantity for an ingredient and sends that instruction through the client connection (unless the instruction originated from the server)
     * @param category String: ingredient category (e.g. salad)
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     * @param threshold int: minimum number in stock before shop is notified to restock
     * @param fromServer boolean: true if this method is being called from the ClientConnection object
     */
    public synchronized void setMinThreshold(String category, String ingredient, int threshold, boolean fromServer) {
        categories.get(category).getIngredient(ingredient).setMinThreshold(threshold);
        if (!fromServer) client.setMinThreshold(category, ingredient, threshold);
    }
    
    /**
     * Adds a new ingredient to the appropriate category and UI and sends that instruction through the client connection (unless the instruction originated from the server)
     * @param ingredient Ingredient: the ingredient to be added
     * @param fromServer boolean: true if this method is being called from the ClientConnection object
     */
    public void addIngredient(Ingredient ingredient, boolean fromServer) {
        ingredient.getCategory().addIngredient(ingredient);
        if (!fromServer) client.addIngredient(ingredient);
    }
    
    /**
     * Removes an ingredient from the appropriate category and sends that instruction through the client connection (unless the instruction originated from the server)
     * @param category String: ingredient category (e.g. salad)
     * @param ingredient String: name of the ingredient to be removed (e.g. lettuce)
     * @param fromServer boolean: true if this method is being called from the ClientConnection object
     */
    public synchronized void removeIngredient(String category, String ingredient, boolean fromServer) {
        categories.get(category).removeIngredient(ingredient);
        if (!fromServer) client.removeIngredient(category, ingredient);
    }
    
    /**
     * Returns the specified category
     * @param category String: ingredient category (e.g. salad)
     * @return Category: the specified category
     */
    public Category getCategory(String category) {
        return categories.get(category);
    }
    
    /**
     * Returns the specified ingredient from the specified category
     * @param category String: ingredient category (e.g. salad)
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     * @return Ingredient: the specified ingredient
     */
    public Ingredient getIngredient(String category, String ingredient) {
        return categories.get(category).getIngredient(ingredient);
    }
    
}
