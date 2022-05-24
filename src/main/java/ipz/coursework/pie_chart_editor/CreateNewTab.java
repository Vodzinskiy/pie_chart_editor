package ipz.coursework.pie_chart_editor;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


/**
 * Class - controller for set/change tab name window
 */
public class CreateNewTab{

    @FXML
    private TextField newTabName;

    @FXML
    private Button createNewTabButton;

    // Holds this controller's Stage
    private Stage thisStage = new Stage();


    // Will hold a reference to the first controller, allowing us to access the methods found there.
    private final MainController mainController;

    List<String> namesOfTabs = new ArrayList<String>();


    public CreateNewTab(MainController mainController) {
        this.mainController = mainController;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateNewTab.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.setTitle("CreateNewTab");
            Properties props = new Properties();
            props.loadFromXML(new FileInputStream("settings.xml"));
            if (props.getProperty("theme").equals("Light")){
                thisStage.getScene().getRoot().getStylesheets().remove(getClass().getResource("style.css").toString());
            }
            if (props.getProperty("theme").equals("Dark")){
                thisStage.getScene().getRoot().getStylesheets().add(getClass().getResource("style.css").toString());
            }
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
    void initialize(){
        createNewTabButton.setOnAction(event -> showCreateNewTabWindow());
        newTabName.setText("Новий");
        mainController.getSettingsController().setTabNameStage(thisStage);
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
