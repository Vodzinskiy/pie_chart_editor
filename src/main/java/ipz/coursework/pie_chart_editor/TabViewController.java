package ipz.coursework.pie_chart_editor;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

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
    private TableColumn<DataForPieChart, Void> color;

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
    List<String> Colors = new ArrayList<>();

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

        Callback<TableColumn<DataForPieChart, Void>, TableCell<DataForPieChart, Void>> cellFactory = new
                Callback<TableColumn<DataForPieChart, Void>, TableCell<DataForPieChart, Void>>() {
                    @Override
                    public TableCell<DataForPieChart, Void> call(final TableColumn<DataForPieChart, Void> param) {
                        final TableCell<DataForPieChart, Void> cell = new TableCell<DataForPieChart, Void>() {
                            private ColorPicker colpicker = new ColorPicker();
                            {
                                colpicker.setOnAction(event -> {
                                    String col = colpicker.getValue().toString();
                                    String str = "-fx-pie-color:" + col.substring(0, 8).replaceAll("0x", "#") + ";";
                                    Colors.add(str);
                                    System.out.println(Colors);
                                });
                            }

                            @Override
                            public void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    setGraphic(colpicker);
                                }
                            }
                        };
                        return cell;
                    }
                };
        color.setCellFactory(cellFactory);

        /*
        update all column
         */
        updateArrayNum();
        updateArrayName();
        updateArrayInterest();
//        addPickerToTable();
        /*
        add data to a pie chart
         */
        addArrayToPieChart();
        /*
        add interest to column,
         */
//        updateArrayColor();
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
        Colors.clear();
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
        //color.setCellValueFactory(new PropertyValueFactory<DataForPieChart, ColorPicker>("color"));
        tableView.getColumns().add(color);

        name.setCellFactory(TextFieldTableCell.forTableColumn());
        interest.setCellFactory(TextFieldTableCell.forTableColumn());
        num.setCellFactory(TextFieldTableCell.forTableColumn());

        tableView.setItems(dataForPieChart);


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
            alert.setContentText("в стовпчику можуть бути лише ЧИСЛА");
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
     * update array with colors
     *
     */
    public void updateArrayColors(){

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
        for (int i = 0; i<columnDataInterest.size();i++){
            PieChart.Data slice = new PieChart.Data(columnDataName.get(i),Double.parseDouble(columnDataNum.get(i)));

            pieChart.getData().add(slice);

        }
    }

    /**
     * calculates the percentage of the numbers specified
     *
     */
    public void updateIntest(){

        double temp = 0;
        //add all number
        for (int i = 0; i<columnDataNum.size();i++){
            temp += Double.parseDouble(columnDataNum.get(i));
        }
        // "1%"
        temp = 100/temp;
        // calculates all percentage, and add to column
        for (int i = 0; i<columnDataNum.size();i++){
            //calculates
            columnDataInterest.set(i,Double.toString((Double.parseDouble(columnDataNum.get(i))*temp)));
            //add
            DataForPieChart dataForPieChart = tableView.getItems().get(i);
            dataForPieChart.setInterest(columnDataInterest.get(i)+" %");
        }
    }
    public void setColorOfPieChart(){
        for (int i = 0; i < pieChart.getData().size(); i++){
            pieChart.getData().get(i).getNode().setStyle(Colors.get(i));
        }
    }
}





