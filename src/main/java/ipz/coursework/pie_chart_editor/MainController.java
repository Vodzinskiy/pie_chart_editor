package ipz.coursework.pie_chart_editor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * the main controller, which contains the menu bar
 * and all the buttons that are present in it,
 * as well as the tab bar
 *
 * */
public class MainController {

    private final Stage thisStage;

    @FXML
    private MenuItem Dark;

    @FXML
    private MenuItem aboutProject;

    @FXML
    private MenuItem creators;

    @FXML
    private Menu settings;

    @FXML
    private MenuItem Light;

    @FXML
    private Menu thememenu;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    /**
     * menu item for creating tabs
     */
    @FXML
    private MenuItem newTab;
    /**
     * menu item for change the theme
     */
    @FXML
    private MenuItem changeTheme;
    /**
     * menu item for open file
     */
    @FXML
    private MenuItem openFile;
    /**
     * menu item for save file
     */
    @FXML
    private MenuItem saveFile;
    /**
     * menu item for closing the program
     */
    @FXML
    private MenuItem exit;

    /**
     * tab bar
     */
    @FXML
    private TabPane tabPane;

    List<String> rows = new ArrayList<>();
    public String tabName;
    static List<String> columnOpenName = new ArrayList<>();
    static List<String> columnOpenNum = new ArrayList<>();


    public MainController() {
        // Create the new stage
        thisStage = new Stage();
        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml"));
            // Set this class as the controller
            loader.setController(this);
            // Load the scene
            thisStage.setScene(new Scene(loader.load()));
            // Setup the window/stage
            thisStage.setTitle("pie_chart_editor");
            thisStage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Show the stage that was loaded in the constructor
     */
    public void showStage() {
        thisStage.showAndWait();
    }

    /**
     * this method is performed at the start of the window
     */
    @FXML
    void initialize() {

        /*
         create a shortcut keys to create a new tab (Ctrl + N)
        */
        newTab.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));

        openFile.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));


        newTab.setOnAction(event -> CreateNewTabName());

        openFile.setOnAction(event -> openNewFile());

        saveFile.setOnAction(event -> saveToFile());

        aboutProject.setOnAction(event -> openAbout());

        creators.setOnAction(event -> People());

        settings.setOnAction(event -> Settingsmenu());


        Dark.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                tabPane.getScene().getRoot().getStylesheets().add(getClass().getResource("style.css").toString());
                //System.out.println("pass");
            }
        });
        Light.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                tabPane.getScene().getRoot().getStylesheets().remove(getClass().getResource("style.css").toString());
                //System.out.println("pass");
            }
        });
    }

    /**
     * open window to rename the tab
     *
     */
    void ChangeTabName(){
        CreateNewTab createNewTab = new CreateNewTab(this);
        createNewTab.setNewTabName(tabPane.getSelectionModel().getSelectedItem().getText());
        // Show the new stage/window
        createNewTab.showStage();
        tabPane.getSelectionModel().getSelectedItem().setText(tabName);
    }

    /**
     * open file and assigns data to list`s
     *
     */
    void openNewFile(){
        columnOpenName.clear();
        columnOpenNum.clear();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File openFile = fileChooser.showOpenDialog(new Stage());

        if(openFile != null){
            String fileName = openFile.getName();
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, openFile.getName().length());

            if (fileExtension.equals("xlsx")) {
                int j = 0;
                try {
                    FileInputStream file = new FileInputStream(new File(openFile.getPath()));
                    Workbook workbook = new XSSFWorkbook(file);
                    DataFormatter dataFormatter = new DataFormatter();
                    Iterator<Sheet> sheets = workbook.sheetIterator();
                    while (sheets.hasNext()) {
                        Sheet sh = sheets.next();
                        Iterator<Row> iterator = sh.iterator();
                        while (iterator.hasNext()) {
                            Row row = iterator.next();
                            Iterator<Cell> cellIterator = row.iterator();
                            for (int i = 0; i < 2; i++) {
                                Cell cell = cellIterator.next();
                                String cellValue = dataFormatter.formatCellValue(cell);
                                if (i == 0) {
                                    columnOpenName.add(j, cellValue);
                                }
                                if (i == 1) {
                                    columnOpenNum.add(j, cellValue);
                                }
                            }
                            j++;
                        }
                    }
                    workbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (fileExtension.equals("txt")) {
                try {
                    int j = 0;
                    Scanner scanner = new Scanner(openFile);
                    while (scanner.hasNextLine()) {
                        rows.add(j, scanner.nextLine());
                        j++;
                    }
                    for (int i = 0; i< rows.size();i++){
                        String[] temp = rows.get(i).replaceAll("\\s", "").split(",");
                        columnOpenName.add(i,temp[0]);
                        columnOpenNum.add(i,temp[1]);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            CreateNewTab(openFile.getName().replace(".xlsx", "").replace(".txt", ""));
        }
    }

    void saveToFile() {
        //save file
    }

    /**
     * creates a window to specify the name of the new tab
     */
    public void CreateNewTabName() {
        columnOpenName.clear();
        columnOpenNum.clear();
        CreateNewTab createNewTab = new CreateNewTab(this);
        // Show the new stage/window
        createNewTab.showStage();
        CreateNewTab(tabName);
    }

    @FXML
    void changetheme(ActionEvent event) {
    }

    /**
     * create new tab
     */
    public void CreateNewTab(String name) {

        if(name  != null){
            Tab nTab = new Tab(name);

            FXMLLoader nLoader = new FXMLLoader(getClass().getResource("tab-view.fxml"));
            try {
                Parent nRoot = nLoader.load();
                TabViewController controller = nLoader.getController();
                controller.createTableOpenFile();
                nTab.setContent(nRoot);
                ContextMenu contextMenu = new ContextMenu();
                //Creating the menu Items for the context menu
                MenuItem item = new MenuItem("переіменувати");
                item.setOnAction(event -> ChangeTabName());
                contextMenu.getItems().addAll(item);
                //Adding the context menu to the button and the text field
                nTab.setContextMenu(contextMenu);
            } catch (IOException e) {
                e.printStackTrace();
            }
            tabPane.getTabs().add(nTab);
        }
    }



    /**
     * set tab name to create or rename
     * @param tabName
     */
    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    /**
     * returns a list of names from an open file
     * @return
     */
    public List<String> getColumnOpenName() {
        return columnOpenName;
    }
    /**
     * returns a list of numbers from an open file
     * @return
     */
    public List<String> getColumnOpenNum() {
        return columnOpenNum;
    }

    /**
     * create about window
     *
     */

    void openAbout() {
    try {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("about-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Про програму");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    } catch (Exception e) {
        e.printStackTrace();
    }


    }

    void People(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("person-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Розробники");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void Settingsmenu()  {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("settingsmenu-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("settings");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

