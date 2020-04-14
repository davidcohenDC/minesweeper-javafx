package graphicsutility;

import controlutility.AlertStyle;
import controlutility.AlertStyleImpl;
import gamelogics.GameStatus;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import org.checkerframework.checker.nullness.Opt;
import scoresystem.Player;

import java.util.Optional;

public class AlertHandlerImpl implements AlertHandler{
    private AlertStyle alStyle;
    final Alert alert = new Alert(Alert.AlertType.INFORMATION);


    public AlertHandlerImpl() {
    this.alStyle = new AlertStyleImpl();
    }

    @Override
    public void won(final Optional<Player> player) {
        if(player.isPresent()) {
            alert.setTitle( "|CONGRATULATIONS |");
            alert.setContentText(player.get().getName()+" YOU WON!!"+'\r'+ "Your score: " + player.get().getScore());
        } else {
            alert.setTitle("| CONGRATULATIONS |");
            alert.setContentText("YOU WON!!");
        }

        alert.setHeaderText(null);
        alert.getDialogPane().setStyle("-fx-background-color: linear-gradient(green, darkgreen);" + "-fx-font-weight: bold;");
        alert.showAndWait();

    }
    @Override
    public void confirm() {
        alert.setTitle("| ARE YOU SURE? |");
        alert.setContentText("Leave the game");
        alert.setHeaderText(null);
        alert.getDialogPane().setStyle("-fx-background-color: linear-gradient(green, darkgreen);" + "-fx-font-weight: bold;");
        alert.showAndWait();
    }

    @Override
    public void lost(final Optional<Player> player) {
        if ((!player.isPresent()) || player.get().getName().equals("")) {
            alert.setTitle("| GAME OVER |");
            alert.setContentText("YOU LOST!!");
        } else {
            alert.setTitle( "|GAME OVER |");
            alert.setContentText(player.get().getName()+" YOU LOST!!");
        }
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

    public void sameName() {
        alert.setTitle("| ERROR |");
        alert.setContentText("Same name!!");
        alert.setHeaderText(null);
        alert.getDialogPane().setStyle("-fx-font-weight: bold;");
        alert.showAndWait();
    }





}
