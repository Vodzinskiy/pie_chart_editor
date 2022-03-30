package ipz.coursework.pie_chart_editor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
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
    private TableView<DataForPieChart> tableView;

    @FXML
    private PieChart pieChart;

    private ObservableList<DataForPieChart> dataForPieChart = FXCollections.observableArrayList();

    //public ArrayList<String> variableName = new ArrayList<String>();
    List<String> columnDataName;
    List<String> columnDataInterest;

    @FXML
    void addToList(ActionEvent event) {
        int count = tableView.getItems().size() + 1;
        dataForPieChart.add(new DataForPieChart("0","Змінна"+count));

        refractArrayName();
        refractArrayInterest();
        addArrayToPieChart();

    }

    @FXML
    void removeFromList(ActionEvent event) {
        tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItem());
        refractArrayName();
        refractArrayInterest();
        addArrayToPieChart();
    }

    @FXML
    void clear(ActionEvent event) {
        tableView.getItems().clear();

        refractArrayName();
        refractArrayInterest();
        addArrayToPieChart();


    }

    @FXML
    void initialize() {

        interest.setCellValueFactory(new PropertyValueFactory<>("interest"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        name.setCellFactory(TextFieldTableCell.forTableColumn());
        interest.setCellFactory(TextFieldTableCell.forTableColumn());

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

        refractArrayInterest();
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
    public void addArrayToPieChart(){
        pieChart.getData().clear();
        for (int i = 0; i<columnDataInterest.size();i++){
            PieChart.Data slice = new PieChart.Data(columnDataName.get(i),Double.parseDouble(columnDataInterest.get(i)));
            pieChart.getData().add(slice);
        }
    }
}