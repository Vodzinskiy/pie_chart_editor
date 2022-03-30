package ipz.coursework.pie_chart_editor;

import javafx.beans.property.SimpleStringProperty;

public class DataForPieChart {
    private SimpleStringProperty interest;
    private SimpleStringProperty name;

    public DataForPieChart(String interest, String name) {
        this.interest = new SimpleStringProperty(interest);
        this.name = new SimpleStringProperty(name);
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
}
