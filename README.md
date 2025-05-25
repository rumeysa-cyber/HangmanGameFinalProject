# HangmanGameFinalProject 

A simple JavaFX-based Hangman game with a login screen, difficulty selection, and result tracking.

---

## Features

-  **Login/Register System**  
-  **Level Selection** – Easy / Medium / Hard  
-  **Interactive UI** – Built with FXML + Scene Builder  
-  **Results Saved** – All game results recorded in `results.txt`  
-  **Hangman Drawing** – Game over visuals on canvas  
-  **Word List** – Words loaded from external file (`words.txt`)

---

## Technologies Used

- Java 17+ 
- JavaFX 24  
- FXML (Scene Builder support)  
- IntelliJ IDEA

---

##  Project Structure
HangmanGameFinalProject
├── src/
│ └── main/
│ ├── java/
│ │ └── org.example.hangmanfinal
│ │ ├── Main.java
│ │ ├── LoginController.java
│ │ └── GameController.java
│ └── resources/
│ └── org.example.hangmanfinal
│ ├── login-view.fxml
│ ├── game-view.fxml
│ └── words.txt
├── results.txt
├── README.md
└── .gitignore
## 📂 How to Run

 Make sure Java 17+ and JavaFX 24 SDK are installed
 Clone this repo:
   
   git clone https://github.com/rumeysa-cyber/HangmanGameFinalProject.git
   Open with IntelliJ IDEA

Set VM options:
--module-path /path/to/javafx-sdk-24.0.1/lib --add-modules javafx.controls,javafx.fxml
Run Main.java

## Student Information
- Name: Rumeysa Satılmış
- Student Number: 23040102017
