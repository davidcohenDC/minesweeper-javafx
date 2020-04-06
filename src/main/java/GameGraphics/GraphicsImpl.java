package gamegraphics;

import java.io.IOException;

import controlutility.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import timer.TimerFactory;

import javax.swing.*;

/**
 * The Controller related to the SinglePlayer.fxml GUI.
 */
public final class GraphicsImpl implements Graphics {
    private TimerFactory timer;
    private final int height;
    private final int width;
    private final int mines;
    private AlertStyle alStyle;
    private RWSettings rwSett;
    private final GridPane grid = new GridPane();

    @FXML
    private JLabel lbFlags;

    @FXML
    private JLabel lbTimer;

    @FXML
    private Button btnRestart;

    @FXML
    private Button btnStartGame;

    public GraphicsImpl(final Modality modality, final int mines, final int height, final int width, final Stage stage) throws IOException{
        this.height = height;
        this.width = width;
        this.mines = mines;

        this.rwSett = new RWSettingsImpl();
        if (modality == Modality.STANDARD) {
            //new SinglePlayerController(height,width,mines);
            final Parent pane = FXMLLoader.load(ClassLoader.getSystemResource("layouts/SinglePlayer.fxml"));
            final Scene scene = new Scene(pane, stage.getScene().getWidth(), stage.getScene().getHeight());
            scene.getStylesheets().add(ClassLoader.getSystemResource("css/" + rwSett.getCss()).toExternalForm());
            stage.setScene(scene);
            stage.show();

        } else if (modality == Modality.ONE_VS_ONE) {

        } else {

        }
    }

    @Override
    public void initialize() throws IOException {
        this.rwSett = new RWSettingsImpl();
    }

    @Override
    public void btPlayGame() throws IOException {

    }

    @Override
    public void btRestart() throws IOException {

    }

    @Override
    public void exit() throws IOException {

    }
}
