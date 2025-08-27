package pro.celsz.fxnotes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import org.apache.commons.io.IOUtils;
import org.controlsfx.control.Notifications;


import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class NoteManager {
    private static ArrayList<Note> notes = new ArrayList<>();

    private static String currentNote = "welcome";

    private static ChangeListener<String> noteChangeListener = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (Objects.requireNonNull(getNote(currentNote)).getContent().equals(oldValue) & !Objects.equals(newValue, "")) {
                Objects.requireNonNull(getNote(currentNote)).setContent(newValue);
            }
        }
    };

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
        textArea.setText(getNote(currentNote).getContent());
        for (Note note : notes) {
            Button btn = new Button(note.getTitle());
            btn.setPrefWidth(200);
            btn.setPrefHeight(50);
            btn.setOnAction(e -> {
                 textArea.clear();
                 textArea.setText(note.getContent());
                 currentNote = note.getTitle();
            });
            textArea.textProperty().removeListener(noteChangeListener);
            textArea.textProperty().addListener(noteChangeListener);
            listVBox.getChildren().add(btn);
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

    public static void save() throws IOException {
        for (Note note : notes) {
            try (FileWriter fw = new FileWriter(path + "/" + note.getTitle())) {
                fw.write(note.getContent());
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        }
    }




}
