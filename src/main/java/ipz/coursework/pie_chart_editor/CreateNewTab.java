package ipz.coursework.pie_chart_editor;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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

    // Holds this controller's Stage
    private Stage thisStage = new Stage();

    @FXML
    private Label label;

    String TabText;


    // Will hold a reference to the first controller, allowing us to access the methods found there.
    private final MainController mainController;

    List<String> namesOfTabs = new ArrayList<String>();


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
                thisStage.getScene().getRoot().getStylesheets().remove(getClass().getResource("style.css").toString());
            }
            if (props.getProperty("theme").equals("Dark")){
                thisStage.getScene().getRoot().getStylesheets().add(getClass().getResource("style.css").toString());
            }
            if (props.getProperty("language").equals("English")){
                languageCreateNewTab("English.xml");
            }
            if (props.getProperty("language").equals("Ukrainian")){
                languageCreateNewTab("Ukraine.xml");
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

    void languageCreateNewTab(String res) throws IOException {
        Properties prop = new Properties();
        prop.loadFromXML(new FileInputStream(res));
        label.setText(prop.getProperty("CreateTabLabel"));
        createNewTabButton.setText(prop.getProperty("CreateTabButton"));
        TabText = prop.getProperty("newFile");

    }


    @FXML
    void initialize(){
        createNewTabButton.setOnAction(event -> showCreateNewTabWindow());
        mainController.getSettingsController().setTabNameStage(thisStage);
    }

    public void setNewTabName(String name){
        newTabName.setText(name);
    }

    public void showCreateNewTabWindow() {
        if (newTabName.getText().isEmpty()){
            newTabName.setText(TabText);
        }
        else{
            mainController.setTabName(newTabName.getText());
            thisStage.close();
        }
    }

    public void exit(){thisStage.close();}
}
