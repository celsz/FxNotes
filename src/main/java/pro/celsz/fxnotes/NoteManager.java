package pro.celsz.fxnotes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import org.apache.commons.io.IOUtils;
import org.controlsfx.control.Notifications;


import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class NoteManager {
    private static ArrayList<Note> notes = new ArrayList<>();



    private static final String path = "src/main/resources/pro/celsz/fxnotes/data";

    public static ArrayList<Note> getNotes() {
        return notes;
    }

    public static void addNote(Note note) {
        notes.add(note);
    }

    public static void addNoteToMemory(Note note) {
        StringBuilder finals =  new StringBuilder();
        int counter = 0;
        for (int i = 0; i < note.getContent().length(); i++) {

            if (counter == 40){
                finals.append("\n");
                counter = 0;
            }else{
                finals.append(note.getContent().charAt(i));
                counter++;
            }
        }
        try (FileWriter fw = new FileWriter(path + "/" +note.getTitle())){
            fw.write(finals.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Boolean deleteNoteFromMemory(Note note) throws IOException {
        File file = new File(path + "/" +note.getTitle());
        if (file.exists()) {
            return file.delete();
        }
        return false;
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
        listVBox.getChildren().clear();
        initNotesList();
        textArea.setText("ВЫБЕРИТЕ ЗАМЕТКУ!");
        for (Note note : notes) {
            Button btn = new Button(note.getTitle());
            btn.setPrefWidth(200);
            btn.setPrefHeight(50);

            btn.setOnAction(e -> {
                 textArea.clear();
                 textArea.setText(note.getContent());

            });

            btn.setOnContextMenuRequested(e -> {
                if (!btn.getText().equals("welcome")) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Удалить заметку??");
                    alert.setHeaderText("Уверен?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if  (result.isPresent() && result.get() == ButtonType.OK) {
                        try {
                            deleteNoteFromMemory(note);
                            initNotesListVisual(listVBox, textArea);
                        } catch (IOException ex) {
                            Alert alert1 = new Alert(Alert.AlertType.ERROR);
                            alert1.setTitle("Ошибка");

                        }
                    }
                }else {
                    Notifications notifications = Notifications.create().title("Ошибка!").text("Вы не можете удалить начальную заметку!");
                    notifications.show();
                }
            });

            ChangeListener<String> noteChangeListener = new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (note.getContent().equals(oldValue) & !Objects.equals(newValue, "")) {
                        Objects.requireNonNull(note).setContent(newValue);
                    }
                }
            };

            textArea.textProperty().removeListener(noteChangeListener);
            textArea.textProperty().addListener(noteChangeListener);
            listVBox.getChildren().add(btn);
        }
    }

    private static void initNotesList() throws FileNotFoundException {
        notes.clear();
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
