package client;

import java.util.Map;
import java.util.TreeMap;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * GUI layout for ingredients tab.<br>
 * Sends information about changes regarding categories and ingredients to ClientConnection object.<br>
 * Receives instructions from server via ClientConnection object and forwards them to the appropriate Category and Ingredient objects.
 * @author Bespoke Burgers
 *
 */
public class IngredientsUI extends Tab {
    private final int HEADER_ROW = 0;
    private final int FIRST_INGREDIENT_ROW = 1;
    private final int INGREDIENT_COL = 0;
    private final int CURRENT_STOCK_COL = 1;
    private final int UPDATE_STOCK_COL = 2;
    
    private ClientConnection client;
    private volatile Map<String, Category> categories;
    private GridPane gridLayout;

    /**
     * Constructor
     * @param client ClientConnection: connection through which information can be sent to other clients
     */
    public IngredientsUI(ClientConnection client) {
        this.client = client;
        this.categories = new TreeMap<String, Category>();
        
        setupIngredientsTab();
    }
    
   
    /**
     * Sets up the format of the ingredients tab.
     */
	public void setupIngredientsTab() {
		this.setText("Ingredients");
		gridLayout = new GridPane();
		gridLayout.getStyleClass().add("ingredientsGrid");
		VBox mainLayout = new VBox(gridLayout);
		this.setContent(mainLayout);
		
		refreshIngredients();
	}
	
	/**
	 * Refreshes the grid of ingredients
	 */
	private void refreshIngredients() {
//	    createDebugIngredients();
	    gridLayout.getChildren().clear();
	    
	    //header row
	    Pane ingredientHeader = new Pane(new Text("Ingredient"));
        ingredientHeader.getStyleClass().add("headerPane");
        Pane currentStockHeader = new Pane(new Text("Current Stock"));
        currentStockHeader.getStyleClass().add("headerPane");
        Pane updateStockHeader = new Pane(new Text("Update Stock"));
        updateStockHeader.getStyleClass().add("headerPane");
        gridLayout.addRow(HEADER_ROW, ingredientHeader, currentStockHeader, updateStockHeader);
        
        //ingredients rows
        int row = FIRST_INGREDIENT_ROW;
	    for (Category category : categories.values()) {
	        for (Ingredient ingredient : category.getIngredients()) {
	            Pane ingredientCell = new Pane(new Text(ingredient.getName()));
	            ingredientCell.setOnMouseClicked(this::handleMouseEvent);
	            Pane currentStockCell = new Pane(new Text(String.valueOf(ingredient.getQuantity())));
	            currentStockCell.setOnMouseClicked(this::handleMouseEvent);
	            Pane UpdateStock = new Pane(new Text("Buttons and shit here"));
	            UpdateStock.setOnMouseClicked(this::handleMouseEvent);
	            gridLayout.addRow(row, ingredientCell, currentStockCell, UpdateStock);
	            row++;
	        }
	    }
	}
	
	/**
	 * Handles mouse events on ingredient rows
	 * @param event MouseEvent: the event to be handled
	 */
	private void handleMouseEvent(MouseEvent event) {
	    Pane source = (Pane)event.getSource();
        int rowNum = GridPane.getRowIndex(source);
        int colNum = GridPane.getColumnIndex(source);
        System.out.printf("Mouse entered cell [%d, %d]%n", colNum, rowNum);
        System.out.println(((Text)source.getChildren().get(0)).getText());
	}
	
	private void createDebugIngredients() {
	    //Test categories
        Category salad = new Category("Salad", 0);
        Category patty = new Category("Patty", 1);
        addCategory(salad, true);
        addCategory(patty, true);
        
        //Test ingredients.
        Ingredient lettuce = new Ingredient(salad, "Lettuce",300,10,1.00);
        Ingredient tomato = new Ingredient(salad, "Tomato",300,10,1.00);
        Ingredient beef = new Ingredient(patty, "Beef",300,10,1.00);
        addIngredient(lettuce, true);
        addIngredient(tomato, true);
        addIngredient(beef, true);
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
            //TODO add "are you sure?" if category has ingredients
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
    public void addIngredient(Ingredient ingredient, boolean fromServer) {
        ingredient.getCategory().addIngredient(ingredient);
        if (!fromServer) client.addIngredient(ingredient);
    }
    
    /**
     * Removes an ingredient from the appropriate category and sends that instruction through the client connection (unless the instruction originated from the server)
     * @param category String: ingredient category (e.g. salad)
     * @param ingredient String: name of the ingredient to be removed (e.g. lettuce)
     * @param fromServer boolean: true if this method is being called from the ClientConnection object
     */
    public synchronized void removeIngredient(String category, String ingredient, boolean fromServer) {
        categories.get(category).removeIngredient(ingredient);
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
    
}
