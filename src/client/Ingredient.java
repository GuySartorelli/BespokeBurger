package client;

/**
 * Holds specific information about ingredient state (e.g. quantity and price) for display.
 * @author Bespoke Burgers
 *
 */
public class Ingredient implements Comparable<Ingredient> {
    private String category;
    private String name;
    private double price;
    private int quantity;
    private int minThreshold;

    /**
     * Constructor
     * @param category String: category of the ingredient (e.g. salad)
     * @param name String: name of the ingredient (e.g. lettuce)
     * @param quantity int: number of ingredient in stock
     * @param minThreshold int: minimum number in stock before shop is notified to restock
     * @param price double: cost to customer per unit
     */
    public Ingredient(String category, String name, int quantity, int minThreshold, double price) {
        this.category = category;
        this.name = name;
        this.quantity = quantity;
        this.minThreshold = minThreshold;
        this.price = price;
    }
    
    /**
     * Returns the current quantity of this ingredient
     * @return int: the current quantity of this ingredient
     */
    public int getQuantity() {
      //TODO
    }
    
    /**
     * Sets the quantity of this ingredient
     * @param quantity int: the new quantity of this ingredient
     */
    public void setQuantity(int quantity) {
      //TODO
    }
    
    /**
     * Returns the cost to customer per unit of this ingredient
     * @return double: cost to customer per unit of this ingredient
     */
    public double getPrice() {
      //TODO
    }
    
    /**
     * Sets the cost to customer per unit of this ingredient
     * @param price double: new cost to customer per unit of this ingredient
     */
    public void setPrice(double price) {
      //TODO
    }
    
    /**
     * Returns the category to which this ingredient belongs
     * @return Category: the category to which this ingredient belongs
     */
    public String getCategory() {
      //TODO
    }
    
    /**
     * Returns the name of this ingredient
     * @return String: the name of this ingredient
     */
    public String getName() {
      //TODO
    }
    
    /**
     * Returns the minimum acceptable quantity of this ingredient
     * @return int: the minimum acceptable quantity of this ingredient
     */
    public int getMinThreshold() {
      //TODO
    }
    
    /**
     * Sets the minimum acceptable quantity of this ingredient
     * @param threshold int: the new minimum acceptable quantity of this ingredient
     */
    public void setMinThreshold(int threshold) {
      //TODO
    }
    
    @Override
    public int compareTo(Ingredient other) {
        int categoryComparison = this.category.compareTo(other.category);
        return (categoryComparison == 0) ? this.name.compareTo(other.name) : categoryComparison;
    }

}
