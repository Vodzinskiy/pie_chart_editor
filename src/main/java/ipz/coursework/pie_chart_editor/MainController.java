package ipz.coursework.pie_chart_editor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<?, ?> color;

    @FXML
    private TableColumn<?, ?> interest;

    @FXML
    private TableColumn<?, ?> name;

    @FXML
    private TableView<?> tabelView;



    @FXML
    void addToList(ActionEvent event) {

    }

    @FXML
    void removeFromList(ActionEvent event) {

    }

    @FXML
    void initialize() {


    }

}