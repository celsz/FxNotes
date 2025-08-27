module pro.celsz.fxnotes {
    requires javafx.controls;
    requires javafx.fxml;
    requires commons.io;
    requires org.controlsfx.controls;


    opens pro.celsz.fxnotes to javafx.fxml;
    exports pro.celsz.fxnotes;
}