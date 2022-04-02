package ipz.coursework.pie_chart_editor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Main class of the program
 *
 * */
public class Main extends Application {
    /**
     * creating the initial program window
     *
     * */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("pie_chart_editor");
        Image icon = new Image("file:icon.png");
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * launch the program
     *
     * */
    public static void main(String[] args) {
        launch();
    }
}