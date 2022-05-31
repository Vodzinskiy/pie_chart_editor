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
import javafx.scene.image.Image;
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
    private Label themeLabel;

    @FXML
    private Label languageLabel;

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
    Properties prop = new Properties();

    ObservableList<String> list;
    ObservableList<String> languages;

    private final MainController mainController;

    public SettingsController(MainController mainController) {
        this.mainController = mainController;
        try {
            thisStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("settings-view.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.setTitle("Налаштування");
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
                languageSettings("English.xml");
            }
            if (props.getProperty("language").equals("Ukrainian")){
                languageSettings("Ukraine.xml");
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
        props.loadFromXML(new FileInputStream("settings.xml"));
        if (props.getProperty("language").equals("English")){
            prop.loadFromXML(new FileInputStream("English.xml"));
            list = FXCollections.observableArrayList(prop.getProperty("light"),prop.getProperty("dark"));
            languages = FXCollections.observableArrayList(prop.getProperty("English"),prop.getProperty("Ukrainian"));
        }
        if (props.getProperty("language").equals("Ukrainian")){
            prop.loadFromXML(new FileInputStream("Ukraine.xml"));
            list = FXCollections.observableArrayList(prop.getProperty("light"),prop.getProperty("dark"));
            languages = FXCollections.observableArrayList(prop.getProperty("English"),prop.getProperty("Ukrainian"));
        }

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
        if (props.getProperty("language").equals("Ukrainian")){
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
                languageChange();
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

    void languageChange() throws IOException {
        TabViewController tabViewController = new TabViewController();
        if(LanguageChooser.getSelectionModel().getSelectedItem().equals("Ukrainian") || LanguageChooser.getSelectionModel().getSelectedItem().equals("Українська")){
            props.setProperty("language","Ukrainian");
            props.storeToXML(new FileOutputStream("settings.xml"), "");

            languageSettings("Ukraine.xml");
            mainController.languageMain("Ukraine.xml");
            mainController.getCreateNewTab().languageCreateNewTab("Ukraine.xml");
            mainController.getSaveViewController().language("Ukraine.xml");
            mainController.getAboutView().language("Ukraine.xml");
            mainController.getPersonView().language("Ukraine.xml");
            tabViewController.languageTab("Ukraine.xml");

        }
        if(LanguageChooser.getSelectionModel().getSelectedItem().equals("English") || LanguageChooser.getSelectionModel().getSelectedItem().equals("Англійська")) {
            props.setProperty("language","English");
            props.storeToXML(new FileOutputStream("settings.xml"), "");

            languageSettings("English.xml");
            mainController.languageMain("English.xml");
            mainController.getCreateNewTab().languageCreateNewTab("English.xml");
            mainController.getSaveViewController().language("English.xml");
            mainController.getAboutView().language("English.xml");
            mainController.getPersonView().language("English.xml");
            tabViewController.languageTab("English.xml");
        }
    }

    void languageSettings(String res) throws IOException {
        prop.loadFromXML(new FileInputStream(res));
        Labels.setText(prop.getProperty("labelsCheck"));
        Legend.setText(prop.getProperty("legendCheck"));
        thisStage.setTitle(prop.getProperty("settings"));
        themeLabel.setText(prop.getProperty("themeLabel"));
        languageLabel.setText(prop.getProperty("languageLabel"));
        themeChooser.getItems().set(0,prop.getProperty("light"));
        themeChooser.getItems().set(1,prop.getProperty("dark"));
        LanguageChooser.getItems().set(0,prop.getProperty("English"));
        LanguageChooser.getItems().set(1,prop.getProperty("Ukrainian"));
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

    public void exit(){thisStage.close();}


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
