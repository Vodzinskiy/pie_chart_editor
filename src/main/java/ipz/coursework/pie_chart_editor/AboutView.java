package ipz.coursework.pie_chart_editor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.Objects;
import java.util.Properties;

/**
 * class controller for about-window
 */
public class AboutView {
    @FXML
    private Label Programprovision;

    @FXML
    private Label Redactor;

    @FXML
    private Label Version;

    @FXML
    private Label Zbirka;

    private final Stage thisStage = new Stage();

    private final MainController mainController;

    /**
     *about-window startup method
     */
    public AboutView(MainController mainController) {
        this.mainController = mainController;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("about-view.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            Image icon = new Image("file:icon.png");
            thisStage.getIcons().add(icon);
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

    /**
     * method which is started at start AboutView class
     */
    @FXML
    void initialize() {
        mainController.getSettingsController().setAboutStage(thisStage);
    }

    /**
     *translation about window
     */
    void language(String res){
        try{
            Properties prop = new Properties();
            prop.loadFromXML(new FileInputStream(res));
            Redactor.setText((prop.getProperty("aboutTitle")));
            thisStage.setTitle((prop.getProperty("aboutWindow")));
            Version.setText((prop.getProperty("aboutVersion")));
            Zbirka.setText((prop.getProperty("aboutCreateDate")));
            Programprovision.setText((prop.getProperty("aboutProgram")));
        }
        catch (Exception ignored){}
    }

    /**
     * closing about-window
     */
    public void exit(){thisStage.close();}
}