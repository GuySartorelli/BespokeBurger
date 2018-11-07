package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
<<<<<<< Upstream, based on origin/master
=======
import java.sql.Statement;
import java.util.List;
>>>>>>> a974e42 order part finished

import static protocol.Protocol.*;

/**
 * Handles all queries, updates, and table alterations to/from the database
 * @author Bespoke Burgers
 *
 */
public class Database {
<<<<<<< Upstream, based on origin/master
    //JDBC constants
    private static final String USERNAME = "";
    private static final String PASSWORD = "";
    private static final String DB_URL = "";
=======
	//JDBC constants
	private static final String USERNAME = "zhanglian4" ;
	private static final String PASSWORD = "123";
	private static final String DB_URL = "jdbc:postgresql://db.ecs.vuw.ac.nz/"+"zhanglian4_jdbc";
>>>>>>> a974e42 order part finished

<<<<<<< Upstream, based on origin/master
    /**
     * Convenience method for connecting to the database per request 
     * @return Connection to the database
     * @throws SQLException 
     */
    private static Connection connect() throws SQLException {          
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Could not connect to database");
            e.printStackTrace();
            return null;
        }
        Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        return connection;
    }
    
    /**
     * Convenience method to handle all SQL queries
     * @param query String: contains the SQL structured query
     * @return ResultSet containing response from database
     * @throws SQLException 
     */
    private static ResultSet query(String query) throws SQLException {
        Connection db = connect();
        if (db == null) return null;
        ResultSet rs = db.createStatement().executeQuery(query);
        db.close();
        return rs;
    }
    
    /**
     * Convenience method to handle all SQL updates
     * @param update String: contains the SQL structured update request
     * @return String containing the response from database
     * @throws SQLException 
     */
    private static short update(String update) throws SQLException {
        Connection db = connect();
        if (db == null) return ERROR;
        short result = (short) db.createStatement().executeUpdate(update);
        db.close();
        return result;
    }
    
    
    
    /**
     * Get all orders from the database in the format "id,customerName,ingredientCategory,ingredientName,num,ingredientCategory,ingredientName,num;id etc"
     * @return String representing all orders in the database
     */
    public static String getOrders(){
        //TODO format the appropriate query to get all orders from the database and use query()
        //Alter the response from the database to match the format suggested in the javadoc above
    }
    
    /**
     * Enter a new order into the database
     * @param id unique identifier for this order
     * @param ingredients list of all ingredients and their quantity
     * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
     */
    public static short newOrder(int id, String ingredients) {
        //TODO Format the appropriate update statement and send to the database via update
        //return the appropriate value
    }
    
    /**
     * Get all ingredients from the database in the format "ingredient1,num,minThreshold,price,category,ingredient2,num,minThreshold,price,category etc"
     * @return String representing all ingredients in the database
     */
    public static String getIngredients(){
        //TODO format the appropriate query to get all ingredients from the database and use query()
        //Alter the response from the database to match the format suggested in the javadoc above
    }
    
    /**
     * Add a new ingredient to the database
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     * @param category String: category of the ingredient (e.g. salad)
     * @param quantity int: number of ingredient in stock
     * @param minThreshold int: minimum number in stock before shop is notified to restock
     * @param price double: cost to customer per unit
     * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
     */
    public static short addIngredient(String ingredient, String category, int quantity, int minThreshold, double price) {
        //TODO Format the appropriate update statement and send to the database via update
        //return the appropriate value
    }
    
    /**
     * Remove an ingredient from the database
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
     */
    public static short removeIngredient(String ingredient) {
        //TODO Format the appropriate update statement and send to the database via update
        //return the appropriate value
    }
    
    /**
     * Update the price of an ingredient in the database
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     * @param price double: cost to customer per unit
     * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
     */
    public static short updatePrice(String ingredient, double price) {
        //TODO Format the appropriate update statement and send to the database via update
        //return the appropriate value
    }
    
    /**
     * Increase the number of an ingredient in stock
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     * @param byAmount int: number by which to increase the quantity
     * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
     */
    public static short increaseQty(String ingredient, int byAmount) {
        //TODO Format the appropriate update statement and send to the database via update
        //return the appropriate value
    }
    
    /**
     * Decrease the number of an ingredient in stock
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     * @param byAmount int: number by which to decrease the quantity
     * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
     */
    public static short decreaseQty(String ingredient, int byAmount) {
        //TODO Format the appropriate update statement and send to the database via update
        //return the appropriate value
    }
    
    /**
     * Update the minimum allowed quantity of a specific ingredient
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     * @param threshold int: minimum number in stock before shop is notified to restock
     * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
     */
    public static short updateThreshold(String ingredient, int threshold) {
        //TODO Format the appropriate update statement and send to the database via update
        //return the appropriate value
    }
    
    /**
     * Get all categories from the database in the format "category1,category2,category3 etc"
     * Note that category order is the order in which they are to be displayed.
     * @return String representing all categories in the database
     */
    public static String getCategories(){
        //TODO format the appropriate query to get all categories from the database and use query()
        //Alter the response from the database to match the format suggested in the javadoc above
    }
    
    /**
     * Provide a new ordering for a category in the database
     * @param category String: ingredient category (e.g. salad)
     * @param newOrder int: number representing the order for this category (e.g 1 is the first category to be displayed)
     * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
     */
    public static short reorderCategory(String category, int newOrder) {
        //TODO Format the appropriate update statement and send to the database via update
        //return the appropriate value
    }
    
    /**
     * Add a new category to the database
     * @param category String: ingredient category (e.g. salad)
     * @param order int: number representing the order for this category (e.g 1 is the first category to be displayed)
     * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
     */
    public static short addCategory(String category, int order) {
        //TODO Format the appropriate update statement and send to the database via update
        //return the appropriate value
    }
    
    /**
     * Remove a category from the database
     * @param category String: ingredient category (e.g. salad)
     * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
     */
    public static short removeCategory(String category) {
        //TODO Format the appropriate update statement and send to the database via update
        //return the appropriate value
    }
