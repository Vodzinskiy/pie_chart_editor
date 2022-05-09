package ipz.coursework.pie_chart_editor;

import java.awt.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.paint.Color;
import javafx.util.Callback;


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
        add data to a pie chart
         */
            addArrayToPieChart();
        /*
        add interest to column,
         */
            updateIntest();
//            System.out.println(getIndexForDefaultColors());
            setDefaultColorsOfPieChart();
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
        DataForPieChart item = tableView.getSelectionModel().getSelectedItem();
        tableView.getItems().remove(item);

        updateArrayNum();
        updateArrayName();
        updateArrayInterest();
        updateIntest();
        addArrayToPieChart();
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
        tableView.getItems().get(tabViewSize-1).getColorPicker().setValue(Color.web(defaultColorsOfPieChart[tabViewSize%8 - 1]));
    }
    @FXML
    void initialize() {

        interest.setCellValueFactory(new PropertyValueFactory<>("interest"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        num.setCellValueFactory(new PropertyValueFactory<>("num"));
//        color.setCellValueFactory(new PropertyValueFactory<DataForPieChart, ColorPicker>("color"));
        color.setCellValueFactory(new PropertyValueFactory<>("colorPicker"));

        name.setCellFactory(TextFieldTableCell.forTableColumn());
        interest.setCellFactory(TextFieldTableCell.forTableColumn());
        num.setCellFactory(TextFieldTableCell.forTableColumn());

        tableView.setItems(dataForPieChart);
        pieChart.setLegendVisible(false);

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
            item.setNode(slice.getNode());

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





