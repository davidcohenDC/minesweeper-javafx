package graphicsutility;

import controlutility.RWSettings;
import controlutility.RWSettingsImpl;
import graphics.Graphics;
import graphics.GraphicsImpl;
import graphics.SinglePlayerController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ButtonReactionimpl implements ButtonReaction {
    private AnchorPane rootPane;

    public ButtonReactionimpl(final AnchorPane rootPane) {
        this.rootPane = rootPane;
    }

    @Override
    public void backHome() throws IOException {
        //closeElements();
        final RWSettings rwSett = new RWSettingsImpl();
        final Parent pane = FXMLLoader.load(ClassLoader.getSystemResource("layouts/playGame.fxml"));
        final Stage stage = (Stage) this.rootPane.getScene().getWindow();
        final Scene scene = new Scene(pane, stage.getScene().getWidth(), stage.getScene().getHeight());
        scene.getStylesheets().add(ClassLoader.getSystemResource("css/" + rwSett.getCss()).toExternalForm());
        stage.setScene(scene);
    }

    @Override
    public void restartGame()  {
    }


}
