package org.example.hangmanfinal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

public class GameController {

    @FXML private Label nameLabel;
    @FXML private Label wordBox;
    @FXML private TextField letterInput;
    @FXML private Label triesLabel;
    @FXML private Label resultLabel;
    @FXML private Button tryAgainBtn;
    @FXML private Canvas drawArea;
    @FXML private Button menuBtn;

    private GraphicsContext gfx;
    private String word;
    private char[] shown;
    private int wrongCount = 0;
    private int maxTries;
    private List<String> allWords = new ArrayList<>();
    private String currentPlayer;

    public void setPlayerName(String player) {
        nameLabel.setText("Welcome, " + player + "!");
        currentPlayer = player;
    }

    public void setGameLevel(String level) {
        switch (level) {
            case "Easy": maxTries = 10; break;
            case "Medium": maxTries = 7; break;
            case "Hard": maxTries = 5; break;
            default: maxTries = 6; break;
        }
    }

    @FXML
    public void initialize() {
        gfx = drawArea.getGraphicsContext2D();
        readWords();
        letterInput.setOnAction(e -> checkLetter());
    }

    @FXML
    public void beginGame() {
        wrongCount = 0;
        word = allWords.get(new Random().nextInt(allWords.size()));
        shown = new char[word.length()];
        Arrays.fill(shown, '_');
        updateWord();
        triesLabel.setText("Mistakes: 0");
        resultLabel.setText("");
        drawHangman(wrongCount);
    }

    private void updateWord() {
        wordBox.setText(String.valueOf(shown).replace("", " ").trim());
    }

    @FXML
    public void checkLetter() {
        String input = letterInput.getText().trim().toLowerCase();
        letterInput.clear();

        if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
            resultLabel.setText("Type one letter.");
            return;
        }

        char ch = input.charAt(0);
        boolean found = false;

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == ch) {
                shown[i] = ch;
                found = true;
            }
        }

        if (found) {
            updateWord();
            if (!new String(shown).contains("_")) {
                resultLabel.setText("🎉 You won!");
                saveGameResult(currentPlayer, true);
            }
        } else {
            wrongCount++;
            triesLabel.setText("Mistakes: " + wrongCount);
            drawHangman(wrongCount);

            if (wrongCount >= maxTries) {
                resultLabel.setText("💀 Game over! Word: " + word);
                wordBox.setText(word);
                saveGameResult(currentPlayer, false);
            }
        }
    }

    private void drawHangman(int count) {
        gfx.clearRect(0, 0, drawArea.getWidth(), drawArea.getHeight());

        gfx.setStroke(Color.BLACK);
        gfx.setLineWidth(2);

        gfx.strokeLine(50, 200, 150, 200);
        gfx.strokeLine(100, 200, 100, 50);
        gfx.strokeLine(100, 50, 160, 50);
        gfx.strokeLine(160, 50, 160, 70);

        Runnable[] parts = new Runnable[]{
                () -> gfx.strokeOval(140, 70, 40, 40),
                () -> gfx.strokeLine(160, 110, 160, 170),
                () -> gfx.strokeLine(160, 120, 130, 150),
                () -> gfx.strokeLine(160, 120, 190, 150),
                () -> gfx.strokeLine(160, 170, 130, 210),
                () -> gfx.strokeLine(160, 170, 190, 210),
                () -> gfx.strokeOval(150, 80, 3, 3),
                () -> gfx.strokeOval(167, 80, 3, 3),
                () -> gfx.strokeArc(150, 90, 20, 10, 0, -180, ArcType.OPEN),
                () -> gfx.strokeText("X", 160, 60)
        };

        int total = parts.length;
        int step = Math.min(count * total / maxTries, total);

        for (int i = 0; i < step; i++) {
            parts[i].run();
        }
    }

    @FXML
    public void goToMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/hangmanfinal/login-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) menuBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readWords() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/org/example/hangmanfinal/words.txt")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    allWords.add(line.trim().toLowerCase());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveGameResult(String username, boolean won) {
        String path = "results.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            String result = username + " - " + (won ? "WON" : "LOST");
            writer.write(result);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
