package graphicsutility;

import controlutility.AlertStyle;
import controlutility.AlertStyleImpl;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import scoresystem.Player;

import java.util.Optional;

/**
 * The implementation of {@link AlertHandler}.
 */
public class AlertHandlerImpl implements AlertHandler {
    private final AlertStyle alStyle;
    private final Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public AlertHandlerImpl() {
        this.alStyle = new AlertStyleImpl();
    }

    @Override
    public final void won(final Optional<Player> player) {
        if (player.isPresent()) {
            this.alert.setTitle("|CONGRATULATIONS |");
            this.alert.setContentText(player.get().getName() + " YOU WON!!" + '\r' + "Your score: " + player.get().getScore());
        } else {
            this.alert.setTitle("| CONGRATULATIONS |");
            this.alert.setContentText("YOU WON!!");
        }

        this.alert.setHeaderText(null);
        this.alert.getDialogPane()
                .setStyle("-fx-background-color: linear-gradient(green, darkgreen);" + "-fx-font-weight: bold;");
        this.alert.showAndWait();

    }

    @Override
    public final Boolean confirm() {
        ButtonType btnOk = new ButtonType("Ok");
        ButtonType btnNo = new ButtonType("No");
        Alert alConfirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", btnOk, btnNo);
        alConfirm.showAndWait();
        return alConfirm.getResult() == btnOk;
    }

    @Override
    public final void lost(final Optional<Player> player) {
        if ((player.isEmpty()) || player.get().getName().equals("")) {
            this.alert.setTitle("| GAME OVER |");
            this.alert.setContentText("YOU LOST!!");
        } else {
            this.alert.setTitle("|GAME OVER |");
            this.alert.setContentText(player.get().getName() + " YOU LOST!!");
        }
        this.alert.setHeaderText(null);
        this.alert.getDialogPane().setStyle("-fx-background-color: linear-gradient(red, darkred);" + "-fx-font-weight: bold;");
        this.alert.showAndWait();
    }

    @Override
    public final void lostWithTimer() {
        this.alert.setTitle("GAME OVER");
        this.alert.setContentText("YOU LOST!");
        this.alert.setHeaderText(null);
        this.alStyle.setStyle(this.alert);
        Platform.runLater(this.alert::showAndWait);
    }

    @Override
    public final void sameName() {
        this.alert.setTitle("| ERROR |");
        this.alert.setContentText("Same name!!");
        this.alert.setHeaderText(null);
        this.alert.getDialogPane().setStyle("-fx-font-weight: bold;");
        this.alert.showAndWait();
    }

}
