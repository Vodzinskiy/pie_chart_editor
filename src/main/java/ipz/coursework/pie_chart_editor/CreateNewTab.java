package ipz.coursework.pie_chart_editor;

import java.io.IOException;
import javafx.fxml.FXML;
<<<<<<< HEAD
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
=======
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
>>>>>>> master

/**
 * Class - controller for set/change tab name window
 */
public class CreateNewTab {

    @FXML
<<<<<<< HEAD
    private ResourceBundle resources;

    @FXML

    private URL location;

    @FXML
    private Button createTabButton;

    @FXML
    private TextField newTabName;

    @FXML
    void createNewTabButton(ActionEvent event) throws IOException {
        if (newTabName.getText().isEmpty()) {
            if (newTabName.getText().isEmpty()) {
                newTabName.setText("Нова вкладка");
            } else {
                MainController mainController = new MainController();
                mainController.CreateNewTab();
                //System.out.println(newTabName.getText().isEmpty());
            }
=======
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
>>>>>>> master
        }

//        @FXML
//        void initialize(){
//
//        }
//
//        String getNewTabName() {
//            return newTabName.getText();
//        }
    }
<<<<<<< HEAD
=======

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
>>>>>>> master
}