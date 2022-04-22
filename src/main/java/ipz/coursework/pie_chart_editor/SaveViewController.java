package ipz.coursework.pie_chart_editor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.stage.Stage;


/**
 * class - controller for closing warning
 */
public class SaveViewController {

    @FXML
    private Button cancel;

    @FXML
    private Button dontSave;

    @FXML
    private Button save;

    @FXML
    private Label saveText;

    private Stage thisStage;

    Tab tab;

    public void setTab(Tab tab){
        this.tab = tab;
    }

    // Will hold a reference to the first controller, allowing us to access the methods found there.
    private final MainController mainController;

    public SaveViewController(MainController mainController) {
        // We received the first controller, now let's make it usable throughout this controller.
        this.mainController = mainController;
    }

    @FXML
    void initialize() {
        dontSave.setOnAction(event -> notSave());
        cancel.setOnAction(event -> close());
        save.setOnAction(event -> save());

        saveText.setText("Зберегти зміни в \""+tab.getText()+"\" ?");
    }

    void close(){
        thisStage.close();
    }

    void notSave(){
        tab.getTabPane().getTabs().remove(tab);
        thisStage.close();
    }

    void save(){
        if(mainController.saveToFile()){
            notSave();
        }
    }

    /**
     * Show the stage that was loaded in the constructor
     */
    public void showStage() {
        try {
            // Create the new stage
            thisStage = new Stage();

            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("save-view.fxml"));

            // Set this class as the controller
            loader.setController(this);

            // Load the scene
            thisStage.setScene(new Scene(loader.load()));

            // Setup the window/stage
            thisStage.setTitle("CreateNewTab");

        } catch (Exception ignored) {
        }
        thisStage.showAndWait();
    }
}