package gamegraphics;

import java.io.File;
import java.io.IOException;
import java.util.stream.IntStream;

import controlutility.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import timer.*;
import javax.sound.sampled.*;

/**
 * The Controller related to the SinglePlayer.fxml GUI.
 */

public final class GraphicsImpl implements Graphics {
    private final int height;
    private final int width;
    private final int mines;
    private ModalityController modalityController;

    //create parent style c
    private AlertStyle alStyle;
    private RWSettings rwSett;

    //create the timer
    private final TimerFactory timerFactory = new TimerFactoryImpl();

    //create the clip for the background song
    private Clip clip;
    private static final String SEPARATOR = System.getProperty("file.separator");
    private final String urlSound = System.getProperty("user.home") + SEPARATOR + ".minesweeper" + SEPARATOR + "sound" + SEPARATOR;

    public GraphicsImpl(final Modality modality, final Difficulty difficulty, final int mines, final int height, final int width, final Stage stage) throws IOException {
        this.height = height;
        this.width = width;
        this.mines = mines;
        this.rwSett = new RWSettingsImpl();

        //create pane and loader
        final Parent parentPane;
        final FXMLLoader loader;
        //modality check for panel
        switch (modality) {
            case STANDARD:
                //final Timer timer = timerFactory.createTimerForStandardMode();
                //final TimerView timerView = new TimerViewImpl(timer , lbTimer);
                //final TimerView timerView = timerViewFactory.createDefault(timer, this.lbTimer);
                loader = new FXMLLoader(ClassLoader.getSystemResource("layouts/SinglePlayer.fxml"));
                this.modalityController = new SinglePlayerController(this.height, this.width, this.mines);
                loader.setController(modalityController);
                parentPane = loader.load();
                Button Button = new Button("ciao");
                final Scene singlePlayerScene = new Scene(parentPane, stage.getScene().getWidth(), stage.getScene().getHeight());
                singlePlayerScene.getStylesheets().add(ClassLoader.getSystemResource("css/" + rwSett.getCss()).toExternalForm());
                stage.setScene(singlePlayerScene);
                stage.show();
                LoadElements();
                Button button = new Button(String.valueOf("ciao"));
                break;

            case ONE_VS_ONE:
                break;

            case BTT:
                break;

        }

    }

    @Override
    public void LoadElements() throws IOException {
        startSong();
    }

    private void startSong() {
        try {
            this.clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        final String path = urlSound + rwSett.getSong();
        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile())) {
            clip.open(audioStream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
            ex.printStackTrace();
        }
    }

}
