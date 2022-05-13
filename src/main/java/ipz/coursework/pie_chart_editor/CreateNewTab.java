package ipz.coursework.pie_chart_editor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.imageio.plugins.tiff.FaxTIFFTagSet;

/**
 * Class - controller for set/change tab name window
 */
public class CreateNewTab {

    @FXML
    private TextField newTabName;

    @FXML
    private Button createNewTabButton;

    // Holds this controller's Stage
    private Stage thisStage;

    // Will hold a reference to the first controller, allowing us to access the methods found there.
    private final MainController mainController;

    List<String> namesOfTabs = new ArrayList<String>();


    public CreateNewTab(MainController mainController) {
        // We received the first controller, now let's make it usable throughout this controller.
        this.mainController = mainController;

        try {
            // Create the new stage
            thisStage = new Stage();

            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateNewTab.fxml"));

            // Set this class as the controller
            loader.setController(this);

            // Load the scene
            thisStage.setScene(new Scene(loader.load()));

            // Setup the window/stage
            thisStage.setTitle("CreateNewTab");


        } catch (Exception ignored) {
        }
    }

    /**
     * Show the stage that was loaded in the constructor
     */
    public void showStage() {
        thisStage.showAndWait();
    }


    @FXML
    void initialize() throws IOException {
        createNewTabButton.setOnAction(event -> showCreateNewTabWindow());
        newTabName.setText("Новий");
    }

//    public boolean checkElementInArray(String[] arr, String s){
//        return Arrays.asList(arr).contains(s);
//    }



    public void setNewTabName(String name){
        newTabName.setText(name);
    }

    public void showCreateNewTabWindow() {
        if (newTabName.getText().isEmpty()){
            newTabName.setText("Новий");
//            namesOfTabs.add(newTabName.getText());
//            System.out.println(namesOfTabs.toString());
        }

        else{
            mainController.setTabName(newTabName.getText());
//            namesOfTabs.add(newTabName.getText());
//            System.out.println(namesOfTabs.toString());
            thisStage.close();
        }
    }
}
