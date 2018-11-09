package client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Tab;
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
    
    private ClientConnection client;
    private volatile Map<String, Category> categories;
    private Map<String, IngredientRow> rows;
    private IngredientRow selected;
    private GridPane gridLayout;
    
    /**
     * FOR DEBUGGING ONLY! DELETE BEFORE RELEASE
     */
    private void createDebugIngredients() {
        //Test categories
        Category salad = new Category("Salad", 1);
        Category patty = new Category("Patty", 0);
        addCategory(salad, true);
        addCategory(patty, true);
        
        //Test ingredients.
        Ingredient lettuce = new Ingredient(salad, "Lettuce",300,10,1.00);
        Ingredient tomato = new Ingredient(salad, "Tomato",400,10,1.00);
        Ingredient beef = new Ingredient(patty, "Beef",500,10,1.00);
        addIngredient(lettuce, true);
        addIngredient(tomato, true);
        addIngredient(beef, true);
    }

    /**
     * Constructor
     * @param client ClientConnection: connection through which information can be sent to other clients
     */
    public IngredientsUI(ClientConnection client) {
        this.client = client;
        this.categories = new HashMap<String, Category>();
        this.rows = new HashMap<String, IngredientRow>();
        
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
		VBox.setMargin(gridLayout, new Insets(5,0,5,0));
		mainLayout.setPadding(new Insets(10));
		this.setContent(mainLayout);
		
        createDebugIngredients();
		refreshIngredients();
	}
	
	/**
	 * Refreshes the grid of ingredients
	 */
	private void refreshIngredients() {
	    gridLayout.getChildren().clear();
	    
        gridLayout.addRow(HEADER_ROW, createHeaderPane("Ingredient"), createHeaderPane("Current Stock"), createHeaderPane("Update Stock"));
        
        int rowIndex = FIRST_INGREDIENT_ROW;
        ArrayList<Category> categoryValues = new ArrayList<Category>(categories.values());
        Collections.sort(categoryValues);
	    for (Category category : categoryValues) {
	        for (Ingredient ingredient : category.getIngredients()) {
	            IngredientRow row = new IngredientRow(this, ingredient, rowIndex);
	            rows.put(ingredient.getName(), row);
	            gridLayout.addRow(rowIndex, row.getIngredientCell(), row.getCurrentStockCell(), row.getUpdateStockCell());
	            rowIndex++;
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
        refreshIngredients();
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
    
}
