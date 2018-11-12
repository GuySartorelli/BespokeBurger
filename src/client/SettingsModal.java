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
 * Used as a modal display to edit the settings and/or remove the selected ingredient.
 * @author Bespoke Burgers
 *
 */
public class SettingsModal extends Stage {
	
	//Attributes
	VBox layout;
	Ingredient ingredient;
	
	
	/**
	 * Constructor
	 * @param parentStage Stage: the Stage object that the modal was called from.
	 * @param ingredient Ingredient: the Ingredient object that was selected.
	 */
	public SettingsModal(Stage parentStage, Ingredient ingredient) {
		
		this.setTitle("Settings");
		
		layout = new VBox();
		layout.setSpacing(10);
		this.ingredient = ingredient;

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
		Label header = new Label("Settings");
		header.getStyleClass().add("headerLabel");
		
		//Title labels
		VBox titlesVBox = new VBox();
		Label ingredientTitle = new Label("Ingredient:");
		Label classTitle = new Label("Category:");
		Label thresholdTitle = new Label("Threshold:");
		
		titlesVBox.getChildren().addAll(ingredientTitle,classTitle,thresholdTitle);
		
		//Set height and style of the labels.
		for (Node n : titlesVBox.getChildren()) {
			Label label = (Label) n;
			label.setMinHeight(40);
			label.getStyleClass().add("normalLabel");
		}
		
		//Setting textfields.
		VBox textFieldsVBox = new VBox();
		TextField ingredientTF = new TextField(); ingredientTF.setText(ingredient.getName());
		TextField categoryTF = new TextField(); categoryTF.setText(ingredient.getCategory().getName());
		TextField thresholdTF = new TextField(); thresholdTF.setText(Integer.toString(ingredient.getMinThreshold()));
		textFieldsVBox.getChildren().addAll(ingredientTF,categoryTF,thresholdTF);
		
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
            	
            	//TO DO: Do the thing.
            
            }
         });
		
		//Place remove button.
		Button removeButton = new Button("Remove");
		removeButton.setMinHeight(buttonHeight);
		removeButton.setMinWidth(buttonWidth/2);
		removeButton.getStyleClass().add("redButton");
		
		//Remove button action event.
		removeButton.setOnAction(new EventHandler<ActionEvent>() {
   		 
            @Override
            public void handle(ActionEvent event) {
            	
            	//TO DO: Remove the thing.
            
            }
         });
		
		//Put the buttons into an HBox.
		HBox buttonHBox = new HBox();
		buttonHBox.setSpacing(50);
		buttonHBox.setAlignment(Pos.CENTER);
		buttonHBox.getChildren().addAll(saveButton,removeButton);

		
		//Add children to the main layout VBox.
		layout.getChildren().addAll(header,settingsHBox,buttonHBox);
		
		
	}

}
