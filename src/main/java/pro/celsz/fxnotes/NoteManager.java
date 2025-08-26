package pro.celsz.fxnotes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

//org.apache.commons.io.IOUtils
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class NoteManager {
    private static ArrayList<Note> notes = new ArrayList<>();

    private static String currentNote;

    private static final String path = "src/main/resources/pro/celsz/fxnotes/data";

    public static ArrayList<Note> getNotes() {
        return notes;
    }

    public static void addNote(Note note) {
        notes.add(note);
    }

    public static void removeNote(String title) {
        notes.removeIf(note -> note.getTitle().equals(title));
    }

    public static Note getNote(String title) {
        for (Note note : notes) {
            if (note.getTitle().equals(title)) {
                return note;
            }
        }
        return null;
    }


    public static void initNotesListVisual(VBox listVBox, TextArea textArea) throws FileNotFoundException {
        initNotesList();

        for (Note note : notes) {
            Button btn = new Button(note.getTitle());
            btn.setPrefWidth(20);
            btn.setPrefHeight(20);
            btn.setOnAction(e -> {
                 textArea.clear();
                 textArea.setText(note.getContent());
                 currentNote = note.getTitle();
                 textArea.textProperty().addListener(new ChangeListener<String>() {
                     @Override
                     public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                         note.setContent(newValue);
                     }
                 });
            });
        }
    }

    private static void initNotesList() throws FileNotFoundException {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {

            try(FileInputStream inputStream = new FileInputStream("src/main/resources/pro/celsz/fxnotes/data/" + file.getName())) {
                String everything = IOUtils.toString(inputStream);
                Note nn = new Note(file.getName(), everything);
                addNote(nn);


            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }


}
