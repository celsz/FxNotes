module pro.celsz.fxnotes {
    requires javafx.controls;
    requires javafx.fxml;
    requires commons.io;


    opens pro.celsz.fxnotes to javafx.fxml;
    exports pro.celsz.fxnotes;
}