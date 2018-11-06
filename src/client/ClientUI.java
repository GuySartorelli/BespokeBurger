package client;

import javafx.application.Application;
import javafx.stage.Stage;

import static protocol.Protocol.*;

/**
 * Main entry point for the client-side.
 * @author Guy Sartorelli
 *
 */
public class ClientUI extends Application {
    private ClientConnection client;
    private OrdersUI ordersUI;
    private IngredientsUI ingredientsUI;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
    }

}
