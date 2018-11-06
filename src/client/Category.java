package client;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Category implements Comparable<Category> {
    private String name;
    private int order;
    private Map<String, Ingredient> ingredients;

    public Category(String name, int order) {
        
    }
    
    public Category(String name, int order, List<Ingredient> ingredients) {
        
    }
    
    public void addIngredient(Ingredient ingredient) {
        
    }
    
    public void removeIngredient(String ingredient) {
        
    }
    
    public int getOrder() {
        
    }
    
    public void setOrder(int newOrder) {
        
    }
    
    public String getName() {
        
    }
    
    public Ingredient getIngredient(String ingredient) {
        
    }
    
    public Collection<Ingredient> getIngredients(){
        return ingredients.values();
    }
    
    public int compareTo(Category other) {
        
    }

}
