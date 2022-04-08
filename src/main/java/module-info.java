module ipz.coursework.pie_chart_editor {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;


    opens ipz.coursework.pie_chart_editor to javafx.fxml;
    exports ipz.coursework.pie_chart_editor;
}