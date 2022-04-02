package ipz.coursework.pie_chart_editor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * the main controller, which contains the menu bar
 * and all the buttons that are present in it,
 * as well as the tab bar
 *
 * */
public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    /**
     * menu item for creating tabs
     *
     * */
    @FXML
    private MenuItem newTab;
    /**
     * menu item for open file
     *
     * */
    @FXML
    private MenuItem openFile;
    /**
     * menu item for save file
     *
     * */
    @FXML
    private MenuItem saveFile;
    /**
     * menu item for closing the program
     *
     * */
    @FXML
    private MenuItem exit;

    /**
     * tab bar
     *
     * */
    @FXML
    private TabPane tabPane;

    /**
     * this method is performed at the start of the window
     *
     * */
    @FXML
    void initialize() {
        /*
         create a shortcut keys to create a new tab (Ctrl + N)
        */
        newTab.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));

        /*
        create a new tab at the start of the window
         */
        try {
            Tab tab = new Tab("new tab");//here "new tab" is a name of the tab
            tabPane.getTabs().add(tab);
            tab.setContent((Node) FXMLLoader.load(this.getClass().getResource("tab-view.fxml")));//"tab-view.fxml" - is fxlm-file from which the tab is built
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * creates a window to specify the name of the new tab
     * @param actionEvent
     */
    public void CreateNewTabName(ActionEvent actionEvent)  {

        /*
           !!!!
           Not finished yet,
           ideally, it should create a window in which the user specifies
            the name of the new tab-file and the "create" button creates this tab.

            The problem is that it is not possible to link pressing
            a button in CreateNewTab class with the creation of a new tab

            now this method creates a tab with the given name, if you have ideas - write to me
         */


        //to better times...

        /*FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("createNewTab.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("name");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();*/
        CreateNewTab();

    }

    /**
     * create new tab
     *
     * */
    public void CreateNewTab(){
        try {
            Tab tab = new Tab("new tab");
            tabPane.getTabs().add(tab);
            tab.setContent((Node) FXMLLoader.load(this.getClass().getResource("tab-view.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}