package controllers;

import java.io.IOException;
import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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
 *
 */
public final class HomeController {

    @FXML
    private AnchorPane rootPane;

    /** used to switch scene on the same stage. */
    private void switchScene(final Parent pane) {
        final Stage stage = (Stage) this.rootPane.getScene().getWindow();
        final Scene scene = new Scene(pane, stage.getScene().getWidth(), stage.getScene().getHeight());

        stage.setScene(scene);
    }

    @FXML
    private void btPlayGame(final ActionEvent event) throws IOException {
        event.consume();
        final Parent pane = FXMLLoader.load(ClassLoader.getSystemResource("layouts/playGame.fxml"));
        this.switchScene(pane);
    }

    @FXML
    private void btHowToPlay(final ActionEvent event) throws IOException {
        event.consume();
        final Parent pane = FXMLLoader.load(ClassLoader.getSystemResource("layouts/howToPlay.fxml")); //
        this.switchScene(pane);
    }

    @FXML
    private void btSettings(final ActionEvent event) throws IOException {
        event.consume();
        // TODO
    }

    @FXML
    private void btStatistics(final ActionEvent event) throws IOException {
        event.consume();
        // TODO
    }

    @FXML
    private void exit(final ActionEvent event) {
        event.consume();
        final Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Are you shure to exit?");
        final Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
            System.exit(0);
        }
    }

}
