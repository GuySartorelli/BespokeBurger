package client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

<<<<<<< HEAD
=======
import javax.swing.ImageIcon;

import client.CurrencyTextField.CurrencySymbol;
import javafx.collections.FXCollections;
>>>>>>> branch 'ClientUI' of https://gitlab.ecs.vuw.ac.nz/sartorguy/bespokeburgers.git
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
<<<<<<< HEAD
=======
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Separator;
>>>>>>> branch 'ClientUI' of https://gitlab.ecs.vuw.ac.nz/sartorguy/bespokeburgers.git
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * GUI layout for ingredients tab.<br>
 * Sends information about changes regarding categories and ingredients to ClientConnection object.<br>
 * Receives instructions from server via ClientConnection object and forwards them to the appropriate Category and Ingredient objects.
 * @author Bespoke Burgers
 *
 */
public class IngredientsUI extends Tab {
    private int nextRow = 0;
    
    private ClientConnection client;
    private volatile Map<String, Category> categories;
    private Map<String, IngredientRow> rows;
    private IngredientRow selected;
    private ScrollPane scrollPane;
    private GridPane gridLayout;
    
    private Stage parentStage;
    
    public void createDebugModals() {

    	Category salad = new Category("Salad", 1);
    	Ingredient lettuce = new Ingredient(salad, "Lettuce",300,10,1.00);

    	//Test button to show modal/s.
    	Button orderButton = new Button("order modal test");
    	gridLayout.getChildren().add(orderButton);
    	OrderModal orderModal = new OrderModal(parentStage,lettuce);

    	orderButton.setOnAction(new EventHandler<ActionEvent>() {

    		@Override
    		public void handle(ActionEvent event) {

    			orderModal.show();
    		}
    	});

    	Button settingsButton = new Button("settings modal test");
    	gridLayout.getChildren().add(settingsButton);
    	SettingsModal settingsModal = new SettingsModal(parentStage,lettuce);
    	settingsButton.setOnAction(new EventHandler<ActionEvent>() {

    		@Override
    		public void handle(ActionEvent event) {

    			settingsModal.show();
    		}
    	});
    	
    	Button newIngredientButton = new Button("new ingredient modal test");
    	gridLayout.getChildren().add(newIngredientButton);
    	NewIngredientModal newIngredientModal = new NewIngredientModal(parentStage);

    	newIngredientButton.setOnAction(new EventHandler<ActionEvent>() {

    		@Override
    		public void handle(ActionEvent event) {

    			newIngredientModal.show();
    		}
    	});
    	
    	Button editCategoriesButton = new Button("edit categories modal test");
    	gridLayout.getChildren().add(editCategoriesButton);
    	Category patty = new Category("Patty",1);
    	Category bun = new Category("Bun",2);
    	Category salad2 = new Category("Salad",3);
    	Category sauce = new Category("Sauce",4);

    	List<Category> categories = Arrays.asList(patty,bun,salad2,sauce);
    	EditCategoriesModal editCategoriesModal = new EditCategoriesModal(parentStage,categories);

    	editCategoriesButton.setOnAction(new EventHandler<ActionEvent>() {

    		@Override
    		public void handle(ActionEvent event) {

    			editCategoriesModal.show();
    		}
    	});

    }
    
    public void createAddingAndEditingMenu() {
		
		ComboBox<Object> cb = new ComboBox<>();
		cb.setItems(FXCollections.observableArrayList(
		    "New Ingredient",new Separator(), "Edit Category" )
		);
		
		gridLayout.add(cb, 0, 5);
		cb.setValue("New Ingredient and Edit Category");
//		cb.setStyle("-fx-background-image:url('/baseline-add_box-black-18/2x/baseline_add_box_black_18dp.png')");
		
		
    	NewIngredientModal newIngredientModal = new NewIngredientModal(parentStage);
    	
    	Category patty = new Category("Patty",1);
    	Category bun = new Category("Bun",2);
    	Category salad2 = new Category("Salad",3);
    	Category sauce = new Category("Sauce",4);
    	
    	List<Category> categories = Arrays.asList(patty,bun,salad2,sauce);
    	EditCategoriesModal editCategoriesModal = new EditCategoriesModal(parentStage,categories);
     	
    	cb.getSelectionModel().selectedIndexProperty().addListener((ov,oldv,newv)->{
    		   		
    		if (cb.getSelectionModel().getSelectedIndex() == 0) newIngredientModal.show();
    		if (cb.getSelectionModel().getSelectedIndex() == 2) editCategoriesModal.show();
			
		});
   	
    }

