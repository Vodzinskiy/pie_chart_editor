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
import java.util.Objects;
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

    private final MainController mainController;

    /**
     *SaveViewController-window startup method
     */
    public SaveViewController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * method which is started at start SaveViewController class
     */
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
            thisStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("save-view.fxml"));
            loader.setController(this);

            thisStage.setScene(new Scene(loader.load()));
            Image icon = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("icon.png")));
            thisStage.getIcons().add(icon);
            thisStage.setResizable(false);
            Properties props = new Properties();
            props.loadFromXML(Objects.requireNonNull(this.getClass().getResourceAsStream("settings.xml")));
            if (props.getProperty("theme").equals("Light")){
                thisStage.getScene().getRoot().getStylesheets().remove(Objects.requireNonNull(getClass().getResource("style.css")).toString());
            }
            if (props.getProperty("theme").equals("Dark")){
                thisStage.getScene().getRoot().getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toString());
            }
            if (props.getProperty("language").equals("English")){
                language("English.xml");
            }
            if (props.getProperty("language").equals("Ukrainian")){
                language("Ukraine.xml");
            }
        } catch (Exception ignored) {}
        thisStage.showAndWait();
    }

    /**
     *translation SaveViewController window
     */
    void language(String res){
        try{
            Properties prop = new Properties();
            prop.loadFromXML(Objects.requireNonNull(this.getClass().getResourceAsStream(res)));
            saveText.setText((prop.getProperty("saveWindowLabel")) + " \""+tab.getText()+"\" ?");
            save.setText(prop.getProperty("saveButton"));
            dontSave.setText(prop.getProperty("dontSaveButton"));
            cancel.setText(prop.getProperty("cancel"));
        }
        catch (Exception ignored){}
    }

    /**
     * specify a name for the closed tab
     */
    public void setTab(Tab tab){
        this.tab = tab;
    }

    /**
     * closing about-window
     */
    public void exit(){thisStage.close();}
}