module ipz.coursework.pie_chart_editor {
    requires javafx.controls;
    requires javafx.fxml;


    opens ipz.coursework.pie_chart_editor to javafx.fxml;
    exports ipz.coursework.pie_chart_editor;
}