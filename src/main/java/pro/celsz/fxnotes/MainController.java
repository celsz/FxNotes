package pro.celsz.fxnotes;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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

    @FXML
    private Button addNote;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            NoteManager.initNotesListVisual(listVBox, textNote);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        addNote.setOnAction(e -> {
            String title;
            String content;
            TextInputDialog dialogTitle = new TextInputDialog();
            dialogTitle.setTitle("Введите название");
            dialogTitle.setHeaderText("Заметки");
            Optional<String> result = dialogTitle.showAndWait();

            TextInputDialog dialogContent = new TextInputDialog("Заметка");
            dialogContent.setTitle("Введите содержание");
            dialogContent.setHeaderText("Заметки");



            if (result.isPresent()) {
                title = result.get();
                Optional<String> contentOptional = dialogContent.showAndWait();
                if (contentOptional.isPresent()) {
                    content = contentOptional.get();
                    Note note = new Note(title, content);
                    NoteManager.addNoteToMemory(note);
                    try {
                        NoteManager.initNotesListVisual(listVBox, textNote);
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

        });


    }


}