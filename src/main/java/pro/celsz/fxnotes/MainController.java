package pro.celsz.fxnotes;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainController implements Initializable {

    @FXML
    private VBox listVBox;

    @FXML
    private TextArea textNote;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            NoteManager.initNotesListVisual(listVBox, textNote);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }


}