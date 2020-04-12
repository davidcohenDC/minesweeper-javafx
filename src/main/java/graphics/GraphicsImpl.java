package graphics;

import java.io.IOException;
import java.util.Optional;

import controlutility.*;
import graphicsutility.AcquireDialog;
import graphicsutility.AcquireDialogImpl;
import graphicsutility.AlertHandler;
import graphicsutility.AlertHandlerImpl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import scoresystem.Player;
import scoresystem.PlayerFactory;
import scoresystem.PlayerFactoryImpl;
import timer.*;

/**
 * The Controller related to the SinglePlayer.fxml GUI.
 */

public class GraphicsImpl implements Graphics {
    private final PlayerFactory playerFactory;
    private final Difficulty difficulty;
    private GameController modalityController;
    private int mines;
    private int height;
    private int width;
    private Modality modality;
    private Optional<String> firstPlayerName;
    private Optional<String> secondPlayerName;
    private Optional<Player> firstplayer;
    private Optional<Player> secondplayer;
    public RWSettings rwSett;
    private final TimerFactory timerFactory = new TimerFactoryImpl();
    private AcquireDialog getPlayer;
    private Stage stage;

    public GraphicsImpl(final Modality modality, final Difficulty difficulty, final int mines, final int height, final int width, final Stage stage) throws IOException {
        this.difficulty = difficulty;
        this.height = height;
        this.width = width;
        this.mines = mines;
        this.playerFactory = new PlayerFactoryImpl();
        this.rwSett = new RWSettingsImpl();
        this.getPlayer = new AcquireDialogImpl();
        this.modality = modality;

        switch (modality) {
            case STANDARD:
                final GameController sdController = new SinglePlayerController(height, width, mines,this.timerFactory.createTimerForStandardMode());
                sceneStart(stage,"layouts/SinglePlayer.fxml",sdController);
                break;

            case ONE_VS_ONE:
                final GameController ovoController = new MultiplayerController(height, width, mines,this.timerFactory.createTimerForStandardMode());
                sceneStart(stage,"layouts/Multiplayer.fxml",ovoController);
                break;

            case BTT:
                final GameController bttController = new SinglePlayerController(height, width, mines,this.timerFactory.createTimerForBeatTheTimerMode(5));
                sceneStart(stage,"layouts/SinglePlayer.fxml",bttController);
                break;
        }

    }

    private void sceneStart(final Stage stage, final String layout, final GameController modalityController) throws IOException{
        final Parent parentPane;
        final FXMLLoader loader;
        loader = new FXMLLoader(ClassLoader.getSystemResource(layout));
        this.modalityController = modalityController;
        setPlayer();
        loader.setController(modalityController);
        parentPane = loader.load();
        final Scene beatTheTimeScene = new Scene(parentPane, stage.getScene().getWidth(), stage.getScene().getHeight());
        beatTheTimeScene.getStylesheets().add(ClassLoader.getSystemResource("css/" + rwSett.getCss()).toExternalForm());
        stage.setScene(beatTheTimeScene);
        stage.show();
    }

    @Override
    public void setPlayer() {
        this.firstPlayerName = getPlayer.acquireFirst();
        switch (modality) {
            case STANDARD:
                if(firstPlayerName.isPresent()) {
                    final Player player = playerFactory.createPlayerForStandardMode(firstPlayerName.get(),difficulty);
                    this.modalityController.setPlayer(Optional.of(player));
                } else {
                    this.modalityController.setPlayer(Optional.empty());
                }
                break;

            case ONE_VS_ONE:
                this.secondPlayerName = getPlayer.acquireSecond();
                if(firstPlayerName.isPresent() && secondPlayerName.isPresent()) {
                    final Player player = playerFactory.createPlayerFor1vs1Mode(firstPlayerName.get(),difficulty,secondPlayerName.get());
                    this.modalityController.setPlayer(Optional.of(player));
                } else {
                    this.modalityController.setPlayer(Optional.empty());
                }
                break;

            case BTT:
                if(firstPlayerName.isPresent()) {
                    final Player player = playerFactory.createPlayerForBeatTheTimerMode(firstPlayerName.get(),difficulty);
                    this.modalityController.setPlayer(Optional.of(player));
                } else {
                    this.modalityController.setPlayer(Optional.empty());
                }
                break;
        }
    }

    @Override
    public Integer getWidth() {
        return this.width;
    }

    @Override
    public Integer getHeight() {
        return this.height;
    }

    @Override
    public Stage getStage() {
        return this.stage;
    }


}
