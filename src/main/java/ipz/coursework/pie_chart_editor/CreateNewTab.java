package ipz.coursework.pie_chart_editor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CreateNewTab {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField newTabName;

    @FXML
    void createNewTabButton(ActionEvent event) throws IOException {
       /* if (newTabName.getText().isEmpty()){
            newTabName.setText("Нова вкладка");
        }
        else{
            MainController mainController = new MainController();
            mainController.CreateNewTab();
        }*/
    }

    @FXML
    void initialize() {

    }

    String getNewTabName(){
        return newTabName.getText();
    }



}