    /**
     * Constructor
     * @param client ClientConnection: connection through which information can be sent to other clients
     * @param parentStage Stage: the stage that this tab's tabPane is being displayed on.
     */
    public IngredientsUI(ClientConnection client, Stage parentStage) {
        this.client = client;
        this.categories = new HashMap<String, Category>();
        this.rows = new HashMap<String, IngredientRow>();
        this.parentStage = parentStage;
        
        
        setupIngredientsTab();
    }
    
   
    /**
     * Sets up the format of the ingredients tab.
     */
	public void setupIngredientsTab() {
		
		this.setText("Ingredients");
		scrollPane = new ScrollPane();
		gridLayout = new GridPane();
		gridLayout.getStyleClass().add("ingredientsGrid");
		scrollPane.setContent(gridLayout);
		VBox mainLayout = new VBox(scrollPane);
		VBox.setMargin(scrollPane, new Insets(5,0,5,0));
		mainLayout.setPadding(new Insets(10));
		this.setContent(mainLayout);
		
		refreshIngredients();
//		createDebugModals();
		createAddingAndEditingMenu();
	}
	
	/**
	 * Refreshes the grid of ingredients
	 */
	private void refreshIngredients() {
	    gridLayout.getChildren().clear();
        nextRow = 0;
	    
        gridLayout.addRow(nextRow, createHeaderPane("Ingredient"), createHeaderPane("Current Stock"), createHeaderPane("Update Stock"));
        nextRow++;
        
        ArrayList<Category> categoryValues = new ArrayList<Category>(categories.values());
        Collections.sort(categoryValues);
	    for (Category category : categoryValues) {
	        for (Ingredient ingredient : category.getIngredients()) {
	            addIngredientRow(ingredient);
	        }
	    }
	}
	
	/**
	 * Creates a pane for ingredient table header cells
	 * @param contents String: The text for the header cell
	 * @return Pane: a pane which represents the cell
	 */
	private Pane createHeaderPane(String contents) {
        VBox pane = new VBox(new Text(contents));
        pane.setPadding(new Insets(5));
        pane.setAlignment(Pos.CENTER_LEFT);
        pane.getStyleClass().add("normalBorder");
        pane.getStyleClass().add("headerPane");
        return pane;
	}
	
	/**
	 * Marks a row as selected
	 * @param row String: name of the ingredient row to be selected
	 */
	public void select(String row) {
	    if (selected != null) selected.deselect();
	    selected = rows.get(row);
	    if (selected != null) selected.select();
	}
	
	
	//NON-LAYOUT-SPECIFIC METHODS\\ 
    
    /**
     * Adds a new category to the categories map and UI and sends that instruction through the client connection (unless the instruction originated from the server)
     * @param category Category: the category to be added
     * @param fromServer boolean: true if this method is being called from the ClientConnection object
     */
    public void addCategory(Category category, boolean fromServer) {
        if (categories.containsKey(category.getName())){
            categories.get(category.getName()).setOrder(category.getOrder());
        } else {
            categories.put(category.getName(), category);
        }
        if (!fromServer) client.addCategory(category);
    }
    
    /**
     * Removes a category from the categories map and sends that instruction through the client connection (unless the instruction originated from the server)
     * @param category String: ingredient category to be removed (e.g. salad)
     * @param fromServer boolean: true if this method is being called from the ClientConnection object
     */
    public synchronized void removeCategory(String category, boolean fromServer) {
        if (!categories.get(category).isEmpty()){
            //TODO add "Cannot remove category that contains ingredients" if category has ingredients
            return;
        }
        categories.remove(category);
        if (!fromServer) client.removeCategory(category);
    }
    
    /**
     * Updates the price of an ingredient and sends that instruction through the client connection (unless the instruction originated from the server)
     * @param category String: ingredient category (e.g. salad)
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     * @param price double: cost to customer per unit
     * @param fromServer boolean: true if this method is being called from the ClientConnection object
     */
    public void updatePrice(String category, String ingredient, double price, boolean fromServer) {
        categories.get(category).getIngredient(ingredient).setPrice(price);
        if (!fromServer) client.updatePrice(category, ingredient, price);
    }
    
    /**
     * Increases the quantity of an ingredient and sends that instruction through the client connection (unless the instruction originated from the server)
     * @param category String: ingredient category (e.g. salad)
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     * @param byAmount int: number by which to increase the quantity
     * @param fromServer boolean: true if this method is being called from the ClientConnection object
     */
    public synchronized void increaseQty(String category, String ingredient, int byAmount, boolean fromServer) {
        Ingredient ing = categories.get(category).getIngredient(ingredient);
        ing.setQuantity(ing.getQuantity() + byAmount);
        if (!fromServer) client.increaseQty(category, ingredient, byAmount);
    }
    
