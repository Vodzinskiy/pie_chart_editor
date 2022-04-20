package ipz.coursework.pie_chart_editor;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;

import java.io.*;

import java.net.URL;
import java.util.*;


import javafx.stage.WindowEvent;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * the main controller, which contains the menu bar
 * and all the buttons that are present in it,
 * as well as the tab bar
 *
 * */
public class MainController {

    /**
     * main stage
     */
    private final Stage thisStage;

    @FXML
    private MenuItem Dark;
    /**
     * menu item which opens about project window
     */
    @FXML
    private MenuItem aboutProject;
    /**
     * menu item which opens list of creators window
     */
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
     * menu item for save file from the already open
     */
    @FXML
    private MenuItem saveFile;
    /**
     * menu item for save file as xlsx or  txt
     */
    @FXML
    private  MenuItem saveFileAs;
    /**
     * menu item for save pie chart image
     */
    @FXML
    private  MenuItem savePieChartPicture;
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
    /**
     * table view for get data from TabViewContriller
     */
    @FXML
    private TableView<DataForPieChart> tableView;
    /**
     * pie chart for get data from TabViewContriller
     */
    @FXML
    private PieChart pieChart;

    List<String> rows = new ArrayList<>();
    public String tabName;
    static List<String> columnOpenName = new ArrayList<>();
    static List<String> columnOpenNum = new ArrayList<>();
    String filePath;
    SaveViewController saveViewController = new SaveViewController(this);


