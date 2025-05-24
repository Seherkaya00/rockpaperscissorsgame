import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class GameController {

    @FXML
    private ImageView userImage;

    @FXML
    private ImageView computerImage;

    @FXML
    private Label resultLabel;

    private final String[] moves = {"rock", "paper", "scissors"};


    private final Image rockImage = new Image(getClass().getResource("/fist.jpeg").toExternalForm());
    private final Image paperImage = new Image(getClass().getResource("/palm.jpeg").toExternalForm());
    private final Image scissorsImage = new Image(getClass().getResource("/scissors.jpeg").toExternalForm());

    private final AudioClip winSound = new AudioClip(getClass().getResource("/win.wav").toExternalForm());
    private final AudioClip loseSound = new AudioClip(getClass().getResource("/lose.wav").toExternalForm());

    public void playRock() {
        playGame("rock");
    }

    public void playPaper() {
        playGame("paper");
    }

    public void playScissors() {
        playGame("scissors");
    }

    private void playGame(String userMove) {
        userImage.setImage(getImage(userMove));

        String computerMove = moves[new Random().nextInt(moves.length)];
        computerImage.setImage(getImage(computerMove));

        String result;
        if (userMove.equals(computerMove)) {
            result = "Draw!";
        } else if (
                (userMove.equals("rock") && computerMove.equals("scissors")) ||
                        (userMove.equals("paper") && computerMove.equals("rock")) ||
                        (userMove.equals("scissors") && computerMove.equals("paper"))
        ) {
            result = "You Win!";
            winSound.play();
        } else {
            result = "You Lose!";
            loseSound.play();
        }

        resultLabel.setText(result);
        saveGameResult(userMove, computerMove, result);
    }

    private void saveGameResult(String player, String computer, String result) {
        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter("data/results.txt", true));
            writer.write("Player: " + player + ", Computer: " + computer + ", Result: " + result);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Image getImage(String move) {
        switch (move) {
            case "rock":
                return rockImage;
            case "paper":
                return paperImage;
            case "scissors":
                return scissorsImage;
            default:
                return null;
        }
    }

    public void showHowToPlay() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("How to Play");
        alert.setHeaderText(null);
        alert.setContentText("Choose Rock, Paper, or Scissors.\nRock beats Scissors\nScissors beats Paper\nPaper beats Rock");


        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        URL iconUrl = getClass().getResource("/icon.png");
        if (iconUrl != null) {
            alertStage.getIcons().add(new Image(iconUrl.toExternalForm()));
        }

        alert.showAndWait();
    }
}