package client;

import javafx.application.Application;
<<<<<<< HEAD

=======
>>>>>>> branch 'OrdersUI' of https://gitlab.ecs.vuw.ac.nz/sartorguy/bespokeburgers.git
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import static protocol.Protocol.*;

import com.sun.javafx.css.StyleManager;

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

	private TabPane tabPane;
	private OrdersUI ordersTab;
	private IngredientsUI ingredientsTab;
    private ComboBox<String> orderFilter;
    
    private AnchorPane root;


	@Override
	public void start(Stage stage) throws Exception {

        this.root = new AnchorPane();
		this.tabPane = new TabPane();

		setupTabPane();


		//Creates the scene with the root group. Sets the style sheet to use.
		//final Scene scene = new Scene(root, 0, 0);
		final Scene scene = new Scene(root, 0, 0);
		
		//scene.getStylesheets().add("/styleIngredients.css");
		//scene.getStylesheets().add("/client/styleIngredients.css");///////
		//scene.getStylesheets().clear();
	
		//com.sun.javafx.css.StyleManager.getInstance().reloadStylesheets(scene);
		
		com.sun.javafx.css.StyleManager.getInstance().removeUserAgentStylesheet(ClientUI.class.getResource("styleIngredients.css")
			    .toExternalForm());
		
		scene.getStylesheets().add(ClientUI.class.getResource("styleIngredients.css")
			    .toExternalForm()); // if some_style.css is next to YourClass.java
		

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
		
		//Creates the 2 tabs and adds them to the TabPane.
		ordersTab = new OrdersUI(client);
		ingredientsTab = new IngredientsUI(client);
		tabPane.getTabs().addAll(ordersTab,ingredientsTab);
		
		//Set widths and heights of the tabs.
		int tabWidth = 183;
		int tabHeight = 100;
		
		tabPane.setTabMinHeight(tabHeight);
		tabPane.setTabMaxHeight(tabHeight);
		
		tabPane.setTabMinWidth(tabWidth);
		tabPane.setTabMaxWidth(tabWidth);
		
<<<<<<< HEAD
		        //Setting up the filterOrders ComboBox
=======
        //Setting up the filterOrders ComboBox
>>>>>>> branch 'OrdersUI' of https://gitlab.ecs.vuw.ac.nz/sartorguy/bespokeburgers.git
        ObservableList<String> values = FXCollections.observableArrayList("Cook","Cashier","Manager");
        orderFilter = new ComboBox<>(values);
        orderFilter.setMinWidth(tabWidth*2.2);
        orderFilter.setMinHeight(tabHeight/2);
        orderFilter.setValue("Cook");
        orderFilter.getStyleClass().add("orderFilter");
                
        //Adds the TabPane and the orderFilter ComboBox to the AnchorPane (root).
        //Adapted from: https://stackoverflow.com/questions/37721760/add-buttons-to-tabs-and-tab-area-javafx
        root.getChildren().addAll(tabPane, orderFilter);
        AnchorPane.setTopAnchor(orderFilter, 3.0);
        AnchorPane.setRightAnchor(orderFilter, 5.0);
        AnchorPane.setTopAnchor(tabPane, 1.0);
        AnchorPane.setRightAnchor(tabPane, 1.0);
        AnchorPane.setLeftAnchor(tabPane, 1.0);
        AnchorPane.setBottomAnchor(tabPane, 1.0);
		
		//Remove ability to close tabs.
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		
		tabPane.setStyle("-fx-padding: -6 0 -1 -6");

		
	}
	
    public static void main(String[] args) throws IOException {
        launch();
    }

}
