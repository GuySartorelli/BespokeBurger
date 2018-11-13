package client;

import java.util.ArrayList;
import java.util.List;

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
 * Used as a modal display to add and remove categories from the database.
 * @author Bespoke Burgers
 *
 */
public class EditCategoriesModal extends Stage {
	
	//Attributes
	VBox layout;
	List<Category> categories;
		
	/**
	 * Constructor
	 * @param parentStage Stage: the Stage object that the modal was called from.
	 * @param catgeories List<String>: a list of strings representing the names of the current categories 
	 * in the database.
	 */
	public EditCategoriesModal(Stage parentStage, List<Category> categories) {
		
		this.setTitle("Edit Categories");
		this.categories = categories;
		
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
		
		//Header.
		Label header = new Label("Edit Categories");
		header.getStyleClass().add("headerLabel");
		
		//Title labels
		VBox titlesVBox = new VBox();
		for (int i = 0; i < categories.size(); i++) {
			
			Label categoryTitle = new Label(categories.get(i).getName());
			categoryTitle.setMinHeight(40);
			categoryTitle.getStyleClass().add("normalLabel");

			titlesVBox.getChildren().add(categoryTitle);
		}
		
		//Create and add Remove buttons.
		VBox buttonsVBox = new VBox();
		for (int i = 0; i < categories.size(); i++) {

			Button removeButton = new Button("Remove");
			removeButton.setMinHeight(35);
			removeButton.getStyleClass().add("redButton");
			removeButton.setId(Integer.toString(i));

			buttonsVBox.getChildren().add(removeButton);

			removeButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					System.out.println("Removing category at index " + removeButton.getId() );

					//TO DO: Remove the thing.
				}
			});
		}
		

		//Add title label and button VBoxes to an HBox.
		HBox currentCategories = new HBox();
		currentCategories.setSpacing(20);
		currentCategories.getChildren().addAll(titlesVBox,buttonsVBox);
		
		//Add new HBox/controls
		HBox addNewHBox = new HBox();
		addNewHBox.setAlignment(Pos.CENTER);
		Label addTitle = new Label("Add New:");
		TextField addTF = new TextField();
		Button saveButton = new Button("Save");
		addNewHBox.getChildren().addAll(addTitle,addTF,saveButton);
		
		//Done button action event.
		saveButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				String newCategory = addTF.getText();

				//TO DO: Do the thing.

			}
		});

		
		//Add children to the main layout VBox.
		layout.getChildren().addAll(header,currentCategories,addNewHBox);
		
		
	}

}