=======

	/**
	 * Convenience method for connecting to the database per request 
	 * @return Connection to the database
	 */
	private boolean logIn()
	{
		boolean res = true;
		try
		{
			String databaseUser = "zhanglian4";
			String databaseUserPass = "123";           
			Class.forName("org.postgresql.Driver");
			Connection connection = null;            
			String url = "jdbc:postgresql://db.ecs.vuw.ac.nz/"+"zhanglian4_jdbc";
			connection = DriverManager.getConnection(url,databaseUser, databaseUserPass);
			Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery("select * from orders where order_number =1001");
			if(! rs.next())
				res = false;            
			rs.close();
			connection.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			res = false;
		}
		return res;
	}

	/**
	 * Convenience method to handle all SQL queries
	 * @param query String: contains the SQL structured query
	 * @return ResultSet containing response from database
	 * @throws SQLException 
	 */
	private static ResultSet query(String query) throws SQLException {
		//TODO
		Connection connection = DriverManager.getConnection(DB_URL,USERNAME, PASSWORD); 
		Statement s = connection.createStatement();
		ResultSet rs = s.executeQuery(query);
		return rs;
	}

	/**
	 * Convenience method to handle all SQL updates
	 * @param update String: contains the SQL structured update request
	 * @return String containing the response from database
	 * @throws SQLException 
	 */
	private static String update(String update) throws SQLException {
		//TODO
		String result;
		Connection connection = DriverManager.getConnection(DB_URL,USERNAME, PASSWORD); 
		Statement s = connection.createStatement();
		ResultSet rs = s.executeQuery(update);
		if(rs.next()) {
			result = "Updated succefully.";
		}else { result = "Update unsucceful.";}
		return result;
	}

	/**
	 * Get all orders from the database in the format "[id,customerName,ingredientName,num,ingredientName,num;id etc]"
	 * @return String representing all orders in the database
	 * @throws SQLException 
	 */
	public static String getOrders() throws SQLException{
		//TODO
		Connection connection = DriverManager.getConnection(DB_URL,USERNAME, PASSWORD); 
		Statement s = connection.createStatement();
		ResultSet rs = s.executeQuery("select * from orders");
		String result = null;
		while(rs.next()) {
			int orderNumber = rs.getInt("order_number");
			String customerName = rs.getString("customer_name");
			String orderDetails = rs.getString("order_details");
			int cost =rs.getInt("cost");
			String timeStamp = rs.getString("time_stamp");
			result = String.valueOf(orderNumber) +" " + customerName + orderDetails + String.valueOf(cost) +" " + timeStamp;
			System.out.println(result);
		} 
		return result;
	}

	/**
	 * Enter a new order into the database
	 * @param id unique identifier for this order
	 * @param ingredients list of all ingredients and their quantity
	 * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
	 * @throws SQLException 
	 */
	public static void newOrder(int id, String ingredients) throws SQLException {
		//TODO
		Connection connection = DriverManager.getConnection(DB_URL,USERNAME, PASSWORD); 
		Statement s = connection.createStatement();
		ResultSet rs = s.executeQuery("insert into orders(order_number, customer_name, order_details, cost, time_stamp) "
				+ "values (" + id + "," + "'customer_name'," + " '" +ingredients + "', 15, '07-11-2018 09:00' )");
	}

	/**
	 * Get all ingredients from the database in the format "ingredient1,num,minThreshold,price,category,ingredient2,num,minThreshold,price,category etc"
	 * @return String representing all ingredients in the database
	 */
	public static String getIngredients(){
		//TODO
	}

	/**
	 * Add a new ingredient to the database
	 * @param ingredient String: name of the ingredient (e.g. lettuce)
	 * @param category String: category of the ingredient (e.g. salad)
	 * @param quantity int: number of ingredient in stock
	 * @param minThreshold int: minimum number in stock before shop is notified to restock
	 * @param price double: cost to customer per unit
	 * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
	 */
	public static short addIngredient(String ingredient, String category, int quantity, int minThreshold, double price) {
		//TODO
	}

	/**
	 * Remove an ingredient from the database
	 * @param ingredient String: name of the ingredient (e.g. lettuce)
	 * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
	 */
	public static short removeIngredient(String ingredient) {
		//TODO
	}

	/**
	 * Update the price of an ingredient in the database
	 * @param ingredient String: name of the ingredient (e.g. lettuce)
	 * @param price double: cost to customer per unit
	 * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
	 */
	public static short updatePrice(String ingredient, double price) {
		//TODO
	}

	/**
	 * Increase the number of an ingredient in stock
	 * @param ingredient String: name of the ingredient (e.g. lettuce)
	 * @param byAmount int: number by which to increase the quantity
	 * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
	 */
	public static short increaseQty(String ingredient, int byAmount) {
		//TODO
	}

	/**
	 * Decrease the number of an ingredient in stock
	 * @param ingredient String: name of the ingredient (e.g. lettuce)
	 * @param byAmount int: number by which to decrease the quantity
	 * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
	 */
	public static short decreaseQty(String ingredient, int byAmount) {
		//TODO
	}

	/**
	 * Update the minimum allowed quantity of a specific ingredient
	 * @param ingredient String: name of the ingredient (e.g. lettuce)
	 * @param threshold int: minimum number in stock before shop is notified to restock
	 * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
	 */
	public static short updateThreshold(String ingredient, int threshold) {
		//TODO
	}

	/**
	 * Get all categories from the database in the format "category1,category2,category2 etc"
	 * Note that category order is the order in which they are to be displayed.
	 * @return String representing all categories in the database
	 */
	public static String getCategories(){
		//TODO
	}

	/**
	 * Provide a new ordering for a category in the database
	 * @param category String: ingredient category (e.g. salad)
	 * @param newOrder int: number representing the order for this category (e.g 1 is the first category to be displayed)
	 * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
	 */
	public static short reorderCategory(String category, int newOrder) {
		//TODO
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
>>>>>>> a974e42 order part finished

}
