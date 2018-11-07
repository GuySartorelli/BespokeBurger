package client;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static protocol.Protocol.*;

/**
 * Processes incoming data and calls appropriate methods on IngredientsUI, OrdersUI, Ingredient, Category, and Order objects.<br>
 * Receives information from IngredientsUI, OrdersUI, Ingredient, Category, and Order objects and forwards that information through the server to the database and web server.

 * @author Guy Sartorelli
 *
 */
public class ClientConnection implements Runnable {
    private final String SERVER_IP = "127.0.0.1";
    private final int SERVER_PORT = 9090;
    private boolean isConnected;
    private IngredientsUI ingredientsUI;
    private OrdersUI ordersUI;
    private Socket socket;
    private PrintWriter serverOut;
    private BufferedReader serverIn;

    public ClientConnection(IngredientsUI ingredientsUI, OrdersUI ordersUI) throws IOException {
        this.ingredientsUI = ingredientsUI;
        this.ordersUI = ordersUI;
        this.connect(SERVER_IP, SERVER_PORT);
    }

    /**
     * Runs on a separate thread to the rest of the client.
     * Incoming data will be caught and processed here.
     */
    @Override
    public void run() {
        while (this.isConnected) {
            try {
                String message = serverIn.readLine();
                process(message);
            } catch (EOFException e) {
                //Thrown when the server unexpectedly closes connection because it's stuck on in.readObject() when the connection terminates
                try { disconnect(); } catch (IOException e1) {
                    //no action needed
                }
                break;
            } catch (IOException e) {
                System.err.println("IO error");
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Connects to the requested server
     * @param ip String: The IP address to the server
     * @param port int: The port the server is listening to
     * @return boolean: true if connection was established
     * @throws IOException
     */
    public boolean connect(String ip, int port) throws IOException {
        try {
            socket = new Socket(ip, port);
            serverOut = new PrintWriter(socket.getOutputStream());
            serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            new Thread(this).start();
            send(REGISTER_AS+DELIM+STORE);
            this.isConnected = true;
        } catch (UnknownHostException e){ return false; }
        return true;
    }
    
    /**
     * Disconnects from the server
     * @throws IOException
     */
    public void disconnect() throws IOException {
        try{
            send(DEREGISTER);
            this.isConnected = false;
            socket.close();
        } catch (IOException e) {
            System.err.println("error disconnecting");
            this.isConnected = false;
            throw e;
        }
    }
    
    /**
     * Processes incoming data from server
     * @param input String: raw data as-sent from the server
     */
    private void process(String input) {
        String[] tokens = input.split(DELIM);
        String protocol = tokens[0];
        
        switch (protocol) {
        case ACKNOWLEDGE_DISCONNECT:
            break; //no action needed
            
        case DEREGISTER:
            try { disconnect(); } catch (IOException e) {
                //no action needed
            }
            break;
        
        case ""+ERROR:
            undoAction(input.split(ERROR+DELIM)[1]);
            break;
            
        case ""+FAILURE:
            undoAction(input.split(FAILURE+DELIM)[1]);
            break;
            
        case NEW_ORDER:
            Map<Ingredient, Integer> ingredientMap = new HashMap<Ingredient, Integer>();
            int id = Integer.parseInt(tokens[1]);
            String customer = tokens[2];
            for (int i = 3; i < tokens.length-1; i++) {
                String category = tokens[i++];
                String ingredientName = tokens[i++];
                int quantity = Integer.parseInt(tokens[i]);
                Ingredient ingredient = ingredientsUI.getIngredient(category, ingredientName);
                ingredientMap.put(ingredient, quantity);
            }
            double price = Double.parseDouble(tokens[tokens.length-1]);
            Order order = new Order(id, customer, ingredientMap, price);
            ordersUI.add(order);
            break;
            
        case UPDATE_STATUS:
            id = Integer.parseInt(tokens[1]);
            String status = tokens[2];
            ordersUI.updateStatus(id, status, true);
            break;
            
        case INCREASE_QUANTITY:
            String category = tokens[1];
            String ingredient = tokens[2];
            int byAmount = Integer.parseInt(tokens[3]);
            ingredientsUI.increaseQty(category, ingredient, byAmount, true);
            break;
            
        case DECREASE_QUANTITY:
            category = tokens[1];
            ingredient = tokens[2];
            byAmount = Integer.parseInt(tokens[3]);
            ingredientsUI.decreaseQty(category, ingredient, byAmount, true);
            break;
            
        case SET_THRESHOLD:
            category = tokens[1];
            ingredient = tokens[2];
            int threshold = Integer.parseInt(tokens[3]);
            ingredientsUI.setMinThreshold(category, ingredient, threshold, true);
            break;
            
        case UPDATE_PRICE:
            category = tokens[1];
            ingredient = tokens[2];
            price = Double.parseDouble(tokens[3]);
            ingredientsUI.updatePrice(category, ingredient, price, true);
            break;
            
        case ADD_INGREDIENT:
            addIngredient(tokens);
            break;
            
        case REMOVE_INGREDIENT:
            category = tokens[1];
            ingredient = tokens[2];
            ingredientsUI.removeIngredient(category, ingredient, true);
            break;
            
        case ADD_CATEGORY:
            addCategory(tokens);
            break;
        
        case REMOVE_CATEGORY:
            category = tokens[1];
            ingredientsUI.removeCategory(category, true);
            break;
            
        case SENDING_INGREDIENTS:
            String[] newTokens = String.join(DELIM,Arrays.copyOfRange(tokens, 1, tokens.length)).split(";");
            for (String token : newTokens) addIngredient(token.split(DELIM));
            break;
            
        case SENDING_CATEGORIES:
            newTokens = String.join(DELIM,Arrays.copyOfRange(tokens, 1, tokens.length)).split(";");
            for (String token : newTokens) addCategory(token.split(DELIM));
            break;
            
        default:
            System.err.println("Unrecognised or unsupported protocol from server");
        }
    }
    
    /**
     * Helper method to add a single ingredient to the system
     * @param tokens String[]: individual tokens from the server message
     * @throws NumberFormatException if unable to parse token to int or double
     * @throws IndexOutOfBoundsException if not enough tokens in server message
     */
    private void addIngredient(String[] tokens) throws NumberFormatException, IndexOutOfBoundsException {
        String name = tokens[1];
        int quantity = Integer.parseInt(tokens[2]);
        int threshold = Integer.parseInt(tokens[3]);
        double price = Double.parseDouble(tokens[4]);
        String category = tokens[5];
        Ingredient ingredient = new Ingredient(category, name, quantity, threshold, price);
        ingredientsUI.addIngredient(ingredient, true);
    }
    
    /**
     * Helper method to add a single category to the system
     * @param tokens String[]: individual tokens from the server message
     * @throws NumberFormatException if unable to parse token to int
     * @throws IndexOutOfBoundsException if not enough tokens in server message
     */
    private void addCategory(String[] tokens) throws NumberFormatException, IndexOutOfBoundsException {
        String name = tokens[1];
        int orderNum = Integer.parseInt(tokens[2]);
        Category category = new Category(name, orderNum);
        ingredientsUI.addCategory(category, true);
    }
    
    /**
     * Send a protocol conforming string to the server
     * @param output String: protocol conforming output
     */
    private void send(String output) {
        serverOut.println(output);
        serverOut.flush();
    }
    
    
    /**
     * Undo an action the server said has failed
     * @param action String: raw protocol conforming string representation of the action
     */
    public void undoAction(String action) {
        //TODO
    }
    
    /**
     * Sends a update of order status for other store client displays.
     * @param orderID String: identifier of the order
     * @param status String: new status
     */
    public void updateStatus(String orderID, String status) {
        String message = UPDATE_STATUS+DELIM+orderID+DELIM+status;
        send(message);
    }
    
    /**
     * Sends an updated price for a specific ingredient.
     * @param category String: ingredient category (e.g. salad)
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     * @param price double: cost to customer per unit
     */
    public void updatePrice(String category, String ingredient, double price) {
        String message = UPDATE_PRICE+DELIM+category+DELIM+ingredient+DELIM+price;
        send(message);
    }
    
    /**
     * Sends a request to increase the number of a specific ingredient held in stock
     * @param category String: ingredient category (e.g. salad)
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     * @param byAmount int: number by which to increase the quantity
     */
    public void increaseQty(String category, String ingredient, int byAmount) {
        String message = INCREASE_QUANTITY+DELIM+category+DELIM+ingredient+DELIM+byAmount;
        send(message);
    }
    
    /**
     * Sends a request to increase the number of a specific ingredient held in stock
     * @param category String: ingredient category (e.g. salad)
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     * @param byAmount int: number by which to decrease the quantity
     */
    public void decreaseQty(String category, String ingredient, int byAmount) {
        String message = DECREASE_QUANTITY+DELIM+category+DELIM+ingredient+DELIM+byAmount;
        send(message);
    }
    
    /**
     * Sends an update of the minimum acceptable quantity for a specific ingredient
     * @param category String: ingredient category (e.g. salad)
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     * @param threshold int: minimum number in stock before shop is notified to restock
     */
    public void setMinThreshold(String category, String ingredient, int threshold) {
        String message = SET_THRESHOLD+DELIM+category+DELIM+ingredient+DELIM+threshold;
        send(message);
    }
    
    /**
     * Adds a new ingredient to the system
     * @param ingredient Ingredient: the ingredient object to be added
     */
    public void addIngredient(Ingredient ingredient) {
        //ingredientName,number,minThreshold,price,category
        String message = ADD_INGREDIENT+DELIM+ingredient.getName()+DELIM+ingredient.getQuantity()+DELIM+
                         ingredient.getMinThreshold()+DELIM+ingredient.getPrice()+DELIM+ingredient.getCategory();
        send(message);
    }
    
    /**
     * Removes an ingredient from the system
     * @param category String: ingredient category (e.g. salad)
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     */
    public void removeIngredient(String category, String ingredient) {
        String message = REMOVE_INGREDIENT+DELIM+category+DELIM+ingredient;
        send(message);
    }
    
    /**
     * Sends an update of the order for all categories
     * @param category String: ingredient category (e.g. salad)
     * @param newOrder int: number representing the order for this category (e.g 1 is the first category to be displayed)
     */
    public void reorderCategories(String category, int newOrder) {
        String message = UPDATE_ORDER+DELIM+category+DELIM+newOrder;
        send(message);
    }
    
    /**
     * Add a new category to the system
     * @param category String: ingredient category (e.g. salad)
     */
    public void addCategory(Category category) {
        String message = ADD_CATEGORY+DELIM+category.getName()+DELIM+category.getOrder();
        send(message);
    }
    
    /**
     * Remove a category from the system
     * @param category String: ingredient category (e.g. salad)
     */
    public void removeCategory(String category) {
        String message = REMOVE_CATEGORY+DELIM+category;
        send(message);
    }

}
