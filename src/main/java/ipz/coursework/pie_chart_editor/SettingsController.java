package ipz.coursework.pie_chart_editor;

import java.io.*;
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
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class SettingsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private CheckBox Labels;

    @FXML
    private CheckBox Legend;

    @FXML
    private ComboBox<String> themeChooser;

    @FXML
    private ComboBox<String> LanguageChooser;

    @FXML
    private TableView<DataForPieChart> tableView;
    /**
     * pie chart for get data from TabViewContriller
     */
    @FXML
    private PieChart pieChart;

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
        ObservableList<String> languages = FXCollections.observableArrayList("Англійська","Українська");

        themeChooser.setItems(list);
        LanguageChooser.setItems(languages);

        props.loadFromXML(new FileInputStream("settings.xml"));

        if (props.getProperty("theme").equals("Light")){
            themeChooser.getSelectionModel().select(0);
        }
        if (props.getProperty("theme").equals("Dark")){
            themeChooser.getSelectionModel().select(1);
        }

        if (props.getProperty("language").equals("English")){
            LanguageChooser.getSelectionModel().select(0);
        }
        if (props.getProperty("language").equals("Ukraine")){
            LanguageChooser.getSelectionModel().select(1);
        }

        if (props.getProperty("legend").equals("true")){
            Legend.setSelected(true);
        }
        if (props.getProperty("legend").equals("false")){
            Legend.setSelected(false);
        }

        if (props.getProperty("labels").equals("true")){
            Labels.setSelected(true);
        }
        if (props.getProperty("labels").equals("false")){
            Labels.setSelected(false);
        }

        themeChooser.setOnAction(event -> {
            try {
                themeChange(themeChooser.getSelectionModel().getSelectedItem());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        LanguageChooser.setOnAction(event -> {
            try {
                languageChange(LanguageChooser.getSelectionModel().getSelectedItem());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Labels.setOnAction(event -> {
            props.replace("labels", String.valueOf(Labels.isSelected()));
            try {
                props.storeToXML(new FileOutputStream("settings.xml"), "");
            } catch (IOException ignored) {}
            try{
                ((PieChart) tabPane.getSelectionModel().getSelectedItem().getContent().lookup("PieChart")).setLabelsVisible(Labels.isSelected());
            }
            catch (Exception ignored){}
        });

        Legend.setOnAction(event -> {
            props.replace("legend", String.valueOf(Legend.isSelected()));
            try {
                props.storeToXML(new FileOutputStream("settings.xml"), "");
            } catch (IOException ignored) {}
            try{
                ((PieChart) tabPane.getSelectionModel().getSelectedItem().getContent().lookup("PieChart")).setLegendVisible(Legend.isSelected());
            }
            catch (Exception ignored){}
        });
    }

    void languageChange(String item) throws IOException {
        if(item.equals("Ukraine") || item.equals("Українська")){
            props.setProperty("language","Ukraine");
            props.storeToXML(new FileOutputStream("settings.xml"), "");

            //here cod for Ukraine
        }
        if(item.equals("English") || item.equals("Англійська")) {
            props.setProperty("language","English");
            props.storeToXML(new FileOutputStream("settings.xml"), "");

            //here cod for English
        }
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
