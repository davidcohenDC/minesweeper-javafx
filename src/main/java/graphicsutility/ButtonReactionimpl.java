package graphicsutility;

import controlutility.RWSettings;
import controlutility.RWSettingsImpl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The implementation of {@link ButtonReaction}.
 */
public class ButtonReactionimpl implements ButtonReaction {
    private AnchorPane rootPane;
    private AlertHandler alert;

    public ButtonReactionimpl(final AnchorPane rootPane) {
        this.rootPane = rootPane;
        this.alert = new AlertHandlerImpl();
    }

    @Override
    public final Boolean backHome() throws IOException {
        if (this.alert.confirm()) {
            final RWSettings rwSett = new RWSettingsImpl();
            final Parent pane = FXMLLoader.load(ClassLoader.getSystemResource("layouts/playGame.fxml"));
            final Stage stage = (Stage) this.rootPane.getScene().getWindow();
            final Scene scene = new Scene(pane, stage.getScene().getWidth(), stage.getScene().getHeight());
            scene.getStylesheets().add(ClassLoader.getSystemResource("css/" + rwSett.getCss()).toExternalForm());
            stage.setScene(scene);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public final void checkMusic(final Button btnSong, final SongAgent music) {
        if (music.isPlaying()) {
            btnSong.setText("MUTED");
            music.pause();
        } else {
            btnSong.setText("MUTE");
            music.play();
        }
    }

    @Override
    public final void checkDualMusic(final Button btnSong, final Button btnSong2, final SongAgent music) {
        if (music.isPlaying()) {
            btnSong.setText("MUTED");
            btnSong2.setText("MUTED");
            music.pause();
        } else {
            btnSong.setText("MUTE");
            btnSong2.setText("MUTE");
            music.play();
        }
    }

}
