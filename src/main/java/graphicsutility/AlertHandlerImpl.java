package graphicsutility;

import controlutility.AlertStyle;
import controlutility.AlertStyleImpl;
import gamelogics.GameStatus;
import javafx.scene.control.Alert;

public class AlertHandlerImpl implements AlertHandler{
    private AlertStyle alStyle;
    final Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public AlertHandlerImpl() {
    this.alStyle = new AlertStyleImpl();
    }

    @Override
    public void won() {
        alert.setTitle("YOU WON!");
        alert.setContentText("GAME OVER");
        alert.setHeaderText(null);
        this.alStyle.setStyle(alert);
        alert.showAndWait();
    }

    @Override
    public void lost() {
        alert.setTitle("YOU LOST!");
        alert.setContentText("GAME OVER");
        alert.setHeaderText(null);
        this.alStyle.setStyle(alert);
        alert.showAndWait();
    }

}
