package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static protocol.Protocol.*;

/**
 * Main entry point for the Server-side.
 * Listens for incoming client connections and classifies them appropriately as a web connection or a store connection.
 * Processes all incoming data from ServerConnection objects and forwards appropriately to other clients and/or the Database.
 * @author Bespoke Burgers
 *
 */
public class Server implements Runnable {
    static final short NONE = 50;
    static final short SHOP = 51;
    static final short WEB = 52;
    private final short ALL = -53;
    private final int PORT = 9090;
    private volatile boolean isRunning;
    private ServerSocket listener;
    private Map<Integer, ServerConnection> unregistered;
    private Map<Integer, PrintWriter> webOut;
    private Map<Integer, PrintWriter> shopOut;
    
    public static void main(String[] args) throws IOException {
        try {
            new Server();
        } catch (IOException e) {
            System.err.println("Could not start server");
            throw(e);
        }
    }
    
    /**
     * Default constructor which also spins up a new thread
     * @throws IOException 
     */
    public Server() throws IOException {
        this.webOut = new HashMap<Integer, PrintWriter>();
        this.shopOut = new HashMap<Integer, PrintWriter>();
        this.unregistered = new HashMap<Integer, ServerConnection>();
        listener = new ServerSocket(PORT);
        this.isRunning = true;
        new Thread(this).start();
        System.out.println("Server listening on port " + PORT);
    }

    /**
     * Listens for incoming connections and instantiates ServerConnection objects for them
     */
    @Override
    public void run() {
        while (this.isRunning) {
        try {
            Socket socket = listener.accept();
            ServerConnection connection = new ServerConnection(socket, this);
            connection.start();
            unregistered.put(socket.getPort(), connection);
        } catch (IOException e) {
            close();
            e.printStackTrace();
        }
    }
    }

    /**
     * Processes incoming data from ServerConnection objects
     * @param id int: id corresponding to the connection
     * @param input String: raw data as-sent from the client
     */
    public void process(int id, short registeredTo, String input) {
        String[] tokens = input.split(DELIM);
        String protocol = tokens[0];
        
        if (registeredTo == NONE) {
            switch (protocol) {
            case REGISTER_AS:
                try {
                    String registerTo = tokens[1];
                    if (registerTo.equals(WEBSITE)) registerClient(id, WEB, webOut);
                    else if (registerTo.equals(STORE)) registerClient(id, SHOP, shopOut);
                    else System.err.printf("Client %d attempting to register as unrecognised type %s\n", id, registerTo);
                } catch (IndexOutOfBoundsException e) {System.err.println("Error registering client: insufficient tokens");}
                break;
            default:
                System.err.printf("Unregistered client %d attempting to use additional protocols\n", id);
                unregistered.get(id).getWriter().println(FAILURE+DELIM+input);
                unregistered.get(id).getWriter().flush();
            }
        } else if (registeredTo == WEB || registeredTo == SHOP) {
        
            switch (protocol) {
            case DEREGISTER:
                if (registeredTo == WEB) deregisterClient(id, webOut);
                else if (registeredTo == SHOP) deregisterClient(id, shopOut);
                break;
        
            case NEW_ORDER:
                sendTo(SHOP, ALL, input);
                break;
            
            case UPDATE_STATUS:
                sendTo(SHOP, id, input);
                break;
                
            case INCREASE_QUANTITY:
            case DECREASE_QUANTITY:
            case SET_THRESHOLD:
                short success = -1;
                try {
                    String ingredient = tokens[1];
                    int value = Integer.parseInt(tokens[2]);
                    if (protocol == INCREASE_QUANTITY) success = Database.increaseQty(ingredient, value);
                    else if (protocol == DECREASE_QUANTITY) success = Database.decreaseQty(ingredient, value);
                    else if (protocol == SET_THRESHOLD) success = Database.updateThreshold(ingredient, value);
                } catch (NumberFormatException e) {success = ERROR;}
                  catch (IndexOutOfBoundsException e) {success = ERROR;}
                    
                if (success == SUCCESS) sendTo(SHOP, id, input);
                else replyTo(registeredTo, id, success+DELIM+input);
                break;
                
            case UPDATE_ORDER:
                success = -1;
                try {
                    String category = tokens[1];
                    int newOrder = Integer.parseInt(tokens[2]);
                    success = Database.reorderCategory(category, newOrder);
                } catch (NumberFormatException e) {success = ERROR;}
                  catch (IndexOutOfBoundsException e) {success = ERROR;}
                
                if (success == SUCCESS) sendTo(SHOP, id, input);
                else replyTo(registeredTo, id, success+DELIM+input);
                break;                
            
            case ADD_INGREDIENT:
                try {
                    String ingredient = tokens[1];
                    int quantity = Integer.parseInt(tokens[2]);
                    int threshold = Integer.parseInt(tokens[3]);
                    double price = Double.parseDouble(tokens[4]);
                    String category = tokens[5];
                    success = Database.addIngredient(ingredient, category, quantity, threshold, price);
                } catch (NumberFormatException e) {success = ERROR;}
                  catch (IndexOutOfBoundsException e) {success = ERROR;}
                
                if (success == SUCCESS) sendTo(SHOP, id, input);
                else replyTo(registeredTo, id, success+DELIM+input);
                break;
                
            case ADD_CATEGORY:
                try {
                    String category = tokens[1];
                    int order = Integer.parseInt(tokens[2]);
                    success = Database.addCategory(category, order);
                } catch (NumberFormatException e) {success = ERROR;}
                  catch (IndexOutOfBoundsException e) {success = ERROR;}
                        
                if (success == SUCCESS) sendTo(SHOP, id, input);
                else replyTo(registeredTo, id, success+DELIM+input);
                break;
                
            case REMOVE_INGREDIENT:
            case REMOVE_CATEGORY:
                success = -1;
                try {
                    String item = tokens[1];
                    if (protocol == REMOVE_INGREDIENT) success = Database.removeIngredient(item);
                    else if (protocol == REMOVE_CATEGORY) success = Database.removeCategory(item);
                } catch (IndexOutOfBoundsException e) {success = ERROR;}
                
                if (success == SUCCESS) sendTo(SHOP, id, input);
                else replyTo(registeredTo, id, success+DELIM+input);
                break;
                
            case REQUEST_CATEGORIES:
                String categories = Database.getCategories();
                replyTo(registeredTo, id, SENDING_CATEGORIES+DELIM+categories);
                break;
            
            case REQUEST_INGREDIENTS:
                String ingredients = Database.getIngredients();
                replyTo(registeredTo, id, SENDING_INGREDIENTS+DELIM+ingredients);
                break;
                
            default:
                System.err.printf("Unrecognised or unsupported protocol %s from client %d\n", protocol, id);
    
            }
        } else {
            
        }
    }
    
