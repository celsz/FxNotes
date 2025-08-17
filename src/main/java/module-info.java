module pro.celsz.fxnotes {
    requires javafx.controls;
    requires javafx.fxml;


    opens pro.celsz.fxnotes to javafx.fxml;
    exports pro.celsz.fxnotes;
}