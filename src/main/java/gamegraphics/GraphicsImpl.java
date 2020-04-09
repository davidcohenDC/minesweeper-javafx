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
import javafx.scene.control.Dialog;
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
    private final Difficulty difficulty;
    private  ModalityController modalityController;
    private TextInputDialog dialog = new TextInputDialog("");
    //create parent style c
    private RWSettings rwSett;
    private Stage stage;
    //create the timer
    private final TimerFactory timerFactory = new TimerFactoryImpl();
    //create the clip for the background song




    public GraphicsImpl(final Modality modality, final Difficulty difficulty, final int mines, final int height, final int width, final Stage stage) throws IOException {
        this.height = height;
        this.width = width;
        this.mines = mines;
        this.stage = stage;
        this.difficulty = difficulty;
        this.playerFactory = new PlayerFactoryImpl();
        this.rwSett = new RWSettingsImpl();

        this.dialog.setTitle("| SAVE YOUR SCORE |");
        this.dialog.setHeaderText("Input your nickname if you want to save your score :)");
        this.dialog.setContentText("Enter your nickname: ");

        final Optional<String> playerName = dialog.showAndWait();
        final Parent parentPane;
        final FXMLLoader loader;

        //modality check for panel
        switch (modality) {
            case STANDARD:
                loader = new FXMLLoader(ClassLoader.getSystemResource("layouts/SinglePlayer.fxml"));
                this.modalityController = new SinglePlayerController(this.height, this.width, this.mines,this.timerFactory.createTimerForStandardMode());
                setPlayer(playerName);
                loader.setController(modalityController);
                parentPane = loader.load();
                final Scene singlePlayerScene = new Scene(parentPane, stage.getScene().getWidth(), stage.getScene().getHeight());
                singlePlayerScene.getStylesheets().add(ClassLoader.getSystemResource("css/" + rwSett.getCss()).toExternalForm());
                stage.setScene(singlePlayerScene);
                stage.show();
                //startSong();
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
        //startSong();
    }




    private void setPlayer(final Optional<String> playerName) {
        if(playerName.isPresent()) {
            final Player player = playerFactory.createPlayerForStandardMode(playerName.get(),difficulty);
            this.modalityController.setPlayer(Optional.of(player));
        } else {
            this.modalityController.setPlayer(Optional.empty());
        }
    }


}
