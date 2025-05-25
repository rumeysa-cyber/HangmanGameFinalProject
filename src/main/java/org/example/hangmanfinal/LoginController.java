package org.example.hangmanfinal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.HashMap;

public class LoginController {

    @FXML private TextField userInput;
    @FXML private PasswordField passInput;
    @FXML private Button signUpBtn;
    @FXML private Button playBtn;
    @FXML private ComboBox<String> levelSelect;
    @FXML private Label infoLabel;

    private static final HashMap<String, String> users = new HashMap<>();

    @FXML
    public void initialize() {
        levelSelect.getItems().addAll("Easy", "Medium", "Hard");
    }

    @FXML
    public void onRegisterClick() {
        String user = userInput.getText().trim();
        String pass = passInput.getText().trim();

        if (user.isEmpty() || pass.isEmpty()) {
            infoLabel.setText("Fill in all fields.");
            return;
        }

        if (users.containsKey(user)) {
            infoLabel.setText("User already exists.");
            return;
        }

        users.put(user, pass);
        infoLabel.setText("Registered! Select level and start.");

        levelSelect.setDisable(false);
        playBtn.setDisable(false);
        signUpBtn.setDisable(true);
    }

    @FXML
    public void onPlayClick() throws Exception {
        String user = userInput.getText().trim();
        String pass = passInput.getText().trim();
        String level = levelSelect.getValue();

        if (!users.containsKey(user) || !users.get(user).equals(pass)) {
            infoLabel.setText("Wrong login info.");
            return;
        }

        if (level == null) {
            infoLabel.setText("Pick a level.");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/hangmanfinal/game-view.fxml"));
        Parent root = loader.load();

        GameController gc = loader.getController();
        gc.setPlayerName(user);
        gc.setGameLevel(level);
        gc.beginGame();

        Stage stage = (Stage) userInput.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Hangman");
        stage.show();
    }
}
