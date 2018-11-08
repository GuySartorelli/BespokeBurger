package client;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import static protocol.Protocol.*;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

/**
 * Main entry point and application window for the client-side
 * @author Bespoke Burgers
 *
 */
public class ClientUI extends Application {

	//Attributes
	private ClientConnection client;
	private OrdersUI ordersUI;
	private IngredientsUI ingredientsUI;

	private TabPane root;
	private OrdersUI ordersTab;
	private IngredientsUI ingredientsTab;


	@Override
	public void start(Stage stage) throws Exception {

//	    this.client = new ClientConnection();
		this.root = new TabPane();
		setupTabPane();
//		client.requestCategories();
//		client.requestIngredients();


		//Creates the scene with the root group. Sets the style sheet to use.
		final Scene scene = new Scene(root, 0, 0);
		scene.getStylesheets().add("/styleIngredients.css");
		

		//Display the window.
		stage.setTitle("BespokeBurgers - Operator UI");
		stage.setScene(scene);
		stage.show();

		//Fit scene to width and height of the screen (from http://www.java2s.com/Code/Java/JavaFX/Setstagexandyaccordingtoscreensize.htm)
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		stage.setWidth(primScreenBounds.getWidth());
		stage.setHeight(primScreenBounds.getHeight());
		stage.setX(0);
		stage.setY(0);

	}
	
	public void setupTabPane() {
		
		//Creates the 2 tabs.
		ordersTab = new OrdersUI(client);
		ingredientsTab = new IngredientsUI(client);
//		client.setUIs(ingredientsUI, ordersUI);
		root.getTabs().addAll(ordersTab,ingredientsTab);
		
		//Set widths and heights of the tabs.
		int tabWidth = 160;
		int tabHeight = 100;
		
		root.setTabMinHeight(tabHeight);
		root.setTabMaxHeight(tabHeight);
		
		root.setTabMinWidth(tabWidth);
		root.setTabMaxWidth(tabWidth);
		
		//Remove ability to close tabs.
		root.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		
		//Get the width that the tabs occupy in order to set padding.
		//Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		//double width = primScreenBounds.getWidth() - (root.getTabs().size() * tabWidth) - 28;
		double width = 0; //padding also adjusts the main pane area which affects the order panes, so can't use this.
		
		root.setStyle("-fx-padding: -6 " + width + " -1 -6");
		
	}
	
    public static void main(String[] args) throws IOException {
        launch();
    }

}
