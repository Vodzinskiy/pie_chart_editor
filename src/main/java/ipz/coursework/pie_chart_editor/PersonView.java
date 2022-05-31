package ipz.coursework.pie_chart_editor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

public class PersonView {

    @FXML
    private Hyperlink openLink1;

    @FXML
    private Hyperlink openLink2;

    @FXML
    private Hyperlink openLink3;

    @FXML
    private Label Arsen1;

    @FXML
    private Label Bohdan1;

    @FXML
    private Label Developers1;

    @FXML
    private Label Gmail;

    @FXML
    private Label Roma1;

    @FXML
    private Label Telegram;




    @FXML
    private Label Developers;

    private final Stage thisStage = new Stage();

    private final MainController mainController;

    public PersonView(MainController mainController) {
        this.mainController = mainController;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("person-view.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.setTitle("Розробники");
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


    void openLink1() throws Exception{

        Desktop.getDesktop().browse(new URI("https://t.me/vodziskiy"));
    }
    void openLink2() throws Exception{

        Desktop.getDesktop().browse(new URI("https://t.me/arseniyb777"));
    }
    void openLink3() throws Exception{

        Desktop.getDesktop().browse(new URI("https://t.me/bk_cullinan"));
    }

    @FXML
    void initialize() {
        mainController.getSettingsController().setCreatorsStage(thisStage);
        openLink1.setOnAction(actionEvent -> {
            try {
                openLink1();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        openLink2.setOnAction(actionEvent -> {
            try {
                openLink2();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        openLink3.setOnAction(actionEvent -> {
            try {
                openLink3();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void exit(){thisStage.close();}
}