    /**
     * Registers a client connection as coming from the web server or an in-store client
     * @param id int: the id for this client
     * @param registerTo short: corresponds to Server.WEB or Server.SHOP
     * @param outMap {@literal Map<Integer, PrintWriter>}: the map of outputs for this register type
     */
    private void registerClient(int id, short registerTo, Map<Integer, PrintWriter> outMap) {
        ServerConnection connection = unregistered.get(id);
        connection.register(registerTo);
        outMap.put(id, connection.getWriter());
        unregistered.remove(id);
    }
    

    /**
     * Deregisters a client connection because that client has closed its connection
     * @param id int: the id for this client
     * @param outMap {@literal Map<Integer, PrintWriter>}: the map of outputs for this register type
     */
    private void deregisterClient(int id, Map<Integer, PrintWriter> outMap) {
        outMap.remove(id);
    }
    
    /**
     * Sends data in response to a specific client
     * @param destinationType short: WEB or SHOP
     * @param writer int: id for the writer to respond to
     * @param response String: Protocol conforming data to be sent to the client
     * @return
     */
    public short replyTo(short destinationType, int writer, String response) {
        Map<Integer, PrintWriter> destinationMap = null;
        if (destinationType == WEB) destinationMap = webOut;
        else if (destinationType == SHOP) destinationMap = shopOut;
        else return ERROR;
        
        PrintWriter out = destinationMap.get(writer);
        if (out == null) return ERROR;
        
        out.println(response);
        out.flush();
        return SUCCESS;
    }

    /**
     * Sends data out to the appropriate client
     * @param destinationType short: WEB or SHOP
     * @param input String: Protocol conforming data to be sent to the client
     * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
     */
    public short sendTo(short destinationType, int except, String input) {
        Map<Integer, PrintWriter> destinationMap = null;
        if (destinationType == WEB) destinationMap = webOut;
        else if (destinationType == SHOP) destinationMap = shopOut;
        else return ERROR;
        
        for (Map.Entry<Integer, PrintWriter> entry : destinationMap.entrySet()) {
            if (except != ALL && entry.getKey() == except) continue;
            PrintWriter out = entry.getValue(); 
            out.println(input);
            out.flush();
        }
        return SUCCESS;
    }
    
    /**
     * Closes the server's listener and sets isRunning to false
     */
    private void close() {
        this.isRunning = false;
        try {
            listener.close();
            System.out.println("Server closed");
        } catch (IOException e) {
            System.err.println("Error closing server listener");
            e.printStackTrace();
        }
    }
    
    /**
     * Returns the current status of this server
     * @return boolean: true if server is running
     */
    boolean isRunning() {
        return this.isRunning;
    }

}
