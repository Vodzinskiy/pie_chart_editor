package ipz.coursework.pie_chart_editor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;


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

        openFile.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));

        /*
        create a new tab at the start of the window
         */
        CreateNewTab();
    }
    /**
     *
     * open txt file and assigns data to variables 'file'
     * */

    @FXML
    void openNewFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel Files", "*.xls"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showOpenDialog(new Stage());

        String fileName = file.getName();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, file.getName().length());

        if (fileExtension.equals("xlsx")){
            //here code for xlsx
        }
        else if (fileExtension.equals("txt")){
            //here code for txt
        }


    }

    @FXML
    void saveToFile(ActionEvent event) {

    }

    /**
     * creates a window to specify the name of the new tab
     * @param actionEvent
     */
    public void CreateNewTabName(ActionEvent actionEvent) throws IOException {

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
            Tab tab = new Tab("new tab");//here "new tab" is a name of the tab
            tabPane.getTabs().add(tab);
            //"tab-view.fxml" - is fxlm-file from which the tab is built
            tab.setContent((Node) FXMLLoader.load(this.getClass().getResource("tab-view.fxml")));
            ContextMenu contextMenu = new ContextMenu();
            //Creating the menu Items for the context menu
            MenuItem item1 = new MenuItem("закрити");
            MenuItem item2 = new MenuItem("переіменувати");
            contextMenu.getItems().addAll(item1, item2);
            //Adding the context menu to the button and the text field
            tab.setContextMenu(contextMenu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}