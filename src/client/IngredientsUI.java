package client;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.Tab;

public class IngredientsUI extends Tab {
    private ClientConnection client;
    private volatile Map<String, Category> categories;

    public IngredientsUI(ClientConnection client) {
        this.client = client;
        this.categories = new HashMap<String, Category>;
    }
    
    public void addCategory(Category category) {
        
    }
    
    public synchronized void removeCategory(String Category) {
        
    }
    
    public void updatePrice(String ingredient, double price) {
        
    }
    
    public synchronized void increaseQty(String ingredient, int byAmount) {
        
    }
    
    public synchronized void decreaseQty(String ingredient, int byAmount, boolean fromServer) {
        
    }
    
    public synchronized void setMinThreshold(String ingredient, int threshold) {
        
    }
    
    public void addIngredient(Ingredient ingredient) {
        
    }
    
    public synchronized void removeIngredient(String ingredient) {
        
    }
    
    public synchronized Category getCategory(String category) {
        
    }
    
    public synchronized Ingredient getIngredient(String Category, String Ingredient) {
        
    }
    
}
