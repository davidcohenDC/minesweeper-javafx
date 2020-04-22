package controllers;

import java.io.IOException;

import controlutility.RWSettings;
import controlutility.RWSettingsImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The Controller common to Settings,Statistics,PlayGame,HowToPlay.fxml. It
 * control the Home button.
 * The implementation of {@link BackHomeInterface}.
 */
public class BackHomeController implements BackHomeInterface {

    @FXML
    private AnchorPane rootPane;

    /**this method isn't final because in settingsController I override it.**/
    @FXML
    @Override
    public void btBackHome() throws IOException {
        final RWSettings rwSett = new RWSettingsImpl();
        final Parent pane = FXMLLoader.load(ClassLoader.getSystemResource("layouts/home.fxml"));
        final Stage stage = (Stage) this.rootPane.getScene().getWindow();
        final Scene scene = new Scene(pane, stage.getScene().getWidth(), stage.getScene().getHeight());
        scene.getStylesheets().add(ClassLoader.getSystemResource("css/" + rwSett.getCss()).toExternalForm());
        stage.setScene(scene);
    }
}
