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
    private final Modality modality;
    private final RWSettings rwSett;
    private final AlertHandler alert;
    private final AcquireDialog getPlayer;
    private GameController modalityController;
    private static final int TIMER_MULTIPLIER = 5;

    public GraphicsImpl(final Modality modality, final Difficulty difficulty, final int mines, final int height, final int width, final Stage stage) throws IOException {
        this.difficulty = difficulty;
        this.playerFactory = new PlayerFactoryImpl();
        this.rwSett = new RWSettingsImpl();
        this.getPlayer = new AcquireDialogImpl();
        this.alert = new AlertHandlerImpl();
        this.modality = modality;

        TimerFactory timerFactory = new TimerFactoryImpl();
        switch (modality) {
            case STANDARD:
                final GameController sdController = new SinglePlayerController(height, width, mines, timerFactory.createTimerForStandardMode());
                sceneStart(stage,sdController.getFXML(),sdController);
                break;

            case ONE_VS_ONE:
                final GameController ovoController = new MultiplayerController(height, width, mines, timerFactory.createTimersFor1vs1Mode());
                sceneStart(stage,ovoController.getFXML(),ovoController);
                break;

            case BTT:
                final int timerValue = mines * TIMER_MULTIPLIER;
                final GameController bttController = new SinglePlayerController(height, width, mines, timerFactory.createTimerForBeatTheTimerMode(timerValue));
                sceneStart(stage,bttController.getFXML(),bttController);
                break;
        }
    }

    private void sceneStart(final Stage stage, final String layout, final GameController modalityController) throws IOException{
        final Parent parentPane;
        final FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource(layout));
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
        Optional<String> firstPlayerName = getPlayer.acquireFirst();
        Optional<String> secondPlayerName;
        switch (modality) {
            case STANDARD:
                if(firstPlayerName.isPresent()) {
                    final Player firstPlayer = playerFactory.createPlayerForStandardMode(firstPlayerName.get(),difficulty);
                    this.modalityController.setPlayers(Optional.of(firstPlayer),Optional.empty());
                } else {
                    this.modalityController.setPlayers(Optional.empty(),Optional.empty());
                }
                break;

            case ONE_VS_ONE:
                do {
                    secondPlayerName = getPlayer.acquireSecond();
                    if(secondPlayerName.isPresent() && firstPlayerName.isPresent()) {
                        if(firstPlayerName.get().equals(secondPlayerName.get())){
                            alert.sameName();
                        }
                    }
                }while (firstPlayerName.get().equals(secondPlayerName.get()));

                if(firstPlayerName.isPresent() && secondPlayerName.isPresent()) {
                    final Player firstPlayer = playerFactory.createPlayerFor1vs1Mode(firstPlayerName.get(),difficulty, secondPlayerName.get());
                    final Player secondPlayer = playerFactory.createPlayerFor1vs1Mode(secondPlayerName.get(),difficulty, firstPlayerName.get());
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
}
