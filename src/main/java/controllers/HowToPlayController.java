package controllers;

import java.io.IOException;

import controlutility.ReadRules;
import controlutility.ReadRulesImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The Controller common to Settings,Statistics,PlayGame,HowToPlay.fxml. It
 * control the Home button.
 *
 */
public final class HowToPlayController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextArea txtArea = new TextArea();

    /**
     * @exception IOException
     *                            if an I/O error occurs.
     */
    public void initialize() throws IOException {
        final ReadRules reader = new ReadRulesImpl();
        this.txtArea.clear();
        reader.getAllLines().forEach(l -> txtArea.appendText(l + System.getProperty("line.separator")));
    }

    @FXML
    private void btBackHome(final ActionEvent event) throws IOException {
        event.consume();
        final Parent pane = FXMLLoader.load(ClassLoader.getSystemResource("layouts/home.fxml"));
        Stage stage = (Stage) this.rootPane.getScene().getWindow();
        final Scene scene = new Scene(pane, stage.getScene().getWidth(), stage.getScene().getHeight());
        stage.setScene(scene);
    }

}
