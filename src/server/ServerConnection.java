package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Represents each connection from a client (whether from the web server or a store client).
 * Forwards all incoming data to the Server object to be processed and forwarded to appropriate clients
 * @author Bespoke Burgers
 *
 */
public class ServerConnection extends Thread {
    private Server server;
    private Socket socket;
    private int id;
    private BufferedReader in;

    /**
     * Constructor
     * @param socket Socket: The socket for this connection
     * @param server Server: The main server object
     */
    public ServerConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        this.id = socket.getPort();
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //TODO add appropriate try/catch
    }
    
    /**
     * This method runs on a separate thread to the rest of the server.
     * Incoming data will be caught here and forwarded to the main Server object for processing.
     */
    @Override
    public void run() {
        //TODO add listening while loop here
    }
    
    /**
     * Close this connection
     */
    public void close() {
        //TODO close the socket here
    }

}
