package ipz.coursework.pie_chart_editor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileInputStream;
import java.net.URI;
import java.util.Objects;
import java.util.Properties;

/**
 * class controller for PersonView-window
 */
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

    private final Stage thisStage = new Stage();

    private final MainController mainController;

    /**
     *PersonView-window startup method
     */
    public PersonView(MainController mainController) {
        this.mainController = mainController;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("person-view.fxml"));
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
                language("English.xml");
            }
            if (props.getProperty("language").equals("Ukrainian")){
                language("Ukraine.xml");
            }
        } catch (Exception ignored) {}
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

    /**
     * method which is started at start PersonView class
     */
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
    /**
     *translation PersonView-window
     */
    void language(String res){
        try{
            Properties prop = new Properties();
            prop.loadFromXML(new FileInputStream(res));
            thisStage.setTitle((prop.getProperty("personWindow")));
            Developers1.setText(prop.getProperty("personTitle"));
            Gmail.setText(prop.getProperty("personMail"));
            Telegram.setText(prop.getProperty("personTg"));
            Roma1.setText(prop.getProperty("Roma"));
            Arsen1.setText(prop.getProperty("Arsen"));
            Bohdan1.setText(prop.getProperty("Bohdan"));
        }
        catch (Exception ignored){}
    }
    /**
     * closing PersonView-window
     */
    public void exit(){thisStage.close();}
}
