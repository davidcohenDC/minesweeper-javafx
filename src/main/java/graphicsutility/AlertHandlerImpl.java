package graphicsutility;

import controlutility.AlertStyle;
import controlutility.AlertStyleImpl;
import gamelogics.GameStatus;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import scoresystem.Player;

import java.util.Optional;

public class AlertHandlerImpl implements AlertHandler{
    private AlertStyle alStyle;
    final Alert alert = new Alert(Alert.AlertType.INFORMATION);


    public AlertHandlerImpl() {
    this.alStyle = new AlertStyleImpl();
    }

    @Override
    public void won(final int score) {
        alert.setTitle("| CONGRATULATIONS |");
        alert.setContentText("YOU WON!!" + "Your score: " + score);
        alert.setHeaderText(null);
        alert.getDialogPane().setStyle("-fx-background-color: linear-gradient(green, darkgreen);" + "-fx-font-weight: bold;");
        alert.showAndWait();

    }

    @Override
    public void lost() {
        alert.setTitle("GAME OVER");
        alert.setContentText("YOU LOST!!");
        alert.setHeaderText(null);
        alert.getDialogPane().setStyle("-fx-background-color: linear-gradient(red, darkred);" + "-fx-font-weight: bold;");
        alert.showAndWait();
    }

    public void lostWithTimer() {
        alert.setTitle("GAME OVER");
        alert.setContentText("YOU LOST!");
        alert.setHeaderText(null);
        this.alStyle.setStyle(alert);
        Platform.runLater(alert::showAndWait);
    }




}
