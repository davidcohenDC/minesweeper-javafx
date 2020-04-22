package controlutility;

import javafx.scene.control.Alert;

/** *The implementation of {@link AlertStyle}. */
public class AlertStyleImpl implements AlertStyle {
    /**
     * @param alert
     *                  to set Style
     */
    @Override
    public void setStyle(final Alert alert) {
        alert.getDialogPane().setStyle("-fx-background-color: linear-gradient(lightgray, gray );" + "-fx-font-weight: bold;");
    }
}