    /**
     * method which launches main window
     */
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
            thisStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent e) {
                    closeWindow(e);
                }
            });
            thisStage.setTitle("pie_chart_editor");
            Image icon = new Image("file:icon.png");
            thisStage.getIcons().add(icon);
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

        newTab.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));

        openFile.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));

        saveFile.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));

        saveFileAs.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));

        savePieChartPicture.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));

        exit.setAccelerator(new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN));


        newTab.setOnAction(event -> CreateNewTabName());

        openFile.setOnAction(event -> openNewFile());

        saveFile.setOnAction(event -> saveToFile());

        saveFileAs.setOnAction(event -> saveToFileAs());

        savePieChartPicture.setOnAction(event -> savePieChartPicture());

        aboutProject.setOnAction(event -> openAbout());

        creators.setOnAction(event -> People());

        settings.setOnAction(event -> Settingsmenu());

        exit.setOnAction(event -> exit());

        Dark.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                tabPane.getScene().getRoot().getStylesheets().add(getClass().getResource("style.css").toString());
            }
        });
        Light.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                tabPane.getScene().getRoot().getStylesheets().remove(getClass().getResource("style.css").toString());
            }
        });
    }

    /**
     * close application
     */
    void exit(){
        thisStage.close();
    }

    /**
     * open window to rename the tab
     */
    void ChangeTabName(Tab ntab){
        CreateNewTab createNewTab = new CreateNewTab(this);
        createNewTab.setNewTabName(ntab.getText());
        // Show the new stage/window
        createNewTab.showStage();
        ntab.setText(tabName);
    }

    /**
     *start when tab closing
     */
    void closeTab(Event arg0, Tab tab){
        if(!listComparison(tab)){
            // Show the new stage/window
            saveViewController.showStage();
            arg0.consume();
        }
    }


    /**
     *start when window closing
     */
    void closeWindow(Event e){
        e.consume();

        for (int i = tabPane.getTabs().size();i>0;i--){

            if(!listComparison(tabPane.getTabs().get(i-1))){
                // Show the new stage/window
                saveViewController.setTab(tabPane.getTabs().get(i-1));
                saveViewController.showStage();

            }
            else{
                tabPane.getTabs().get(i-1).getTabPane().getTabs().remove(tabPane.getTabs().get(i-1));
            }
            if(tabPane.getTabs().size() == 0){
                exit();
            }
        }
    }

    /**
     *Compares the last saved data with the data in the table
     */
    public boolean listComparison(Tab tab){

        List<String> nameChange = new ArrayList<>();
        List<String> numChange = new ArrayList<>();

        List<String> nameLastSave = new ArrayList<>();
        List<String> numLastSave = new ArrayList<>();

        tableView = (TableView) tab.getContent().lookup("TableView");

        for (int i = 0; i<tableView.getItems().size();i++){
            nameChange.add(i,tableView.getItems().get(i).getName());
        }

        for (int i = 0; i<tableView.getItems().size();i++){
            numChange.add(i,tableView.getItems().get(i).getNum());
        }

        if(tab.getUserData() != null) {
            String fileName = (String) tab.getUserData();
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, ((String) tab.getUserData()).length());

            if (fileExtension.equals("xlsx")) {
                int j = 0;
                try {
                    FileInputStream file = new FileInputStream((String) tab.getUserData());
                    Workbook workbook = new XSSFWorkbook(file);
                    DataFormatter dataFormatter = new DataFormatter();
                    Iterator<Sheet> sheets = workbook.sheetIterator();
                    while (sheets.hasNext()) {
                        Sheet sh = sheets.next();
                        for (Row row : sh) {
                            Iterator<Cell> cellIterator = row.iterator();
                            for (int i = 0; i < 2; i++) {
                                Cell cell = cellIterator.next();
                                String cellValue = dataFormatter.formatCellValue(cell);
                                if (i == 0) {
                                    nameLastSave.add(j, cellValue);
                                }
                                if (i == 1) {
                                    numLastSave.add(j, cellValue);
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
                int j = 0;
                Scanner scanner = new Scanner((String) tab.getUserData());
                while (scanner.hasNextLine()) {
                    rows.add(j, scanner.nextLine());
                    j++;
                }
                for (int i = 0; i < rows.size(); i++) {
                    String[] temp = rows.get(i).replaceAll("\\s", "").split(",");
                    nameLastSave.add(i, temp[0]);
                    numLastSave.add(i, temp[1]);
                }
            }
        }
        if (nameChange.equals(nameLastSave)){
            if (numChange.equals(numLastSave)){
                return true;
            }
        }

        return false;
    }

    /*
    *
    * Open,create tab
    *
     */
    /**
     * creates a window to specify the name of the new tab
     */
    public void CreateNewTabName() {
        columnOpenName.clear();
        columnOpenNum.clear();
        filePath = null;
        tabName = null;
        CreateNewTab createNewTab = new CreateNewTab(this);
        // Show the new stage/window
        createNewTab.showStage();
        CreateNewTab(tabName);
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
                nTab.setUserData(filePath);
                nTab.setOnCloseRequest(new EventHandler<Event>()
                {
                    @Override
                    public void handle(Event arg0)
                    {
                        closeTab(arg0, nTab);
                    }
                });
                saveViewController.setTab(nTab);
                ContextMenu contextMenu = new ContextMenu();
                //Creating the menu Items for the context menu
                MenuItem item = new MenuItem("переіменувати");
                item.setOnAction(event -> ChangeTabName(nTab));
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
     * open file and assigns data to list`s
     */
    void openNewFile(){
        try{
            columnOpenName.clear();
            columnOpenNum.clear();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            File fileOpen = fileChooser.showOpenDialog(new Stage());
            filePath = fileOpen.getAbsolutePath();

            if(openFile != null){
                String fileName = fileOpen.getName();
                String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, fileOpen.getName().length());

                if (fileExtension.equals("xlsx")) {
                    int j = 0;
                    try {
                        FileInputStream file = new FileInputStream(fileOpen.getPath());
                        Workbook workbook = new XSSFWorkbook(file);
                        DataFormatter dataFormatter = new DataFormatter();
                        Iterator<Sheet> sheets = workbook.sheetIterator();
                        while (sheets.hasNext()) {
                            Sheet sh = sheets.next();
                            for (Row row : sh) {
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
                        Scanner scanner = new Scanner(fileOpen);
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
                CreateNewTab(fileOpen.getName().replace(".xlsx", "").replace(".txt", ""));
            }
        }
        catch (Exception ignored){
        }
    }

    /*
    *
    * save tab
    *
     */

    /**
     * save pie chart as PNG
     */
    void  savePieChartPicture(){
        try{
            tableView = (TableView) tabPane.getSelectionModel().getSelectedItem().getContent().lookup("TableView");
            pieChart = (PieChart) tabPane.getSelectionModel().getSelectedItem().getContent().lookup("PieChart");
            FileChooser fileChooser = new FileChooser();
            //Set extension filter to .xlsx files
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
            fileChooser.getExtensionFilters().add(extFilter);

            if (!tableView.getItems().get(0).getName().isEmpty()){
                //Show save file dialog
                File file = fileChooser.showSaveDialog(new Stage());
                if(file != null){
                    pieChart.setLabelsVisible(true);
                    pieChart.setLegendVisible(false);

                    WritableImage image = pieChart.snapshot(new SnapshotParameters(), null);

                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);

                    pieChart.setLabelsVisible(false);
                    pieChart.setLegendVisible(true);
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Помилка");
                alert.setHeaderText(null);
                alert.setContentText("Відсутні данні для зберігання");
                alert.showAndWait();
            }
        }
        catch (Exception ignored){
        }
    }

    /**
     * save data from table view as .xlsx or txt
     */
    boolean saveToFileAs(){
        try {

        tableView = (TableView)tabPane.getSelectionModel().getSelectedItem().getContent().lookup("TableView");

        List<String> finalNameList = new ArrayList<>();
        for (int i = 0; i<tableView.getItems().size();i++){
            finalNameList.add(i,tableView.getItems().get(i).getName());
        }

        List<String> finalNumList = new ArrayList<>();
        for (int i = 0; i<tableView.getItems().size();i++){
            finalNumList.add(i, tableView.getItems().get(i).getNum());
        }

        List<String> finalInterestList = new ArrayList<>();
        for (int i = 0; i<tableView.getItems().size();i++){
            finalInterestList.add(i, tableView.getItems().get(i).getInterest());
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(tabPane.getSelectionModel().getSelectedItem().getText());

        //Set extension filter to .xlsx files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
        FileChooser.ExtensionFilter extTxtFilter = new FileChooser.ExtensionFilter("Txt files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.getExtensionFilters().add(extTxtFilter);

        if(!tableView.getItems().get(0).getNum().isEmpty()){

            //Show save file dialog
            File file = fileChooser.showSaveDialog(new Stage());

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("1");

            //Data to write to Excel file.
            int rowCount = 0;

            for(int i = 0;i<finalNameList.size();i++){
                XSSFRow row = sheet.createRow(rowCount++);
                int columnCount = 0;

                XSSFCell cell = row.createCell(columnCount++);
                cell.setCellValue(finalNameList.get(i));
                cell = row.createCell(columnCount++);
                cell.setCellValue(finalNumList.get(i));
                cell = row.createCell(columnCount);
                cell.setCellValue(finalInterestList.get(i));
            }

            //If file is not null, write to file using output stream.
            String fileName = file.getName();
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, file.getName().length());

            if (fileExtension.equals("xlsx")) {
                try (FileOutputStream outputStream = new FileOutputStream(file.getAbsolutePath())) {
                    workbook.write(outputStream);
                    tabPane.getSelectionModel().getSelectedItem().setUserData(file.getAbsolutePath());
                    return true;
                }
                catch (IOException ignored) {
                }
            }
            if (fileExtension.equals("txt")){
                try {
                    File fileTxt = new File(file.getAbsolutePath());

                    // Создание объекта FileWriter
                    FileWriter writer = new FileWriter(fileTxt);

                    // Запись содержимого в файл
                    for (int i = 0; i<finalNameList.size();i++){
                        writer.write(finalNameList.get(i)+", "+finalNumList.get(i)+"\n");
                    }
                    writer.flush();
                    writer.close();
                    tabPane.getSelectionModel().getSelectedItem().setUserData(file.getAbsolutePath());
                    return true;
                }
                catch (IOException ignored) {
                }
            }
        }
        }
        catch (Exception ignored){
        }
        return false;
    }

    boolean saveToFile() {
        if (tabPane.getSelectionModel().getSelectedItem().getUserData() == null) {
            return saveToFileAs();
        } else {

            tableView = (TableView) tabPane.getSelectionModel().getSelectedItem().getContent().lookup("TableView");

            List<String> finalNameList = new ArrayList<>();
            for (int i = 0; i < tableView.getItems().size(); i++) {
                finalNameList.add(i, tableView.getItems().get(i).getName());
            }

            List<String> finalNumList = new ArrayList<>();
            for (int i = 0; i < tableView.getItems().size(); i++) {
                finalNumList.add(i, tableView.getItems().get(i).getNum());
            }

            List<String> finalInterestList = new ArrayList<>();
            for (int i = 0; i < tableView.getItems().size(); i++) {
                finalInterestList.add(i, tableView.getItems().get(i).getInterest());
            }

            String fileName = (String) tabPane.getSelectionModel().getSelectedItem().getUserData();
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (!tableView.getItems().get(0).getNum().isEmpty()) {

                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet("1");

                //Data to write to Excel file.
                int rowCount = 0;

                for (int i = 0; i < finalNameList.size(); i++) {
                    XSSFRow row = sheet.createRow(rowCount++);
                    int columnCount = 0;

                    XSSFCell cell = row.createCell(columnCount++);
                    cell.setCellValue(finalNameList.get(i));
                    cell = row.createCell(columnCount++);
                    cell.setCellValue(finalNumList.get(i));
                    cell = row.createCell(columnCount);
                    cell.setCellValue(finalInterestList.get(i));
                }

                if (fileExtension.equals("xlsx")) {

                    try (FileOutputStream outputStream = new FileOutputStream(String.valueOf(tabPane.getSelectionModel().getSelectedItem().getUserData()))) {
                        workbook.write(outputStream);
                        return true;
                    } catch (IOException ignored) {
                    }
                }
                if (fileExtension.equals("txt")) {
                    try {
                        File fileTxt = new File(String.valueOf(tabPane.getSelectionModel().getSelectedItem().getUserData()));

                        // Создание объекта FileWriter
                        FileWriter writer = new FileWriter(fileTxt);

                        // Запись содержимого в файл
                        for (int i = 0; i < finalNameList.size(); i++) {
                            writer.write(finalNameList.get(i) + ", " + finalNumList.get(i) + "\n");
                        }
                        writer.flush();
                        writer.close();
                        return true;
                    } catch (IOException ignored) {
                    }
                }
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText("Збережено");
            alert.showAndWait();
        }
        return false;
    }

    /**
     * set tab name to create or rename
     */
    public void setTabName(String tabName) {
        this.tabName = tabName;
    }
    /**
     * returns a list of names from an open file
     */
    public List<String> getColumnOpenName() {
        return columnOpenName;
    }
    /**
     * returns a list of numbers from an open file
     */
    public List<String> getColumnOpenNum() {
        return columnOpenNum;
    }

    /**
     * create about window
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
    /**
     * create creators window
     */
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

    /**
     * create settings window
     */
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

