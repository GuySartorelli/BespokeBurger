package server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

/**
 * Handles all queries, updates, and table alterations to/from the database
 * @author Bespoke Burgers
 *
 */
public class Database {
    //JDBC constants
    private static final String USERNAME = ;
    private static final String PASSWORD = ;
    private static final String DB_URL = ;

    /**
     * Convenience method for connecting to the database per request 
     * @return Connection to the database
     */
    private static Connection connect() {
        
    }
    
    /**
     * Convenience method to handle all SQL queries
     * @param query String: contains the SQL structured query
     * @return ResultSet containing response from database
     */
    private static ResultSet query(String query) {
        
    }
    
    /**
     * Convenience method to handle all SQL updates
     * @param update String: contains the SQL structured update request
     * @return String containing the response from database
     */
    private static String update(String update) {
        
    }
    
    /**
     * Get all orders from the database in the format "[id,customerName,ingredientName,num,ingredientName,num;id etc]"
     * @return String representing all orders in the database
     */
    public static String getOrders(){
        
    }
    
    /**
     * Enter a new order into the database
     * @param id unique identifier for this order
     * @param ingredients list of all ingredients and their quantity
     * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
     */
    public static short newOrder(int id, String ingredients) {
        
    }
    
    /**
     * Get all ingredients from the database in the format "ingredient1,num,minThreshold,price,category,ingredient2,num,minThreshold,price,category etc"
     * @return String representing all ingredients in the database
     */
    public static String getIngredients(){
        
    }
    
    /**
     * Add a new ingredient to the database
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     * @param category String: category of the ingredient (e.g. salad)
     * @param quantity int: number of ingredient in stock
     * @param minThreshold int: minimum number in stock before shop is notified to restock
     * @param price double: cost per unit added for customer
     * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
     */
    public static short addIngredient(String ingredient, String category, int quantity, int minThreshold, double price) {
        
    }
    
    /**
     * Remove an ingredient from the database
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
     */
    public static short removeIngredient(String ingredient) {
        
    }
    
    /**
     * Update the price of an ingredient in the database
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     * @param price double: cost per unit added for customer
     * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
     */
    public static short updatePrice(String ingredient, double price) {
        
    }
    
    /**
     * Increase the number of an ingredient in stock
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     * @param byAmount int: number by which to increase the quantity
     * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
     */
    public static short increaseQty(String ingredient, int byAmount) {
        
    }
    
    /**
     * 
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     * @param byAmount
     * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
     */
    public static short decreaseQty(String ingredient, int byAmount) {
        
    }
    
    /**
     * Get all categories from the database in the format "category1,category2,category2 etc"
     * Note that category order is the order in which they are to be displayed.
     * @return String representing all categories in the database
     */
    public static String getCategories(){
        
    }
    
    /**
     * Provide a new ordering for a category in the database
     * @param category String: ingredient category (e.g. salad)
     * @param newOrder int: number representing the order for this category (e.g 1 is the first category to be displayed)
     * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
     */
    public static short reorderCategory(String category, int newOrder) {
        
    }
    
    /**
     * Add a new category to the database
     * @param category String: ingredient category (e.g. salad)
     * @param order int: number representing the order for this category (e.g 1 is the first category to be displayed)
     * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
     */
    public static short addCategory(String category, int order) {
        
    }
    
    /**
     * Remove a category from the database
     * @param category String: ingredient category (e.g. salad)
     * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
     */
    public static short removeCategory(String category) {
        
    }

}
