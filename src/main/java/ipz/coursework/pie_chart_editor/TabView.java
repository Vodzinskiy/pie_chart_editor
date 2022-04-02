package ipz.coursework.pie_chart_editor;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class TabView {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PieChart pieChart;

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

    /**
     * create a list for the table
     *
     * */
    private ObservableList<DataForPieChart> dataForPieChart = FXCollections.observableArrayList();


    List<String> columnDataName;
    List<String> columnDataInterest;
    List<String> columnDataNum;

    /**
     * adding a new row to the table
     * @param event
     */
    @FXML
    void addToList(ActionEvent event) {
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
        tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItem());

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

        updateArrayName();
        addArrayToPieChart();
    }

    public void onEditInterest(TableColumn.CellEditEvent<DataForPieChart, String> dataForPieChartStringCellEditEvent) {
        DataForPieChart dataForPieChart = tableView.getSelectionModel().getSelectedItem();
        dataForPieChart.setInterest(dataForPieChartStringCellEditEvent.getNewValue());
        tableView.refresh();
        updateArrayInterest();
        addArrayToPieChart();
    }

    public void onEditNum(TableColumn.CellEditEvent<DataForPieChart, String> dataForPieChartStringCellEditEvent) {
        DataForPieChart dataForPieChart = tableView.getSelectionModel().getSelectedItem();
        dataForPieChart.setNum(dataForPieChartStringCellEditEvent.getNewValue());
        tableView.refresh();

        updateArrayNum();
        updateArrayInterest();
        updateIntest();
        addArrayToPieChart();

    }

    public void updateArrayName(){
        columnDataName = new ArrayList<>();
        for (DataForPieChart item : tableView.getItems()) {
            columnDataName.add(name.getCellObservableValue(item).getValue());
        }
    }

    public void updateArrayInterest(){
        columnDataInterest = new ArrayList<>();
        for (DataForPieChart item : tableView.getItems()) {
            columnDataInterest.add(interest.getCellObservableValue(item).getValue());
        }
    }

    public void updateArrayNum(){

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
}