    /**
     * Decreases the quantity of an ingredient and sends that instruction through the client connection (unless the instruction originated from the server)
     * @param category String: ingredient category (e.g. salad)
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     * @param byAmount int: number by which to decrease the quantity
     * @param fromServer boolean: true if this method is being called from the ClientConnection object
     */
    public synchronized void decreaseQty(String category, String ingredient, int byAmount, boolean fromServer) {
        System.out.println("Decrease qty called. From server = " + fromServer);
        Ingredient ing = categories.get(category).getIngredient(ingredient);
        ing.setQuantity(ing.getQuantity() - byAmount);
        if (!fromServer) client.decreaseQty(category, ingredient, byAmount);
    }
    
    /**
     * Sets the minimum acceptable quantity for an ingredient and sends that instruction through the client connection (unless the instruction originated from the server)
     * @param category String: ingredient category (e.g. salad)
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     * @param threshold int: minimum number in stock before shop is notified to restock
     * @param fromServer boolean: true if this method is being called from the ClientConnection object
     */
    public synchronized void setMinThreshold(String category, String ingredient, int threshold, boolean fromServer) {
        categories.get(category).getIngredient(ingredient).setMinThreshold(threshold);
        if (!fromServer) client.setMinThreshold(category, ingredient, threshold);
    }
    
    /**
     * Adds a new ingredient to the appropriate category and UI and sends that instruction through the client connection (unless the instruction originated from the server)
     * @param ingredient Ingredient: the ingredient to be added
     * @param fromServer boolean: true if this method is being called from the ClientConnection object
     */
    public void addIngredient(Ingredient ingredient,String categoryName, boolean fromServer) {
    	Category category = categories.get(categoryName);
    	ingredient.setCategory(category);
    	category.addIngredient(ingredient);
    	addIngredientRow(ingredient);
    	
        if (!fromServer) client.addIngredient(ingredient);
    }
    
    private void addIngredientRow(Ingredient ingredient) {
        IngredientRow row = new IngredientRow(this, ingredient, nextRow);
        rows.put(ingredient.getName(), row);
        gridLayout.addRow(nextRow, row.getIngredientCell(), row.getCurrentStockCell(), row.getUpdateStockCell());
        nextRow++;
    }
    
    /**
     * Removes an ingredient from the appropriate category and sends that instruction through the client connection (unless the instruction originated from the server)
     * @param category String: ingredient category (e.g. salad)
     * @param ingredient String: name of the ingredient to be removed (e.g. lettuce)
     * @param fromServer boolean: true if this method is being called from the ClientConnection object
     */
    public synchronized void removeIngredient(String category, String ingredient, boolean fromServer) {
        categories.get(category).removeIngredient(ingredient);
        IngredientRow ingredientRow = rows.get(ingredient);
        int deleteRow = ingredientRow.getRowIndex();
        if (deleteRow == selected.getRowIndex()) selected = null;
        
        for (Node node : gridLayout.getChildren()) {
            int row = GridPane.getRowIndex(node);
            if (row > deleteRow)
                if (GridPane.getColumnIndex(node) == IngredientRow.INGREDIENT_COL) {
                    String moveIngredient = ((Text)((Pane)node).getChildren().get(0)).getText();
                    rows.get(moveIngredient).setRowIndex(row-1);
                }
        }
        gridLayout.getChildren().remove(ingredientRow.getIngredientCell());
        gridLayout.getChildren().remove(ingredientRow.getCurrentStockCell());
        gridLayout.getChildren().remove(ingredientRow.getUpdateStockCell());
        rows.remove(ingredient);
        
        if (!fromServer) client.removeIngredient(category, ingredient);
    }
    
    /**
     * Returns the specified category
     * @param category String: ingredient category (e.g. salad)
     * @return Category: the specified category
     */
    public Category getCategory(String category) {
        return categories.get(category);
    }
    
    /**
     * Returns the specified ingredient from the specified category
     * @param category String: ingredient category (e.g. salad)
     * @param ingredient String: name of the ingredient (e.g. lettuce)
     * @return Ingredient: the specified ingredient
     */
    public Ingredient getIngredient(String category, String ingredient) {
        return categories.get(category).getIngredient(ingredient);
    }
    
    public Stage getParentStage() {
    	return this.parentStage;
    }
    
}
