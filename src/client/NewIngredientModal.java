package client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Used as a modal display for adding a new ingredient to the database.
 * @author Bespoke Burgers
 *
 */
public class NewIngredientModal extends Stage {
	
	//Attributes
	VBox layout;
		
	/**
	 * Constructor
	 * @param parentStage Stage: the Stage object that the modal was called from.
	 */
	public NewIngredientModal(Stage parentStage) {
		
		this.setTitle("New Ingredient");
		
		layout = new VBox();
		layout.setSpacing(10);

        Scene orderModalScene = new Scene(layout, 400, 400);
        orderModalScene.getStylesheets().add("/styleModal.css");
        this.setScene(orderModalScene);

        // Specifies the modality for new window.
        this.initModality(Modality.WINDOW_MODAL);

        // Specifies the owner Window (parent) for new window
        this.initOwner(parentStage);
        
        //Centers the modal
    	this.centerOnScreen();
    	
    	setupModal();

	}
	
	public void setupModal() {
		
		//Header and ingredient name.
		Label header = new Label("New Ingredient");
		header.getStyleClass().add("headerLabel");
		
		//Title labels
		VBox titlesVBox = new VBox();
		Label ingredientTitle = new Label("Ingredient:");
		Label classTitle = new Label("Category:");
		Label thresholdTitle = new Label("Threshold:");
		Label price = new Label("Price/unit:");
		
		titlesVBox.getChildren().addAll(ingredientTitle,classTitle,thresholdTitle,price);
		
		//Set height and style of the labels.
		for (Node n : titlesVBox.getChildren()) {
			Label label = (Label) n;
			label.setMinHeight(40);
			label.getStyleClass().add("normalLabel");
		}
		
		//Setting textfields.
		VBox textFieldsVBox = new VBox();
		TextField ingredientTF = new TextField();
		TextField categoryTF = new TextField(); 
		TextField thresholdTF = new TextField();
		TextField priceTF = new TextField();
		textFieldsVBox.getChildren().addAll(ingredientTF,categoryTF,thresholdTF,priceTF);
		
		//Set height of the textfields.
		for (Node n : textFieldsVBox.getChildren()) {
			TextField textField = (TextField) n;
			textField.setMinHeight(40);
		}
		
		//Add title label and textfield VBoxes to an HBox.
		HBox settingsHBox = new HBox();
		settingsHBox.setSpacing(20);
		settingsHBox.getChildren().addAll(titlesVBox,textFieldsVBox);

		
		//Place save button.
		int buttonHeight = 40;
		int buttonWidth = 200;
		Button saveButton = new Button("Save");
		saveButton.setMinHeight(buttonHeight);
		saveButton.setMinWidth(buttonWidth);
		saveButton.getStyleClass().add("orderButton");
		
		//Save button action event.
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
   		 
            @Override
            public void handle(ActionEvent event) {
            	
                String newName = ingredientTF.getText();
                String newClass = categoryTF.getText();
                String newThreshold = thresholdTF.getText();
                String newPrice = priceTF.getText();

                
            	
            	//TO DO: Do the thing.
            
            }
         });
		
		
		
		//Put the button into an HBox.
		HBox buttonHBox = new HBox();
		buttonHBox.setSpacing(50);
		buttonHBox.setAlignment(Pos.CENTER);
		buttonHBox.getChildren().addAll(saveButton);

		
		//Add children to the main layout VBox.
		layout.getChildren().addAll(header,settingsHBox,buttonHBox);
		
		
	}

}
