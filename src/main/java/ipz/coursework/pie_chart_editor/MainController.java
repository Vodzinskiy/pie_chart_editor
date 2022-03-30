package ipz.coursework.pie_chart_editor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;

import java.net.URL;
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

    private ObservableList<DataForPieChart> dataForPieChart = FXCollections.observableArrayList(
            new DataForPieChart("0","Змінна1")
    );

    @FXML
    void addToList(ActionEvent event) {
        int count = tableView.getItems().size() + 1;
        dataForPieChart.add(new DataForPieChart("0","Змінна"+count));
    }

    @FXML
    void removeFromList(ActionEvent event) {
        tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItem());
    }

    @FXML
    void clear(ActionEvent event) {
        tableView.getItems().clear();
        dataForPieChart.add(new DataForPieChart("0","Змінна1"));
    }

    @FXML
    void initialize() {

        interest.setCellValueFactory(new PropertyValueFactory<>("interest"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        name.setCellFactory(TextFieldTableCell.forTableColumn());
        interest.setCellFactory(TextFieldTableCell.forTableColumn());

        tableView.setItems(dataForPieChart);
    }

}