package server;

import java.io.PrintWriter;
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
    private final int PORT = 9090;
    private final short SHOP = 50;
    private final short WEB = 51;
    private Map<Integer, PrintWriter> webOut;
    private Map<Integer, PrintWriter> shopOut;
    
    public static void main(String[] args) {
        new Server();
    }
    
    /**
     * Default constructor which also spins up a new thread
     */
    public Server() {
        new Thread(this);
    }

    /**
     * Listens for incoming connections and instantiates ServerConnection objects for them
     */
    @Override
    public void run() {
        //TODO Add listener while loop here
    }

    /**
     * Processes incoming data from ServerConnection objects
     * @param id int: id corresponding to the connection
     * @param input String: raw data as-sent from the client
     */
    public void process(int id, String input) {
        //TODO process incoming data here
    }

    /**
     * Sends data out to the appropriate client
     * @param destination short: WEB or SHOP
     * @param input String: Protocol conforming data to be sent to the client
     * @return short corresponding to Protocol.ERROR/FAILURE/SUCCESS
     */
    public short sendTo(short destination, String input) {
        //TODO add output to appropriate writers here
    }

}
