package graphics;

import java.io.IOException;
import java.util.Optional;

import controlutility.*;
import graphicsutility.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import scoresystem.Player;
import scoresystem.PlayerFactory;
import scoresystem.PlayerFactoryImpl;
import timer.*;

/**
 * This class redirect the scene to the correct GameController
 */
public class ContextGraphics {
    private final RWSettings rwSett;
    private static final int TIMER_MULTIPLIER = 5;
    private Optional<String> firstPlayerName;
    private Optional<String> secondPlayerName;
    Player firstPlayer;

    public ContextGraphics(final Modality modality, final Difficulty difficulty, final int mines, final int height,
            final int width, final Stage stage) throws IOException {
        PlayerFactory playerFactory = new PlayerFactoryImpl();
        AcquireDialog getPlayer = new AcquireDialogImpl();
        AlertHandler alert = new AlertHandlerImpl();
        TimerFactory timerFactory = new TimerFactoryImpl();
        this.rwSett = new RWSettingsImpl();
        this.firstPlayerName = getPlayer.acquireFirst();

        if (this.firstPlayerName.isPresent()) {
            switch (modality) {
            case STANDARD:
                final GameController sdController = new SinglePlayerController(height, width, mines,
                        timerFactory.createTimerForStandardMode());
                if (this.firstPlayerName.get().isEmpty()) {
                    sdController.setPlayers(Optional.empty(), Optional.empty());
                } else {
                    this.firstPlayer = playerFactory.createPlayerForStandardMode(this.firstPlayerName.get(), difficulty);
                    sdController.setPlayers(Optional.of(this.firstPlayer), Optional.empty());
                }
                sceneStart(stage, sdController.getFXML(), sdController);
                break;

            case ONE_VS_ONE:
                this.secondPlayerName = getPlayer.acquireSecond();
                if (this.secondPlayerName.isPresent()) {
                    if (this.secondPlayerName.get().isEmpty()) {
                        final GameController ovoController = new MultiplayerController(height, width, mines,
                                timerFactory.createTimersFor1vs1Mode());
                        if (this.firstPlayerName.get().isEmpty()) {
                            ovoController.setPlayers(Optional.empty(), Optional.empty());
                        } else {
                            this.firstPlayer = playerFactory.createPlayerFor1vs1Mode(this.firstPlayerName.get(), difficulty,
                                    this.secondPlayerName.get());
                            ovoController.setPlayers(Optional.of(this.firstPlayer), Optional.empty());
                        }
                        sceneStart(stage, ovoController.getFXML(), ovoController);
                    } else {
                        if (this.firstPlayerName.get().equals(this.secondPlayerName.get())) {
                            alert.sameName();
                        } else {
                            this.firstPlayer = playerFactory.createPlayerFor1vs1Mode(this.firstPlayerName.get(), difficulty,
                                    this.secondPlayerName.get());
                            final Player secondPlayer = playerFactory.createPlayerFor1vs1Mode(this.secondPlayerName.get(),
                                    difficulty, this.firstPlayerName.get());
                            final GameController ovoController = new MultiplayerController(height, width, mines,
                                    timerFactory.createTimersFor1vs1Mode());
                            if (this.firstPlayerName.get().isEmpty()) {
                                ovoController.setPlayers(Optional.of(this.firstPlayer), Optional.empty());
                            } else {
                                ovoController.setPlayers(Optional.of(this.firstPlayer), Optional.of(secondPlayer));
                            }
                            sceneStart(stage, ovoController.getFXML(), ovoController);
                        }

                    }
                }
                break;

            case BTT:
                this.firstPlayer = playerFactory.createPlayerForBeatTheTimerMode(this.firstPlayerName.get(), difficulty);
                final int timerValue = mines * TIMER_MULTIPLIER;
                final GameController bttController = new SinglePlayerController(height, width, mines,
                        timerFactory.createTimerForBeatTheTimerMode(timerValue));
                if (this.firstPlayerName.get().isEmpty()) {
                    bttController.setPlayers(Optional.empty(), Optional.empty());
                } else {
                    bttController.setPlayers(Optional.of(this.firstPlayer), Optional.empty());
                }
                sceneStart(stage, bttController.getFXML(), bttController);
                break;
            }
        }
    }

    /**
     * The {@link OutOfTimeEvent} class talk with the {@link TimeEventsListener}
     * <p>
     * This method should occur if a {@link Timer} reaching its limit.
     *
     */
    private void sceneStart(final Stage stage, final String layout, final GameController modalityController) throws IOException {
        final Parent parentPane;
        final FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource(layout));
        loader.setController(modalityController);
        parentPane = loader.load();
        final Scene beatTheTimeScene = new Scene(parentPane, stage.getScene().getWidth(), stage.getScene().getHeight());
        beatTheTimeScene.getStylesheets().add(ClassLoader.getSystemResource("css/" + this.rwSett.getCss()).toExternalForm());
        stage.setScene(beatTheTimeScene);
        stage.show();
    }
}
