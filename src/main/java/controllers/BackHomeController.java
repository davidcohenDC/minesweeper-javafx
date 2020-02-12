package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The Controller common to Settings,Statistics,PlayGame,HowToPlay.fxml. It
 * control the Home button.
 *
 */
public class BackHomeController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private void btBackHome(final ActionEvent event) throws IOException {
        event.consume();
        final Parent pane = FXMLLoader.load(ClassLoader.getSystemResource("layouts/home.fxml"));
        final Stage stage = (Stage) this.rootPane.getScene().getWindow();
        final Scene scene = new Scene(pane, stage.getScene().getWidth(), stage.getScene().getHeight());
        stage.setScene(scene);
    }

}
