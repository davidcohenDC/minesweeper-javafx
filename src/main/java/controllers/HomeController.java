package controllers;

import java.io.IOException;
import java.util.Optional;
import controlutility.AlertStyle;
import controlutility.AlertStyleImpl;
import controlutility.RWSettings;
import controlutility.RWSettingsImpl;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The Controller related to the home.fxml GUI.
 * The implementation of {@link HomeInterface}.
 */
public final class HomeController implements HomeInterface {
    private RWSettings rwSett;
    private AlertStyle alStyle;

    @FXML
    private AnchorPane rootPane;

    @Override
    public void initialize() throws IOException  {
        this.rwSett = new RWSettingsImpl();
        this.alStyle = new AlertStyleImpl();
    }

    /** used to switch scene on the same stage. */
    private void switchScene(final Parent pane) {
        final Stage stage = (Stage) this.rootPane.getScene().getWindow();
        final Scene scene = new Scene(pane, stage.getScene().getWidth(), stage.getScene().getHeight());
        scene.getStylesheets().add(ClassLoader.getSystemResource("css/" + rwSett.getCss()).toExternalForm());
        stage.setScene(scene);
    }

    @FXML
    @Override
    public void btPlayGame() throws IOException {
        final Parent pane = FXMLLoader.load(ClassLoader.getSystemResource("layouts/playGame.fxml"));
        this.switchScene(pane);
    }

    @FXML
    @Override
    public void btHowToPlay() throws IOException {
        final Parent pane = FXMLLoader.load(ClassLoader.getSystemResource("layouts/howToPlay.fxml"));
        this.switchScene(pane);
    }

    @FXML
    @Override
    public void btSettings() throws IOException {
        final Parent pane = FXMLLoader.load(ClassLoader.getSystemResource("layouts/settings.fxml"));
        this.switchScene(pane);
    }

    @FXML
    @Override
    public void btStatistics() throws IOException {
        final Parent pane = FXMLLoader.load(ClassLoader.getSystemResource("layouts/mainStatistics.fxml"));
        this.switchScene(pane);
    }

    @FXML
    @Override
    public void exit() throws IOException {
        final Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Are you sure you want to exit?");
        alert.setHeaderText(null);
        this.alStyle.setStyle(alert);
        final Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
            System.exit(0);
        }
    }

}
