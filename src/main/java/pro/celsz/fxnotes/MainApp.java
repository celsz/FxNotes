package pro.celsz.fxnotes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Fx Notes");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        NoteManager.save();
        super.stop();
    }

    public static void main(String[] args) {
        launch();
    }
}