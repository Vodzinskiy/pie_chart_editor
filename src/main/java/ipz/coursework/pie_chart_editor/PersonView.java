package ipz.coursework.pie_chart_editor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PersonView {
    @FXML
    private Label Developers;

    // Holds this controller's Stage
    private Stage thisStage = new Stage();;


    // Will hold a reference to the first controller, allowing us to access the methods found there.
    private final MainController mainController;

    public PersonView(MainController mainController) {
        // We received the first controller, now let's make it usable throughout this controller.
        this.mainController = mainController;
    }
    /**
     * Show the stage that was loaded in the constructor
     */
    public void showStage() {
        try {

            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("person-view.fxml"));

            // Set this class as the controller
            loader.setController(this);

            // Load the scene
            thisStage.setScene(new Scene(loader.load()));

            // Setup the window/stage
            thisStage.setTitle("CreateNewTab");

            if(!mainController.getDarkChoose()){
                thisStage.getScene().getRoot().getStylesheets().remove(getClass().getResource("style.css").toString());
            }
            else {
                thisStage.getScene().getRoot().getStylesheets().add(getClass().getResource("style.css").toString());
            }

        } catch (Exception ignored) {
        }
        thisStage.showAndWait();
    }

    @FXML
    void initialize() {
        mainController.getSettingsController().setCreatorsStage(thisStage);
    }
}
