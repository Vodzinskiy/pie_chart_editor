package ipz.coursework.pie_chart_editor;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.skin.ColorPickerSkin;

/**
 * Class for table view
 */
public class DataForPieChart {
    private SimpleStringProperty interest;
    private SimpleStringProperty name;
    private SimpleStringProperty num;
//    private ColorPicker picker;

    public DataForPieChart(String num, String name, String interest) {
        this.interest = new SimpleStringProperty(interest);
        this.name = new SimpleStringProperty(name);
        this.num = new SimpleStringProperty(num);
//        this.picker = new ColorPicker();
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

//    public ColorPicker getPicker(){
//        return picker;
//    }
//
//    public void setPicker(ColorPicker picker) {
//        this.picker = picker;
//    }
}
