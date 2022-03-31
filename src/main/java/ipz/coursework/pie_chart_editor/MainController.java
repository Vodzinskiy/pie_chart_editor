package ipz.coursework.pie_chart_editor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<DataForPieChart, Integer> color;

    @FXML
    private TableColumn<DataForPieChart, String> interest;

    @FXML
    private TableColumn<DataForPieChart, String> name;

    @FXML
    private TableColumn<DataForPieChart, String> num;

    @FXML
    private TableView<DataForPieChart> tableView;

    @FXML
    private PieChart pieChart;

    @FXML
    private RadioButton radioButtonInterest;

    @FXML
    private RadioButton radioButtonNumber;

    private ObservableList<DataForPieChart> dataForPieChart = FXCollections.observableArrayList();

    //public ArrayList<String> variableName = new ArrayList<String>();
    List<String> columnDataName;
    List<String> columnDataInterest;
    List<String> columnDataNum;


    @FXML
    void addToList(ActionEvent event) {

        dataForPieChart.add(new DataForPieChart("1","Змінна"+(tableView.getItems().size()+1),"100%"));

        refractArrayNum();
        refractArrayName();
        refractArrayInterest();
        addArrayToPieChart();

        updateIntest();
    }

    @FXML
    void removeFromList(ActionEvent event) {
        tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItem());

        refractArrayNum();
        refractArrayName();
        refractArrayInterest();
        updateIntest();
        addArrayToPieChart();
    }

    @FXML
    void clear(ActionEvent event) {
        tableView.getItems().clear();

        refractArrayNum();
        refractArrayName();
        refractArrayInterest();
        addArrayToPieChart();

    }

    @FXML
    void initialize() {

        //TableColumn selectColor = new TableColumn("Колір");

        interest.setCellValueFactory(new PropertyValueFactory<>("interest"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        num.setCellValueFactory(new PropertyValueFactory<>("num"));
        //color.getCellObservableValue(new PropertyValueFactory<>("color"));

        name.setCellFactory(TextFieldTableCell.forTableColumn());
        interest.setCellFactory(TextFieldTableCell.forTableColumn());
        num.setCellFactory(TextFieldTableCell.forTableColumn());
        //color.setCellFactory(TextFieldTableCell.forTableColumn());


        tableView.setItems(dataForPieChart);

    }

    public void onEditName(TableColumn.CellEditEvent<DataForPieChart, String> dataForPieChartStringCellEditEvent) {
        DataForPieChart dataForPieChart = tableView.getSelectionModel().getSelectedItem();
        dataForPieChart.setName(dataForPieChartStringCellEditEvent.getNewValue());

        refractArrayName();
        addArrayToPieChart();
    }

    public void onEditInterest(TableColumn.CellEditEvent<DataForPieChart, String> dataForPieChartStringCellEditEvent) {
        DataForPieChart dataForPieChart = tableView.getSelectionModel().getSelectedItem();
        dataForPieChart.setInterest(dataForPieChartStringCellEditEvent.getNewValue());
        tableView.refresh();
        refractArrayInterest();
        addArrayToPieChart();
    }

    public void onEditNum(TableColumn.CellEditEvent<DataForPieChart, String> dataForPieChartStringCellEditEvent) {
        DataForPieChart dataForPieChart = tableView.getSelectionModel().getSelectedItem();
        dataForPieChart.setNum(dataForPieChartStringCellEditEvent.getNewValue());
        tableView.refresh();

        refractArrayNum();
        refractArrayInterest();
        updateIntest();
        addArrayToPieChart();

    }

    public void refractArrayName(){
        columnDataName = new ArrayList<>();
        for (DataForPieChart item : tableView.getItems()) {
            columnDataName.add(name.getCellObservableValue(item).getValue());
        }
    }

    public void refractArrayInterest(){
        columnDataInterest = new ArrayList<>();
        for (DataForPieChart item : tableView.getItems()) {
            columnDataInterest.add(interest.getCellObservableValue(item).getValue());
        }
    }

    public void refractArrayNum(){

        columnDataNum = new ArrayList<>();
        for (DataForPieChart item : tableView.getItems()) {
            columnDataNum.add(num.getCellObservableValue(item).getValue());
        }
    }

    public void addArrayToPieChart(){
        pieChart.getData().clear();
        for (int i = 0; i<columnDataInterest.size();i++){
            PieChart.Data slice = new PieChart.Data(columnDataName.get(i),Double.parseDouble(columnDataNum.get(i)));
            pieChart.getData().add(slice);
        }
    }

    public void updateIntest(){

        double temp = 0;
        for (int i = 0; i<columnDataNum.size();i++){
            temp += Double.parseDouble(columnDataNum.get(i));
        }
       temp = 100/temp;
        for (int i = 0; i<columnDataNum.size();i++){
            columnDataInterest.set(i,Double.toString((Double.parseDouble(columnDataNum.get(i))*temp)));
        }
        for (int i = 0; i < columnDataInterest.size() ; i++) {
            DataForPieChart dataForPieChart = tableView.getItems().get(i);
            dataForPieChart.setInterest(columnDataInterest.get(i)+" %");
        }
    }


    @FXML
    void RadioButton(ActionEvent event) {
       if(radioButtonInterest.isSelected()){
           System.out.println("interest");
       }
       else if(radioButtonNumber.isSelected()){
           System.out.println("num");
       }
    }

    /*public void updateInterest(){

    }*/
}