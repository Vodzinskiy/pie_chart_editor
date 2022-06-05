package ipz.coursework.pie_chart_editor;

import javafx.event.Event;
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
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;

import java.io.*;
import java.io.IOException;

import java.util.*;

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
 * */
public class MainController {
    /**
     * main stage
     */
    private final Stage thisStage;

    @FXML
    private Menu fileMenu;

    @FXML
    private Menu helpMenu;

    @FXML
    private  Menu saveFileAsMenu;

    @FXML
    private MenuItem aboutProject;

    @FXML
    private MenuItem creators;

    @FXML
    private MenuItem settings;

    @FXML
    private MenuItem newTab;

    @FXML
    private MenuItem openFile;

    @FXML
    private MenuItem saveFile;

    @FXML
    private  MenuItem saveFileAs;

    @FXML
    private  MenuItem savePieChartPicture;

    @FXML
    private MenuItem exit;
    /**
     * tab bar
     */
    @FXML
    private TabPane tabPane;
    /**
     * table view for get data from TabViewController
     */
    @FXML
    private TableView<DataForPieChart> tableView;
    /**
     *  Label for help to create tab
     */
    @FXML
    private Label helpForCreateNewTab;
    /**
     * pie chart for get data from TabViewContr0ller
     */
    @FXML
    private PieChart pieChart;

    Properties prop = new Properties();

    String nameOfXMLPropsFile;
    List<String> rows = new ArrayList<>();
    public String tabName;
    static List<String> columnOpenName = new ArrayList<>();
    static List<String> columnOpenNum = new ArrayList<>();
    String filePath;
    List<String> namesOfTabs = new ArrayList<>();
    Map<String, List<String>> mapTabNames = new HashMap<>();
    SaveViewController saveViewController = new SaveViewController(this);
    SettingsController settingsController = new SettingsController(this);
    CreateNewTab createNewTab = new CreateNewTab(this);
    PersonView personView = new PersonView(this);
    AboutView aboutView = new AboutView(this);

