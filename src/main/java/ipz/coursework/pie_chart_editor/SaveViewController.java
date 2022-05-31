package ipz.coursework.pie_chart_editor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


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
    }

    void close(){
        thisStage.close();
    }

    void notSave(){
        tab.getTabPane().getTabs().remove(tab);
        thisStage.close();
        mainController.helpTextVisible();
    }

    void save(){
        if(mainController.saveToFile()){
            notSave();
        }
        mainController.helpTextVisible();
    }

    /**
     * Show the stage that was loaded in the constructor
     */
    public void showStage() {
        mainController.getSettingsController().setSaveStage(thisStage);
        try {
            // Create the new stage
            thisStage = new Stage();

            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("save-view.fxml"));

            // Set this class as the controller
            loader.setController(this);

            // Load the scene
            thisStage.setScene(new Scene(loader.load()));
            Image icon = new Image("file:icon.png");
            thisStage.getIcons().add(icon);
            thisStage.setResizable(false);
            Properties props = new Properties();
            props.loadFromXML(new FileInputStream("settings.xml"));
            if (props.getProperty("theme").equals("Light")){
                thisStage.getScene().getRoot().getStylesheets().remove(getClass().getResource("style.css").toString());
            }
            if (props.getProperty("theme").equals("Dark")){
                thisStage.getScene().getRoot().getStylesheets().add(getClass().getResource("style.css").toString());
            }
            if (props.getProperty("language").equals("English")){
                language("English.xml");
            }
            if (props.getProperty("language").equals("Ukrainian")){
                language("Ukraine.xml");
            }
        } catch (Exception ignored) {
        }
        thisStage.showAndWait();
    }

    void language(String res) throws IOException {
        try{
            Properties prop = new Properties();
            prop.loadFromXML(new FileInputStream(res));
            saveText.setText((prop.getProperty("saveWindowLabel")) + " \""+tab.getText()+"\" ?");
            save.setText(prop.getProperty("saveButton"));
            dontSave.setText(prop.getProperty("dontSaveButton"));
            cancel.setText(prop.getProperty("cancel"));
        }
        catch (Exception ignored){}

    }
}