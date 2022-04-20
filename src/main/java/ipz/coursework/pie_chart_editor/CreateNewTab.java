package ipz.coursework.pie_chart_editor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;

public class CreateNewTab {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField newTabName;

    @FXML
    private Button createNewTabButton;

    // Holds this controller's Stage
    private Stage thisStage;

    // Will hold a reference to the first controller, allowing us to access the methods found there.
    private final MainController mainController;

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
    }

    public void setNewTabName(String name){
        newTabName.setText(name);
    }

    public void showCreateNewTabWindow() {
        if (newTabName.getText().isEmpty()){
            newTabName.setText("Новий");
        }
        else{
            mainController.setTabName(newTabName.getText());
            thisStage.close();
        }
    }
}