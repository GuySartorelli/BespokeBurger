package client;

import java.util.Map;

public class Order {
    //statuses
    public static final String PENDING = "pending";
    public static final String IN_PROGRESS = "in progress";
    public static final String COMPLETE = "complete";
    public static final String COLLECTED = "collected";

    private int id;
    private String customer;
    private Map<Ingredient, Integer> ingredients;
    private double price;
    private String status;

    public Order(int id, String customer, Map<Ingredient, Integer> ingredients, double price) {
        this.id = id;
        this.customer = customer;
        this.ingredients = ingredients;
        this.price = price;
    }
    
    public int getId() {
        
    }
    
    public String getCustomer() {
        
    }
    
    public Map<Ingredient, Integer> getIngredients(){
        
    }
    
    public double getPrice() {
        
    }
    
    public String getStatus() {
        
    }
    
    public void setStatus(String status) {
        
    }

}
