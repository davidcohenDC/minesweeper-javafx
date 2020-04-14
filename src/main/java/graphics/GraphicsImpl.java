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
    public RWSettings rwSett;
    private AcquireDialog getPlayer;
    private static final int TIMER_MOLTIPLICATOR = 5;

    public GraphicsImpl(final Modality modality, final Difficulty difficulty, final int mines, final int height, final int width, final Stage stage) throws IOException {
        this.difficulty = difficulty;
        this.height = height;
        this.width = width;
        this.mines = mines;
        this.playerFactory = new PlayerFactoryImpl();
        this.rwSett = new RWSettingsImpl();
        this.getPlayer = new AcquireDialogImpl();
        this.modality = modality;

        TimerFactory timerFactory = new TimerFactoryImpl();
        switch (modality) {
            case STANDARD:
                final GameController sdController = new SinglePlayerController(height, width, mines, timerFactory.createTimerForStandardMode());
                sceneStart(stage,"layouts/SinglePlayer.fxml",sdController);
                break;

            case ONE_VS_ONE:

                final GameController ovoController = new MultiplayerController(height, width, mines, timerFactory.createTimersFor1vs1Mode());
                sceneStart(stage,"layouts/Multiplayer.fxml",ovoController);
                break;

            case BTT:
                final int timerValue = this.mines* TIMER_MOLTIPLICATOR;
                final GameController bttController = new SinglePlayerController(height, width, mines, timerFactory.createTimerForBeatTheTimerMode(timerValue));
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
        stage.show();    }

    @Override
    public void setPlayer() {
        this.firstPlayerName = getPlayer.acquireFirst();
        switch (modality) {
            case STANDARD:
                if(firstPlayerName.isPresent()) {
                    System.out.println(firstPlayerName.get());
                    final Player firstPlayer = playerFactory.createPlayerForStandardMode(firstPlayerName.get(),difficulty);
                    this.modalityController.setPlayers(Optional.of(firstPlayer),Optional.empty());
                } else {
                    this.modalityController.setPlayers(Optional.empty(),Optional.empty());
                }
                break;

            case ONE_VS_ONE:
                this.secondPlayerName = getPlayer.acquireSecond();
                if(firstPlayerName.isPresent() && secondPlayerName.isPresent()) {
                    final Player firstPlayer = playerFactory.createPlayerFor1vs1Mode(firstPlayerName.get(),difficulty,secondPlayerName.get());
                    System.out.println(firstPlayerName.get());
                    final Player secondPlayer = playerFactory.createPlayerFor1vs1Mode(secondPlayerName.get(),difficulty,firstPlayerName.get());
                    System.out.println(secondPlayerName.get());
                    this.modalityController.setPlayers(Optional.of(firstPlayer),Optional.of(secondPlayer));
                } else {
                    this.modalityController.setPlayers(Optional.empty(),Optional.empty());
                }
                break;

            case BTT:
                if(firstPlayerName.isPresent()) {
                    final Player firstPlayer = playerFactory.createPlayerForBeatTheTimerMode(firstPlayerName.get(),difficulty);
                    this.modalityController.setPlayers(Optional.of(firstPlayer),Optional.empty());
                } else {
                    this.modalityController.setPlayers(Optional.empty(),Optional.empty());
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



}
