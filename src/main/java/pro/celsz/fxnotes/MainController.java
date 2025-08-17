package pro.celsz.fxnotes;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainController implements Initializable {
    @FXML
    private AnchorPane mainPane;

    @FXML
    private ScrollPane contentScrollPane;

    @FXML
    private VBox listVBox;

    @FXML
    private VBox dataVBox;

    private Label text = new Label();

    private Map<String, Map<String, Object>> notes = new HashMap<>();
    private List<String> filenames = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initNotes();
        initNotesList();
        dataVBox.getChildren().clear();
        //dataVBox.getChildren().add(text);
    }

    private void initNotes(){
        File notefolder = new File("src/main/resources/pro/celsz/fxnotes/data");
        List<File> files = Arrays.asList(Objects.requireNonNull(notefolder.listFiles()));
        for (File file : files) {
            filenames.add(file.getName());
            try (FileReader reader = new FileReader("src/main/resources/pro/celsz/fxnotes/data" + "/" + file.getName())) {
                int character;
                StringBuilder content = new StringBuilder();
                while ((character = reader.read()) != -1) {
                    content.append((char) character);
                }
                List<String> words = Arrays.asList(content.toString().split(" "));
                StringBuilder note = new StringBuilder();
                for (int i = 0; i < words.size(); i++) {
                    if (!words.get(i).equals("<end>")) {
                        note.append(words.get(i));
                        note.append(" ");
                    }else{
                        i++;
                    }
                }

                Map<String, Object> data = new HashMap<>();
                data.put("content", note.toString());
                notes.put(file.getName(), data);
                System.out.println(notes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initNotesList(){
        listVBox.getChildren().clear();
        Arrays.sort(filenames.toArray());
        for (String filename : filenames) {
            Button item = new Button();
            item.setPrefWidth(220.0);
            item.setPrefHeight(50.0);
            item.setFont(new Font(20.0));
            item.setText(filename);
            item.setOnAction(event -> {
                text.setText(notes.get(filename).get("content").toString());
                dataVBox.getChildren().clear();
                dataVBox.getChildren().add(new Label(notes.get(filename).get("content").toString()));
            });
            listVBox.getChildren().add(item);
        }

    }
}