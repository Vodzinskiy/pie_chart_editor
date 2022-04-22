package ipz.coursework.pie_chart_editor;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Main class of the program
 *
 * */
public class Main extends Application {

    /**
     * launch the program
     *
     * */
    public static void main(String[] args) {
        launch();
    }

    /**
     * creating the initial program window
     *
     * */
    @Override
    public void start(Stage stage) throws IOException {

        MainController mainController = new MainController();

        mainController.showStage();

    }



}