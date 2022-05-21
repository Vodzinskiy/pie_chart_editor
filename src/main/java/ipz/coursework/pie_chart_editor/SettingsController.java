package ipz.coursework.pie_chart_editor;

import java.net.URL;

import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
    private Stage thisStage;
    public Stage tabNameStage;
    private Stage creatorsStage;
    private Stage aboutStage;
    private Stage saveStage;

    private TabPane tabPane;

    // Will hold a reference to the first controller, allowing us to access the methods found there.
    private final MainController mainController;

    public SettingsController(MainController mainController) {
        // We received the first controller, now let's make it usable throughout this controller.
        this.mainController = mainController;

    }

    /**
     * Show the stage that was loaded in the constructor
     */
    public void showStage() {
        try {
            // Create the new stage
            thisStage = new Stage();

            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("settings-view.fxml"));

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
        ObservableList<String> list = FXCollections.observableArrayList("Світла","Темна");
        ObservableList<String> languages = FXCollections.observableArrayList("Англійська","Українська");


        themeChooser.setItems(list);
        LanguageChooser.setItems(languages);
        if(mainController.getDarkChoose()){
            themeChooser.getSelectionModel().select(1);
        }
        else {
            themeChooser.getSelectionModel().selectFirst();
        }

        pieChart = (PieChart) tabPane.getSelectionModel().getSelectedItem().getContent().lookup("PieChart");

        Labels.setSelected(pieChart.getLabelsVisible());
        Legend.setSelected(pieChart.isLegendVisible());

        themeChooser.setOnAction(event -> themeChange(themeChooser.getSelectionModel().getSelectedItem()));
        Legend.selectedProperty().addListener(
                (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                    pieChart.setLegendVisible(Legend.isSelected());
                });
        Labels.selectedProperty().addListener(
                (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                    pieChart.setLabelsVisible(Labels.isSelected());
                });

    }

    void themeChange(String item){
        if (item.equals("Світла") || item.equals("Light")){
            tabPane.getScene().getRoot().getStylesheets().remove(getClass().getResource("style.css").toString());
            thisStage.getScene().getRoot().getStylesheets().remove(getClass().getResource("style.css").toString());
            tabNameStage.getScene().getRoot().getStylesheets().remove(getClass().getResource("style.css").toString());
            //creatorsStage.getScene().getRoot().getStylesheets().remove(getClass().getResource("style.css").toString());
            //aboutStage.getScene().getRoot().getStylesheets().remove(getClass().getResource("style.css").toString());
            saveStage.getScene().getRoot().getStylesheets().remove(getClass().getResource("style.css").toString());
            mainController.setDarkChoose(false);
        }
        if (item.equals("Темна") || item.equals("Dark")){
            tabPane.getScene().getRoot().getStylesheets().add(getClass().getResource("style.css").toString());
            thisStage.getScene().getRoot().getStylesheets().add(getClass().getResource("style.css").toString());
            tabNameStage.getScene().getRoot().getStylesheets().add(getClass().getResource("style.css").toString());
            //creatorsStage.getScene().getRoot().getStylesheets().add(getClass().getResource("style.css").toString());
            //aboutStage.getScene().getRoot().getStylesheets().remove(getClass().getResource("style.css").toString());
            saveStage.getScene().getRoot().getStylesheets().remove(getClass().getResource("style.css").toString());
            mainController.setDarkChoose(true);

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
