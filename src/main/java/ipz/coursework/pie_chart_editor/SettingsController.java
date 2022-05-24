package ipz.coursework.pie_chart_editor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import java.util.Properties;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class SettingsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> themeChooser;

    // Holds this controller's Stage
    private Stage thisStage = new Stage();
    public Stage tabNameStage = new Stage();
    private Stage creatorsStage = new Stage();
    private Stage aboutStage = new Stage();
    private Stage saveStage = new Stage();

    private TabPane tabPane;

    Properties props = new Properties();

    private final MainController mainController;

    public SettingsController(MainController mainController) {
        this.mainController = mainController;
        try {
            thisStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("settings-view.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.setTitle("Налаштування");
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
    void initialize() throws IOException {
        ObservableList<String> list = FXCollections.observableArrayList("Світла","Темна");
        themeChooser.setItems(list);
        props.loadFromXML(new FileInputStream("settings.xml"));
        if (props.getProperty("theme").equals("Light")){
            themeChooser.getSelectionModel().select(0);
        }
        if (props.getProperty("theme").equals("Dark")){
            themeChooser.getSelectionModel().select(1);
        }
        themeChooser.setOnAction(event -> {
            try {
                themeChange(themeChooser.getSelectionModel().getSelectedItem());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    void themeChange(String item) throws IOException {
        if (item.equals("Світла") || item.equals("Light")){
            props.setProperty("theme","Light");
            props.storeToXML(new FileOutputStream("settings.xml"), "");
            try {
                tabPane.getScene().getRoot().getStylesheets().remove(getClass().getResource("style.css").toString());
                thisStage.getScene().getRoot().getStylesheets().remove(getClass().getResource("style.css").toString());
                tabNameStage.getScene().getRoot().getStylesheets().remove(getClass().getResource("style.css").toString());
                creatorsStage.getScene().getRoot().getStylesheets().remove(getClass().getResource("style.css").toString());
                aboutStage.getScene().getRoot().getStylesheets().remove(getClass().getResource("style.css").toString());
                saveStage.getScene().getRoot().getStylesheets().remove(getClass().getResource("style.css").toString());
            }
            catch (Exception ignored){
            }

        }
        if (item.equals("Темна") || item.equals("Dark")){
            props.setProperty("theme","Dark");
            props.storeToXML(new FileOutputStream("settings.xml"), "");
            try{
                tabPane.getScene().getRoot().getStylesheets().add(getClass().getResource("style.css").toString());
                thisStage.getScene().getRoot().getStylesheets().add(getClass().getResource("style.css").toString());
                tabNameStage.getScene().getRoot().getStylesheets().add(getClass().getResource("style.css").toString());
                creatorsStage.getScene().getRoot().getStylesheets().add(getClass().getResource("style.css").toString());
                aboutStage.getScene().getRoot().getStylesheets().add(getClass().getResource("style.css").toString());
                saveStage.getScene().getRoot().getStylesheets().add(getClass().getResource("style.css").toString());
            }
            catch (Exception ignored){
            }
        }
    }

    void setTabPane(TabPane tabPane){
        this.tabPane = tabPane;
    }

    void setTabNameStage(Stage tabNameStage){
        this.tabNameStage = tabNameStage;
    }

    void setSaveStage(Stage saveStage){
        this.saveStage = saveStage;
    }

    void setCreatorsStage(Stage CreatorsStage){
        this.creatorsStage = CreatorsStage;
    }

    void setAboutStage(Stage AboutStage){
        this.aboutStage = AboutStage;
    }
}
