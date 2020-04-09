package gamegraphics;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.IntStream;

import controlutility.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.checkerframework.checker.nullness.Opt;
import scoresystem.Player;
import scoresystem.PlayerFactory;
import scoresystem.PlayerFactoryImpl;
import timer.*;
import javax.sound.sampled.*;

/**
 * The Controller related to the SinglePlayer.fxml GUI.
 */

public final class GraphicsImpl implements Graphics {
    private final PlayerFactory playerFactory;
    private final int height;
    private final int width;
    private final int mines;
    private  ModalityController modalityController;
    private TextInputDialog dialog = new TextInputDialog("");

    //create parent style c
    private AlertStyle alStyle;
    private RWSettings rwSett;
    private Stage stage;

    //create the timer
    private final TimerFactory timerFactory = new TimerFactoryImpl();

    //create the clip for the background song
    private Clip clip;
    private static final String SEPARATOR = System.getProperty("file.separator");
    private final String urlSound = System.getProperty("user.home") + SEPARATOR + ".minesweeper" + SEPARATOR + "sound" + SEPARATOR;


    public GraphicsImpl(final Modality modality, final Difficulty difficulty, final int mines, final int height, final int width, final Stage stage) throws IOException {
        this.playerFactory = new PlayerFactoryImpl();
        this.height = height;
        this.width = width;
        this.mines = mines;
        this.rwSett = new RWSettingsImpl();
        this.stage = stage;
        //this.dialog.setTitle("NICKNAME");
        this.dialog.setHeaderText("Input your nickname if you want to save your score :)");
        this.dialog.setContentText("Enter your nickname: ");

        //create pane and loader
        //alert...
        final Optional<String> playerName = dialog.showAndWait();
        final Parent parentPane;
        final FXMLLoader loader;
        //modality check for panel
        switch (modality) {
            case STANDARD:
                loader = new FXMLLoader(ClassLoader.getSystemResource("layouts/SinglePlayer.fxml"));



                this.modalityController = new SinglePlayerController(this.height, this.width, this.mines,this.timerFactory.createTimerForStandardMode());
                if(playerName.isPresent()) {
                    final Player player = playerFactory.createPlayerForStandardMode(playerName.get(),difficulty);
                    this.modalityController.setPlayer(Optional.of(player));
                } else {
                    this.modalityController.setPlayer(Optional.empty());
                }
                loader.setController(modalityController);
                parentPane = loader.load();
                final Scene singlePlayerScene = new Scene(parentPane, stage.getScene().getWidth(), stage.getScene().getHeight());
                singlePlayerScene.getStylesheets().add(ClassLoader.getSystemResource("css/" + rwSett.getCss()).toExternalForm());
                stage.setAlwaysOnTop(true);
                stage.setScene(singlePlayerScene);
                stage.show();
                break;

            case ONE_VS_ONE:

                break;

            case BTT:
                loader = new FXMLLoader(ClassLoader.getSystemResource("layouts/SinglePlayer.fxml"));
                this.modalityController = new SinglePlayerController(this.height, this.width, this.mines,this.timerFactory.createTimerForBeatTheTimerMode(10));
                loader.setController(modalityController);
                parentPane = loader.load();
                final Scene beatTheTimeScene = new Scene(parentPane, stage.getScene().getWidth(), stage.getScene().getHeight());
                beatTheTimeScene.getStylesheets().add(ClassLoader.getSystemResource("css/" + rwSett.getCss()).toExternalForm());
                stage.setScene(beatTheTimeScene);
                stage.show();
                loadElements();
                break;

        }

    }

    @Override
    public void loadElements() throws IOException {
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