    /**
     * method which launches main window
     */
    public MainController() {
        thisStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.setOnCloseRequest(this::closeWindow);
            thisStage.setTitle("Pie chart editor");
            Image icon = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("icon.png")));
            thisStage.getIcons().add(icon);
            thisStage.setResizable(false);
            Properties props = new Properties();
            props.loadFromXML(Objects.requireNonNull(this.getClass().getResourceAsStream("settings.xml")));
            if (props.getProperty("theme").equals("Light")){
                thisStage.getScene().getRoot().getStylesheets().remove(Objects.requireNonNull(getClass().getResource("style.css")).toString());
            }
            if (props.getProperty("theme").equals("Dark")){
                thisStage.getScene().getRoot().getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toString());
            }
            if (props.getProperty("language").equals("English")){
                languageMain("English.xml");
                this.nameOfXMLPropsFile = "English.xml";
            }
            if (props.getProperty("language").equals("Ukrainian")){
                languageMain("Ukraine.xml");
                this.nameOfXMLPropsFile = "Ukraine.xml";
            }
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
    void initialize(){
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
        settings.setOnAction(event -> settingsWindow());
        exit.setOnAction(event -> exit());
        settingsController.setTabPane(tabPane);
    }

    /**
     * close application
     */
    void exit(){
        thisStage.close();
    }

    /**
     *translate main window
     */
    void languageMain(String res) throws IOException {

        prop.loadFromXML(Objects.requireNonNull(this.getClass().getResourceAsStream(res)));
        fileMenu.setText(prop.getProperty("file"));
        helpMenu.setText(prop.getProperty("help"));
        newTab.setText(prop.getProperty("newFile"));
        openFile.setText(prop.getProperty("openFromFile"));
        saveFile.setText(prop.getProperty("save"));
        saveFileAs.setText(prop.getProperty("saveAsTable"));
        savePieChartPicture.setText(prop.getProperty("saveAsPic"));
        saveFileAsMenu.setText(prop.getProperty("saveAs"));
        settings.setText(prop.getProperty("settings"));
        exit.setText(prop.getProperty("exit"));
        aboutProject.setText(prop.getProperty("about"));
        creators.setText(prop.getProperty("creators"));
        helpForCreateNewTab.setText(prop.getProperty("helpForCreateNewTab"));
    }

    /**
     *start when window closing
     */
    void closeWindow(Event e){
        e.consume();
        if(tabPane.getTabs().size() == 0){
            exit();
        }
        for (int i = tabPane.getTabs().size();i>0;i--){
            if(listComparison(tabPane.getTabs().get(i - 1))){
                saveViewController.setTab(tabPane.getTabs().get(i-1));
                saveViewController.showStage();
            }
            else{
                tabPane.getTabs().get(i-1).getTabPane().getTabs().remove(tabPane.getTabs().get(i-1));
            }
        }
        if(tabPane.getTabs().size() == 0){
            try{
                settingsController.exit();
                createNewTab.exit();
                aboutView.exit();
                personView.exit();
                saveViewController.exit();
                exit();
            }
            catch (Exception exception){
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
                try {
                    File fileOpen = new File((String) tab.getUserData());
                    int j = 0;
                    Scanner scanner = new Scanner(fileOpen);
                    rows.clear();
                    while (scanner.hasNextLine()) {
                        rows.add(j, scanner.nextLine());
                        j++;
                    }
                    for (int i = 0; i< rows.size();i++){
                        String[] temp = rows.get(i).replaceAll("\\s", "").split(",");
                        nameLastSave.add(i,temp[0]);
                        numLastSave.add(i,temp[1]);
                    }
                } catch (FileNotFoundException ignored) {
                }
            }
        }
        if (nameChange.equals(nameLastSave)){
            return !numChange.equals(numLastSave);
        }
        return true;
    }

    /*
    *
    * Open,create tab
    *
     */
    public void editMapTabs (){
        List<String> tabsValueList= new ArrayList<>();
        for (String key : mapTabNames.keySet()){
            for (int i = 0; i < mapTabNames.get(key).size(); i++){
                if (i == 0) continue;
                if (!mapTabNames.get(key).get(i).equals(key + "(" + (i+1) + ")")){
                    mapTabNames.get(key).set(mapTabNames.get(key).indexOf(mapTabNames.get(key).get(i)), key + "(" + (i+1) + ")");
                }
            }
            tabsValueList.addAll(mapTabNames.get(key));
        }
        for (int i = 0; i < tabPane.getTabs().size(); i++){
            if (!tabPane.getTabs().get(i).getText().equals(tabsValueList.get(i))){
                tabPane.getTabs().get(i).setText(tabsValueList.get(i));
            }
        }

    }

    /**
     * creates a window to specify the name of the new tab
     */
    public void CreateNewTabName() {
        columnOpenName.clear();
        columnOpenNum.clear();
        filePath = null;
        tabName = null;
        createNewTab.setNewTabName(prop.getProperty("newFile"));
        createNewTab.showStage();
        CreateNewTab(tabName);
        helpForCreateNewTab.setVisible(false);
    }

    public int countSameElementsInArray(String name){
        int count = 0;
        for (String s : namesOfTabs){
            if (name.equals(s)){
                count += 1;
            }
        }
        return count;
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
                nTab.setOnCloseRequest(arg0 -> closeTab(arg0, nTab));

                ContextMenu contextMenu = new ContextMenu();
                Properties props = new Properties();
                props.loadFromXML(Objects.requireNonNull(this.getClass().getResourceAsStream(this.nameOfXMLPropsFile)));
                MenuItem item = new MenuItem(props.getProperty("rename"));
                item.setOnAction(event -> ChangeTabName(nTab));
                contextMenu.getItems().addAll(item);
                nTab.setContextMenu(contextMenu);
            } catch (IOException e) {
                e.printStackTrace();
            }
            tabPane.getTabs().add(nTab);
            namesOfTabs.add(tabPane.getTabs().get(tabPane.getTabs().size() - 1).getText());
            String nameOfTab =tabPane.getTabs().get(tabPane.getTabs().size() - 1).getText();
            List<String> names = new ArrayList<>();
            if (countSameElementsInArray(nameOfTab) > 1){
                String numOfTabStr = Integer.toString(countSameElementsInArray(nameOfTab));
                String formattedName = nameOfTab + "(" + numOfTabStr + ")";
                tabPane.getTabs().get(tabPane.getTabs().size() - 1).setText(formattedName);
                mapTabNames.get(nameOfTab).add(formattedName);
            }
            else {
                names.add(nameOfTab);
                mapTabNames.put(nameOfTab, names);
            }
            editMapTabs();
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
            helpForCreateNewTab.setVisible(false);
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
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
            fileChooser.getExtensionFilters().add(extFilter);

            if (!tableView.getItems().get(0).getName().isEmpty()){
                File file = fileChooser.showSaveDialog(new Stage());
                if(file != null){

                    Properties properties = new Properties();
                    properties.loadFromXML(Objects.requireNonNull(this.getClass().getResourceAsStream("settings.xml")));
                    if(properties.getProperty("bg").equals("true")){
                        WritableImage image = pieChart.snapshot(new SnapshotParameters(), null);
                        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                    }
                    if(properties.getProperty("bg").equals("false")){
                        SnapshotParameters parameters = new SnapshotParameters();
                        parameters.setFill(Color.TRANSPARENT);
                        WritableImage image = pieChart.snapshot(parameters , null);
                        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                    }

                }
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

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
        FileChooser.ExtensionFilter extTxtFilter = new FileChooser.ExtensionFilter("Txt files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.getExtensionFilters().add(extTxtFilter);

        if(!tableView.getItems().get(0).getNum().isEmpty()){
            File file = fileChooser.showSaveDialog(new Stage());
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("1");
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
                    FileWriter writer = new FileWriter(fileTxt);
                    for (int i = 0; i<finalNameList.size();i++){
                        writer.write(finalNameList.get(i) + ", " + finalNumList.get(i) + ", " + finalInterestList.get(i) +"\n");
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
                        FileWriter writer = new FileWriter(fileTxt);
                        for (int i = 0; i < finalNameList.size(); i++) {
                            writer.write(finalNameList.get(i) + ", " + finalNumList.get(i) + ", " + finalInterestList.get(i) +"\n");
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
        try{
            aboutView.showStage();
        }
        catch (Exception ignored){}
    }
    /**
     * create creators window
     */
    void People(){
        try{
            personView.showStage();
        }
        catch (Exception ignored){}
    }

    /**
     * open settings window
     */
    void settingsWindow(){
        try{
            settingsController.showStage();
        }
        catch (Exception ignored){}
    }
    /**
     * open window to rename the tab
     */
    void ChangeTabName(Tab ntab){
        try{
            tabName = null;
            createNewTab.setNewTabName(ntab.getText());
            createNewTab.showStage();
            if (!tabName.isEmpty()){
                ntab.setText(tabName);
            }
        }
        catch (Exception ignored){}
    }

    /**
     *start when tab closing
     */
    void closeTab(Event arg0, Tab tab){
        try{
            if(listComparison(tab)){
                saveViewController.setTab(tab);
                saveViewController.showStage();
                arg0.consume();
            }
        }
        catch (Exception ignored){}
    }

    void helpTextVisible(){
        if(tabPane.getTabs().size() == 0){
            helpForCreateNewTab.setVisible(true);
        }
    }
    public SettingsController getSettingsController(){return settingsController;}
    public  CreateNewTab getCreateNewTab(){return createNewTab;}
    public SaveViewController getSaveViewController(){return  saveViewController;}
    public AboutView getAboutView(){return  aboutView;}
    public PersonView getPersonView(){return  personView;}
}

