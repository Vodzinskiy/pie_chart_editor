package ipz.coursework.pie_chart_editor;

import javafx.beans.property.SimpleStringProperty;

public class DataForPieChart {
    private SimpleStringProperty interest;
    private SimpleStringProperty name;
    private SimpleStringProperty num;

    public DataForPieChart(String interest,String num, String name) {
        this.interest = new SimpleStringProperty(interest);
        this.name = new SimpleStringProperty(name);
        this.num = new SimpleStringProperty(num);
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
}
