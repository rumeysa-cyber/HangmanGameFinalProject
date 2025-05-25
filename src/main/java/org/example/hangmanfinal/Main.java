package org.example.hangmanfinal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/org/example/hangmanfinal/login-view.fxml"));
        Scene mainScene = new Scene(loader.load(), 400, 400);
        primaryStage.setTitle("Hangman Game");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
