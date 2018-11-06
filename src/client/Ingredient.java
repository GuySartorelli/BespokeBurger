package client;

public class Ingredient implements Comparable<Ingredient> {
    private Category category;
    private String name;
    private double price;
    private int quantity;
    private int minThreshold;

    public Ingredient(Category category, String name, int quantity, int minThreshold, double price) {
        this.category = category;
        this.name = name;
        this.quantity = quantity;
        this.minThreshold = minThreshold;
        this.price = price;
    }
    
    public int getQuantity() {
        
    }
    
    public void setQuantity(int quantity) {
        
    }
    
    public double getPrice() {
        
    }
    
    public void setPrice(double price) {
        
    }
    
    public Category getCategory() {
        
    }
    
    public String getName() {
        
    }
    
    public int getMinThreshold() {
        
    }
    
    public void setMinThreshold(int threshold) {
        
    }
    
    @Override
    public int compareTo(Ingredient other) {
        int categoryComparison = this.category.compareTo(other.category);
        return (categoryComparison == 0) ? this.name.compareTo(other.name) : categoryComparison;
    }

}
