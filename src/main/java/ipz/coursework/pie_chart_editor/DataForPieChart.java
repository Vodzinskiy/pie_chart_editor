package ipz.coursework.pie_chart_editor;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;


/**
 * Class for table view
 */
public class DataForPieChart {
    private SimpleStringProperty interest;
    private SimpleStringProperty name;
    private SimpleStringProperty num;
    private Node node;
    private final ColorPicker colorPicker;

    public DataForPieChart(String num, String name, String interest) {
        this.interest = new SimpleStringProperty(interest);
        this.name = new SimpleStringProperty(name);
        this.num = new SimpleStringProperty(num);
        this.colorPicker = new ColorPicker();
        colorPicker.getStyleClass().add("split-button");
        
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
}
