package ipz.coursework.pie_chart_editor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import javafx.scene.shape.Rectangle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;

import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Reflection;
import javafx.scene.paint.Color;


/**
 * Class - Controller for create a tab
 *
 */
public class TabViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PieChart pieChart;

    @FXML
    private TableColumn<DataForPieChart, ColorPicker> color;

    @FXML
    private TableColumn<DataForPieChart, String> interest;

    @FXML
    private TableColumn<DataForPieChart, String> name;

    @FXML
    private TableColumn<DataForPieChart, String> num;

    @FXML
    private TableView<DataForPieChart> tableView;


    /**
     * create a list for the table
     *
     * */

    private ObservableList<DataForPieChart> dataForPieChart = FXCollections.observableArrayList();

    MainController mainController = new MainController();

    Properties props = new Properties();

    List<String> columnDataName;
    List<String> columnDataInterest;
    List<String> columnDataNum;

    String[] defaultColorsOfPieChart = {"#f3622d", "#fba71b", "#57b757", "#41a9c9", "#4258c9", "#9a42c8", "#c84164", "#888888"};


    /**
     * adding a new row to the table
     * @param event
     */

    @FXML
    void addToList(ActionEvent event) {

        try{
            /*
        (tableView.getItems().size()+1) -> creates new variable names: 1,2,3,4....
         */
            dataForPieChart.add(new DataForPieChart("1","Змінна"+(tableView.getItems().size()+1),"100%"));

        /*
        update all column
         */
            updateArrayNum();
            updateArrayName();
            updateArrayInterest();

        /*
        add interest to column,
         */
            updateIntest();
//            System.out.println(getIndexForDefaultColors());
            setDefaultColorsOfPieChart();
            addFuncToColPicker();

            /*
        add data to a pie chart
         */
            addArrayToPieChart();
            changeLegendColor();

        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Помилка!");
            alert.setHeaderText("Неправильно введенні данні!");
            alert.setContentText("в стовпчику можуть бути лише числа");
            alert.showAndWait();
        }
    }


    /**
     *remove rows from the table
     * @param event
     */
    @FXML
    void removeFromList(ActionEvent event) {
        /*
        remove selection row from table
         */
        if (!tableView.getSelectionModel().isEmpty()){
            DataForPieChart item = tableView.getSelectionModel().getSelectedItem();
            tableView.getItems().remove(item);

            updateArrayNum();
            updateArrayName();
            updateArrayInterest();
            updateIntest();
            addArrayToPieChart();
            changeLegendColor();
        }
    }

    /**
     * cleaning the table
     * @param event
     */
    @FXML
    void clear(ActionEvent event) {
        tableView.getItems().clear();

        updateArrayNum();
        updateArrayName();
        updateArrayInterest();
        updateIntest();
        addArrayToPieChart();
    }
    public void setDefaultColorsOfPieChart(){
        int tabViewSize = tableView.getItems().size();
        tableView.getItems().get(tabViewSize-1).getColorPicker().setValue(Color.web(defaultColorsOfPieChart[(tabViewSize-1)%8]));
    }
    @FXML
    void initialize() throws IOException {

        interest.setCellValueFactory(new PropertyValueFactory<>("interest"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        num.setCellValueFactory(new PropertyValueFactory<>("num"));
//        color.setCellValueFactory(new PropertyValueFactory<DataForPieChart, ColorPicker>("color"));
        color.setCellValueFactory(new PropertyValueFactory<>("colorPicker"));

        name.setCellFactory(TextFieldTableCell.forTableColumn());
        interest.setCellFactory(TextFieldTableCell.forTableColumn());
        num.setCellFactory(TextFieldTableCell.forTableColumn());

        tableView.setItems(dataForPieChart);
        pieChart.setAnimated(true);

        props.loadFromXML(new FileInputStream("settings.xml"));

        pieChart.setLabelsVisible(Boolean.parseBoolean(props.getProperty("labels")));
        pieChart.setLegendVisible(Boolean.parseBoolean(props.getProperty("legend")));
    }

    public void createTableOpenFile(){
        try {
            for(int i = 0; i < mainController.getColumnOpenName().size();i++){
                dataForPieChart.add(new DataForPieChart(mainController.getColumnOpenNum().get(i),mainController.getColumnOpenName().get(i),""));
            }
            updateArrayNum();
            updateArrayName();
            updateArrayInterest();
            updateIntest();
            addArrayToPieChart();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Помилка!");
            alert.setHeaderText("Неправильно введенні данні!");
            alert.setContentText("в стовпчику можуть бути лише числа");
            alert.showAndWait();
        }
    }


    /**
     * saves the specified name
     * @param dataForPieChartStringCellEditEvent
     */
    public void onEditName(TableColumn.CellEditEvent<DataForPieChart, String> dataForPieChartStringCellEditEvent) {
        DataForPieChart dataForPieChart = tableView.getSelectionModel().getSelectedItem();
        dataForPieChart.setName(dataForPieChartStringCellEditEvent.getNewValue());
        updateArrayName();
        addArrayToPieChart();
        changeLegendColor();
    }

    /**
     * saves the specified interest
     * @param dataForPieChartStringCellEditEvent
     */
    public void onEditInterest(TableColumn.CellEditEvent<DataForPieChart, String> dataForPieChartStringCellEditEvent) {
        DataForPieChart dataForPieChart = tableView.getSelectionModel().getSelectedItem();
        dataForPieChart.setInterest(dataForPieChartStringCellEditEvent.getNewValue());
        tableView.refresh();
        updateArrayInterest();
        addArrayToPieChart();
        changeLegendColor();
    }

    /**
     * saves the specified number
     * @param dataForPieChartStringCellEditEvent
     */
    public void onEditNum(TableColumn.CellEditEvent<DataForPieChart, String> dataForPieChartStringCellEditEvent) {

        try{
            DataForPieChart dataForPieChart = tableView.getSelectionModel().getSelectedItem();
            dataForPieChart.setNum(dataForPieChartStringCellEditEvent.getNewValue());
            //replace all "," into "."
            dataForPieChart.setNum(tableView.getSelectionModel().getSelectedItem().getNum().replaceAll(",","."));
            tableView.refresh();

            updateArrayNum();
            updateArrayInterest();
            updateIntest();
            addArrayToPieChart();
            changeLegendColor();

        }
        catch (Exception e){
            tableView.getSelectionModel().getSelectedItem().setNum("0");
            updateArrayNum();
            updateArrayInterest();
            updateIntest();
            addArrayToPieChart();
            //alert window
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Помилка!");
            alert.setHeaderText("Неправильно введенні данні!");
            alert.setContentText("в стовпчику можуть бути лише числа");
            alert.showAndWait();
        }
    }

    /**
     * update column name
     *
     */
    public void updateArrayName(){
        columnDataName = new ArrayList<>();
        for (DataForPieChart item : tableView.getItems()) {
            columnDataName.add(name.getCellObservableValue(item).getValue());
        }
    }
    /**
     * update column interest
     *
     */
    public void updateArrayInterest(){
        columnDataInterest = new ArrayList<>();
        for (DataForPieChart item : tableView.getItems()) {
            columnDataInterest.add(interest.getCellObservableValue(item).getValue());
        }
    }

    /**
     * update column num
     *
     */
    public void updateArrayNum(){

        columnDataNum = new ArrayList<>();
        for (DataForPieChart item : tableView.getItems()) {
            columnDataNum.add(num.getCellObservableValue(item).getValue());
        }

    }

    /**
     * add all data to pie chart
     *
     */
    public void addArrayToPieChart(){
        pieChart.getData().clear();
        for (DataForPieChart item : tableView.getItems()) {
            PieChart.Data slice = new PieChart.Data(item.getName(), Double.parseDouble(item.getNum()));
            pieChart.getData().add(slice);
            String col = item.getColorPicker().getValue().toString();
            String str = "-fx-pie-color:" + col.substring(0, 8).replaceAll("0x", "#") + ";";
            slice.getNode().setStyle(str);
            item.setNode(slice.getNode());
        }
    }
    public void changeLegendColor(){
        Set<Node> items = pieChart.lookupAll("Label.chart-legend-item");
        int i = 0;
        // these colors came from caspian.css .default-color0..4.chart-pie
        for (Node item : items) {
            Label label = (Label) item;
            final Rectangle rectangle = new Rectangle(10, 10, tableView.getItems().get(i).getColorPicker().getValue());
            final Glow niceEffect = new Glow();
            niceEffect.setInput(new Reflection());
            rectangle.setEffect(niceEffect);
            label.setGraphic(rectangle);
            i++;
        }
    }
    void addFuncToColPicker(){
        for (DataForPieChart item : tableView.getItems()){
            ColorPicker colorPicker = item.getColorPicker();
            colorPicker.setOnAction(event -> {
                String col = colorPicker.getValue().toString();
                String str = "-fx-pie-color:" + col.substring(0, 8).replaceAll("0x", "#") + ";";
                item.getNode().setStyle(str);
                changeLegendColor();
            });
        }
    }



    /**
     * calculates the percentage of the numbers specified
     *
     */
    public void updateIntest(){
        double temp = 0;
        //add all number
        for (String s : columnDataNum) {
            temp += Double.parseDouble(s);
        }
        // "1%"
        temp = 100/temp;
        // calculates all percentage, and add to column
        for (int i = 0; i<columnDataNum.size();i++){
            //calculates
            columnDataInterest.set(i,Double.toString((Double.parseDouble(columnDataNum.get(i))*temp)));

            DecimalFormat df = new DecimalFormat("###.###");

            //add
            DataForPieChart dataForPieChart = tableView.getItems().get(i);
            dataForPieChart.setInterest(df.format(Double.parseDouble(columnDataInterest.get(i)))+" %");
        }
    }
}





