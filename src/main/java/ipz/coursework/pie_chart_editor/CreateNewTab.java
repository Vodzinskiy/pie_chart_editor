package ipz.coursework.pie_chart_editor;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Class - controller for set/change tab name window
 */
public class CreateNewTab{

    @FXML
    private TextField newTabName;

    @FXML
    private Button createNewTabButton;

    @FXML
    private Label label;

    private Stage thisStage = new Stage();

    String TabText;

    private final MainController mainController;
    Properties prop = new Properties();

    /**
     *CreateNewTab-window startup method
     */
    public CreateNewTab(MainController mainController) {
        this.mainController = mainController;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateNewTab.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            Image icon = new Image("file:icon.png");
            thisStage.getIcons().add(icon);
            thisStage.setResizable(false);
            Properties props = new Properties();
            props.loadFromXML(new FileInputStream("settings.xml"));
            if (props.getProperty("theme").equals("Light")){
                thisStage.getScene().getRoot().getStylesheets().remove(Objects.requireNonNull(getClass().getResource("style.css")).toString());
            }
            if (props.getProperty("theme").equals("Dark")){
                thisStage.getScene().getRoot().getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toString());
            }
            if (props.getProperty("language").equals("English")){
                languageCreateNewTab("English.xml");
            }
            if (props.getProperty("language").equals("Ukrainian")){
                languageCreateNewTab("Ukraine.xml");
            }
        } catch (Exception ignored) {}
    }

    /**
     * Show the stage that was loaded in the constructor
     */
    public void showStage() {
        thisStage.showAndWait();
    }

    /**
     *translation CreateNewTab window
     */
    void languageCreateNewTab(String res) throws IOException {
        prop.loadFromXML(new FileInputStream(res));
        label.setText(prop.getProperty("CreateTabLabel"));
        createNewTabButton.setText(prop.getProperty("CreateTabButton"));
        newTabName.setText(prop.getProperty("newFile"));
        TabText = prop.getProperty("newFile");
    }

    /**
     * method which is started at start CreateNewTab class
     */
    @FXML
    void initialize(){
        if (newTabName.getText().isEmpty()){
            newTabName.setText(TabText);
            mainController.setTabName(newTabName.getText());
        }
        createNewTabButton.setOnAction(event -> checkIsEmpty());
        mainController.getSettingsController().setTabNameStage(thisStage);
    }

    /**
     * gives the name of the newly created tab
     */
    public void setNewTabName(String name){
        newTabName.setText(name);
    }

    /**
     * check if the tab name is empty
     */
    public void checkIsEmpty() {
        if (newTabName.getText().isEmpty()){
            newTabName.setText(TabText);
        }
        else{
            mainController.setTabName(newTabName.getText());
            thisStage.close();
        }
    }
    /**
     * closing CreateNewTab-window
     */
    public void exit(){thisStage.close();}
}
