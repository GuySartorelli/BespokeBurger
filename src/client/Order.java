package client;

import java.util.Map;

/**
 * Holds specific information about order state (e.g. ingredients, price, and status) for display.
 * @author Bespoke Burgers
 *
 */
public class Order {
    //statuses
    /**This order has been recieved in the store but is not yet being made*/
    public static final String PENDING = "pending";
    /**This order is currently being made*/
    public static final String IN_PROGRESS = "in progress";
    /**This order has been made but has not yet been collected by the customer*/
    public static final String COMPLETE = "complete";
    /**This order has been collected by the customer*/
    public static final String COLLECTED = "collected";

    private int id;
    private String customer;
    private Map<Ingredient, Integer> ingredients;
    private double price;
    private String status;

    /**
     * Constructor
     * @param id int: the order number (unique per day)
     * @param customer: the name of the customer who created the order
     * @param ingredients {@literal Map<Ingredient,Integer>}: a map of ingredients included in the order and the number of units for each ingredient
     * @param price double: the total cost for the order
     */
    public Order(int id, String customer, Map<Ingredient, Integer> ingredients, double price) {
        this.id = id;
        this.customer = customer;
        this.ingredients = ingredients;
        this.price = price;
        this.status = PENDING;
    }
    
    /**
     * Returns the order number for this order
     * @return int: the order number for this order
     */
    public int getId() {
      //TODO
    }
    
    /**
     * Returns the name of the customer who created this order
     * @return String: the name of the customer who created this order
     */
    public String getCustomer() {
      //TODO
    }
    
    /**
     * Returns a map of ingredients included in this order and the number of units for each ingredient
     * @return {@literal Map<Ingredient,Integer>}: a map of ingredients included in this order and the number of units for each ingredient
     */
    public Map<Ingredient, Integer> getIngredients(){
      //TODO
    }
    
    /**
     * Returns the total cost for this order
     * @return double: the total cost for this order
     */
    public double getPrice() {
      //TODO
    }
    
    /**
     * Returns the current status of this order
     * @return String: the current status of this order
     */
    public String getStatus() {
      //TODO
    }
    
    /**
     * Sets the current status of this order
     * @param status String: the new status of this order
     */
    public void setStatus(String status) {
      //TODO
    }

}
