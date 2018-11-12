package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static protocol.Protocol.*;

/**
 * Handles all queries, updates, and table alterations to/from the database
 * @author Bespoke Burgers
 *
 */
public class Database {
	//JDBC constants
	private static final String USERNAME = "zhanglian4" ;
	private static final String PASSWORD = "123";
	private static final String DB_URL = "jdbc:postgresql://db.ecs.vuw.ac.nz/zhanglian4_jdbc";


	/**
	 * Convenience method for connecting to the database per request 
	 * @return Connection to the database
	 */
	private boolean logIn()
	{
		boolean res = true;
		
		try
		{
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection(DB_URL,USERNAME, PASSWORD);
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from orders where order_number =1001");
			rs.next();

			String customerName = rs.getString("customer_name");
//			System.out.println(customerName);

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
	
	private static Statement connect() {
		Statement s = null;
		try {
			Connection connection = DriverManager.getConnection(DB_URL,USERNAME, PASSWORD); 
			s = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * Convenience method to handle all SQL queries
	 * @param query String: contains the SQL structured query
	 * @return ResultSet containing response from database
	 */
	private static ResultSet query(String query){
		//TODO
		ResultSet rs = null;
		
		try {
			Statement s = connect();
			rs = s.executeQuery(query);

		}catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return rs;
	}


	/**
	 * Convenience method to handle all SQL updates
	 * @param update String: contains the SQL structured update request
	 * @return String containing the response from database
	 */
	private static short update(String update) {
		//TODO
		short feedback = 0;
		
		try {
			Statement s = connect();
			ResultSet rs = s.executeQuery(update);
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			String[] result = e.toString().split(":");
			if (result[1].toString().contains("ERROR")){feedback = -1;}
			if (result[1].toString().contains(" No results were returned by the query.")) {feedback = 1;}
		} 
		
		return feedback;
	}

	/**
	 * Get all orders from the database in the format "[id,customerName,ingredientName,num,ingredientName,num;id etc]"
	 * @return String representing all orders in the database
	 */
	public static String getOrders() {
		//TODO

		String result = null;

		ResultSet rs = query("select * from orders");

		try {
			while(rs.next()) {
				int orderNumber = rs.getInt("order_number");
				String customerName = rs.getString("customer_name");
				String orderDetails = rs.getString("order_details");
				int cost =rs.getInt("cost");
				String timeStamp = rs.getString("time_stamp");
				result = String.valueOf(orderNumber) +" " + customerName + orderDetails + String.valueOf(cost) +" " + timeStamp;
//				System.out.println(result);
			} 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return result;
	}

	/**
	 * Enter a new order into the database
	 * @param id unique identifier for this order
	 * @param ingredients list of all ingredients and their quantity
	 * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
	 */
	public static short newOrder(int id, String ingredients) {
		//TODO
		short feedback = update("insert into orders(order_number, customer_name, order_details, cost, time_stamp) "
				+ "values (" + id + "," + "'customer_name'," + " '" +ingredients + "', 15, '07-11-2018 09:00' )");
		return feedback;
	}



	/**
	 * Get all ingredients from the database in the format "ingredient1,num,minThreshold,price,category,ingredient2,num,minThreshold,price,category etc"
	 * @return String representing all ingredients in the database
	 */
	public static String getIngredients(){
		//TODO
		String result = null;

		ResultSet rs = query("select * from ingredients");
		try {
			while(rs.next()) {

				String ingredientName = rs.getString("ingredient_name");
				double price = rs.getDouble("price");
				int quantity =rs.getInt("quantity");
				String category = rs.getString("category");
				int minThreshold = rs.getInt("minThreshold");
				result = ingredientName + String.valueOf(price) + " "+ 
						String.valueOf(quantity) +" " + category + " " + String.valueOf(minThreshold);
//				System.out.println(result);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return result;	
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
	public static short addIngredient(String ingredient, double price, int quantity, String category,  int minThreshold) {
		//TODO
		short feedback = update("insert into ingredients (ingredient_name, price, quantity, category, minThreshold) "
				+ "values (" + "'"+ ingredient +"'" + ", " + price + ", " + quantity +", " + "'" + category + "'" + ", " + minThreshold + ")");
		return feedback;
	}

	/**
	 * Remove an ingredient from the database
	 * @param ingredient String: name of the ingredient (e.g. lettuce)
	 * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
	 */
	public static short removeIngredient(String ingredient){
		//TODO
		short feedback = 0;
		
		try {
			ResultSet rs = query("select * from ingredients where ingredient_name = '" + ingredient + "'");
			if (!rs.next()) { 
				feedback = 0;
				System.out.println(feedback);
				return feedback;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		feedback = update("delete from ingredients where ingredient_name = "+ "'" + ingredient + "'");
//		System.out.println(feedback);
		
		return feedback;
	}
	
	/**
	 * Update the price of an ingredient in the database
	 * @param ingredient String: name of the ingredient (e.g. lettuce)
	 * @param price double: cost to customer per unit
	 * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
	 */
	public static short updatePrice(String ingredient, double price) {
		//TODO
		short feedback = update("update ingredients set price = "+ price + " where ingredient_name = " + "'"
				+ingredient + "' ");
//		System.out.println(feedback);
		return feedback;
	}

	/**
	 * Increase the number of an ingredient in stock
	 * @param ingredient String: name of the ingredient (e.g. lettuce)
	 * @param byAmount int: number by which to increase the quantity
	 * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
	 */
	public static short increaseQty(String ingredient, int byAmount){
		//TODO
		short feedback = update("update ingredients set quantity = quantity +"+ byAmount + " where ingredient_name = " + "'"
				+ingredient + "' ");
//		System.out.println(feedback);
		return feedback;
	}

	/**
	 * Decrease the number of an ingredient in stock
	 * @param ingredient String: name of the ingredient (e.g. lettuce)
	 * @param byAmount int: number by which to decrease the quantity
	 * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
	 */
	public static short decreaseQty(String ingredient, int byAmount){
		//TODO
		short feedback = 0;
		
		try {
			ResultSet rs = query("select quantity from ingredients where ingredient_name = '" + ingredient + "'");
			rs.next();
			int quantity = rs.getInt("quantity");
			if (quantity <= 0 || quantity < byAmount) {
				feedback = 0;
				System.out.println(feedback);
				return feedback;
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		feedback = update("update ingredients set quantity = quantity -"+ byAmount + " where ingredient_name = " + "'"
				+ingredient + "' ");
//		System.out.println(feedback);
		
		return feedback;
	}

	/**
	 * Update the minimum allowed quantity of a specific ingredient
	 * @param ingredient String: name of the ingredient (e.g. lettuce)
	 * @param threshold int: minimum number in stock before shop is notified to restock
	 * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
	 */
	public static short updateThreshold(String ingredient, int threshold){
		//TODO
		short feedback = 0;
		
		try {
			ResultSet rs = query("select minthreshold from ingredients where ingredient_name = '" + ingredient + "'");
			int minthreshold = rs.getInt("minthreshold");
			if (threshold <= 0 || minthreshold <= 0) {
				feedback = 0;
				System.out.println(feedback);
				return feedback;
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		feedback = update("update ingredients set minThreshold = "+ threshold + " where ingredient_name = " + "'"
				+ingredient + "' ");
//		System.out.println(feedback);
		
		return feedback;
	}

	/**
	 * Get all categories from the database in the format "category1,category2,category2 etc"
	 * Note that category order is the order in which they are to be displayed.
	 * @return String representing all categories in the database
	 */
	public static String getCategories() {
		//TODO
		String result = null;
		
		try {
			ResultSet rs = query("select category from category order by sequence");
			while(rs.next()) {
				String category = rs.getString("category");
				result = result + category;
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return result;
	}

	/**
	 * Provide a new ordering for a category in the database
	 * @param category String: ingredient category (e.g. salad)
	 * @param newOrder int: number representing the order for this category (e.g 1 is the first category to be displayed)
	 * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
	 */
	public static short reorderCategory(String category, int newOrder){
		//TODO
		short feedback = update("update category set sequence = " + newOrder +"where category = '" + category + "'");
//		System.out.println(feedback);
		return feedback;
	}

	/**
	 * Add a new category to the database
	 * @param category String: ingredient category (e.g. salad)
	 * @param order int: number representing the order for this category (e.g 1 is the first category to be displayed)
	 * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
	 */
	public static short addCategory(String category, int order) {
		short feedback = update("insert into category (category, sequence) values ('"+ category + "', " + order +")");
//		System.out.println(feedback);
		return feedback;
	}

	/**
	 * Remove a category from the database
	 * @param category String: ingredient category (e.g. salad)
	 * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
	 */
	public static short removeCategory(String category){
		short feedback = update("delete from category where category = '" + category +"'");
//		System.out.println(feedback);
		return feedback;

	}

}
