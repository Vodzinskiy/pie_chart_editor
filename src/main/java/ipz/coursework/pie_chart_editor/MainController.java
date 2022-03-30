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


    @FXML
    void addToList(ActionEvent event) {
        int count = tableView.getItems().size() + 1;
        dataForPieChart.add(new DataForPieChart("0","Змінна"+count));

        refractArrayName();
        refractArrayInterest();

    }

    @FXML
    void removeFromList(ActionEvent event) {
        tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItem());
        refractArrayName();
        refractArrayInterest();
    }

    @FXML
    void clear(ActionEvent event) {
        tableView.getItems().clear();

        refractArrayName();
        refractArrayInterest();


    }

    @FXML
    void initialize() {

        interest.setCellValueFactory(new PropertyValueFactory<>("interest"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        name.setCellFactory(TextFieldTableCell.forTableColumn());
        interest.setCellFactory(TextFieldTableCell.forTableColumn());

        tableView.setItems(dataForPieChart);





        PieChart.Data slice1 = new PieChart.Data("USA", 20);
        PieChart.Data slice2 = new PieChart.Data("EU", 20);
        PieChart.Data slice3 = new PieChart.Data("China", 20);
        PieChart.Data slice4 = new PieChart.Data("Japan", 20);
        PieChart.Data slice5 = new PieChart.Data("Others", 100);

        pieChart.getData().add(slice1);
        pieChart.getData().add(slice2);
        pieChart.getData().add(slice3);
        pieChart.getData().add(slice4);
        pieChart.getData().add(slice5);
    }

    public void onEditName(TableColumn.CellEditEvent<DataForPieChart, String> dataForPieChartStringCellEditEvent) {
        DataForPieChart dataForPieChart = tableView.getSelectionModel().getSelectedItem();
        dataForPieChart.setName(dataForPieChartStringCellEditEvent.getNewValue());

        refractArrayName();
    }

    public void onEditInterest(TableColumn.CellEditEvent<DataForPieChart, String> dataForPieChartStringCellEditEvent) {
        DataForPieChart dataForPieChart = tableView.getSelectionModel().getSelectedItem();
        dataForPieChart.setInterest(dataForPieChartStringCellEditEvent.getNewValue());

        refractArrayInterest();

    }

    public void refractArrayName(){
        List<String> columnDataName = new ArrayList<>();
        for (DataForPieChart item : tableView.getItems()) {
            columnDataName.add(name.getCellObservableValue(item).getValue());
        }
    }

    public void refractArrayInterest(){
        List<String> columnDataInterest = new ArrayList<>();
        for (DataForPieChart item : tableView.getItems()) {
            columnDataInterest.add(interest.getCellObservableValue(item).getValue());
        }
    }
}