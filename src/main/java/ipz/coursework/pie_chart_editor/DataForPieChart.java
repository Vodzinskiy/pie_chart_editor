package ipz.coursework.pie_chart_editor;

import java.util.UUID;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;

/**
 * Class for table view
 */
public class DataForPieChart {
    private final String id;
    private SimpleStringProperty interest;
    private SimpleStringProperty name;
    private SimpleStringProperty num;
    private Node node;
    private ColorPicker colorPicker;

    public DataForPieChart(String num, String name, String interest) {
        this.id = UUID.randomUUID().toString();
        this.interest = new SimpleStringProperty(interest);
        this.name = new SimpleStringProperty(name);
        this.num = new SimpleStringProperty(num);
        this.colorPicker = new ColorPicker();
        colorPicker.getStyleClass().add("split-button");
        colorPicker.setOnAction(event -> {
            String col = colorPicker.getValue().toString();
            String str = "-fx-pie-color:" + col.substring(0, 8).replaceAll("0x", "#") + ";";
            node.setStyle(str);
        });
    }

    public String getId() {
        return id;
    }

    public String getInterest() {
        return interest.get();
    }

    public void setInterest(String interest) {
        this.interest = new SimpleStringProperty(interest);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public String getNum() {
        return num.get();
    }

    public void setNum(String num) {
        this.num = new SimpleStringProperty(num);
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public ColorPicker getColorPicker(){return colorPicker;}

    public void setColorPicker(ColorPicker colorPicker) {
        this.colorPicker = colorPicker;
    }
}
