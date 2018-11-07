package client;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * A category of ingredients (e.g. salad).<br>
 * Provides an easy way to order and separate ingredients for display.
 * @author Bespoke Burgers
 *
 */
public class Category implements Comparable<Category> {
    private String name;
    private int order;
    private Map<String, Ingredient> ingredients;

    /**
     * Constructor to use if the list of ingredients is unknown
     * @param name String: name of category (e.g. salad)
     * @param order int: number representing the order for this category (e.g 1 is the first category to be displayed)
     */
    public Category(String name, int order) {
        this.name = name;
        this.order = order;
        this.ingredients = new HashMap<String, Ingredient>();
    }
    
    /**
     * Constructor to use if the list of ingredients is known
     * @param name String: name of category (e.g. salad)
     * @param order int: number representing the order for this category (e.g 1 is the first category to be displayed)
     * @param ingredients List{@literal<Ingredient>}: list of ingredients contained in this category
     */
    public Category(String name, int order, List<Ingredient> ingredients) {
        this.name = name;
        this.order = order;
        this.ingredients = new HashMap<String, Ingredient>();
        for (Ingredient ingredient : ingredients) this.ingredients.put(ingredient.getName(), ingredient);
    }
    
    /**
     * Adds a new ingredient to the ingredients map
     * @param ingredient Ingredient: the ingredient to be added
     */
    public void addIngredient(Ingredient ingredient) {
        ingredients.put(ingredient.getName(), ingredient);
    }
    
    /**
     * Removes an ingredient from the ingredients map 
     * @param ingredient String: name of the ingredient to be removed (e.g. lettuce)
     */
    public void removeIngredient(String ingredient) {
        ingredients.remove(ingredient);
    }
    
    /**
     * Returns the order number for this category
     * @return int: the order number for this category
     */
    public int getOrder() {
        return this.order;
    }
    
    /**
     * Sets the order number for this category
     * @param newOrder int: number representing the order for this category (e.g 1 is the first category to be displayed)
     */
    public void setOrder(int newOrder) {
        this.order = newOrder;
    }
    
    /**
     * Returns the name of this category
     * @return String: the name of this category
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Returns the specified ingredient
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     * @return Ingredient: the specified ingredient
     */
    public Ingredient getIngredient(String ingredient) {
        return ingredients.get(ingredient);
    }
    
    /**
     * Returns a list of all ingredients in this category
     * @return List{@literal<Ingredient>}: a list of all ingredients in this category
     */
    public List<Ingredient> getIngredients(){
        return new ArrayList<Ingredient>(ingredients.values());
    }
    
    @Override
    public int compareTo(Category other) {
        return this.order - other.order;
    }

}
