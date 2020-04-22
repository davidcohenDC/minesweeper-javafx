package graphics;

import controlutility.RWSettingsImpl;
import gamelogics.*;
import graphicsutility.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import scoresystem.Player;
import scoresystem.ScoreWriter;
import scoresystem.ScoreWriterImpl;
import timer.*;
import timer.Timer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The Controller related to the SinglePlayer.fxml GUI.
 */
public class SinglePlayerController extends AbstractGameController {

    private final int height;
    private final int width;
    private final int mines;
    private final GameEngine engine;
    private final Timer timer;
    private Map<Pair<Integer, Integer>, TileImpl> tilesMap;
    private Optional<Player> firstPlayer;
    private TimerView timerView;
    private AlertHandler alert;
    private SongAgent music;
    private ButtonReaction btnAction;
    private Boolean timerOver = false;
    private PlayerSupervisor supervisorP1;
    private int ccflagsP1;
    private ScoreWriter scoreWriter;

    @FXML
    private Label lbTimerP1 = new Label();
    @FXML
    private Label lbFlagP1;
    @FXML
    private Label lbNameP1;
    @FXML
    private Label lbMinesP1;
    @FXML
    private Button btnRestart;
    @FXML
    private Button btnBackHome;
    @FXML
    private Button btnSong;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private AnchorPane rootPane;

    public SinglePlayerController(final int height, final int width, final int mines, final Timer timer) {
        super(height, width, mines, timer);
        this.height = height;
        this.width = width;
        this.mines = mines;
        this.timer = timer;
        this.engine = new GameEngineImpl(width, height, mines);
    }

    @Override
    public final void initialize() throws IOException {
        HashMap<PlayerSupervisor, Boolean> playerMap = new HashMap<>();
        this.timerView = new TimerViewImpl(this.timer, this.lbTimerP1);
        this.alert = new AlertHandlerImpl();
        this.music = new SongAgentImpl(new RWSettingsImpl());
        this.btnAction = new ButtonReactionimpl(this.rootPane);
        this.supervisorP1 = new PlayerSupervisorImpl(this.firstPlayer, true, playerMap);
        this.scoreWriter = new ScoreWriterImpl();

        this.lbFlagP1.setText("FLAGS:" + this.mines);
        this.lbMinesP1.setText("MINE:" + this.mines);
        this.timerView.startDisplaying();
        this.supervisorP1.view(this.lbNameP1);
        this.lbTimerP1.setText(String.valueOf(this.timer.getValue()));
        this.btnSong.setText("MUTE");
        this.ccflagsP1 = this.mines;

        GridPane grid = new GridPane();
        final TileBuilder tb = new TileBuilderImpl();
        tb.withHeight(height);
        tb.withWidth(width);
        tb.withGrid(grid);
        this.tilesMap = tb.build();

        this.mainBorderPane.setCenter(grid);
        this.music.play();

        setButtons();
        setClickHandler(this.engine, this.tilesMap);
    }

    @Override
    public final void setButtons() {
        this.btnBackHome.setOnAction(t -> {
            try {
                this.music.pause();
                this.timer.stop();
                if (this.btnAction.backHome()) {
                    closeElements();
                } else {
                    this.music.play();
                    this.timer.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        this.btnSong.setOnMouseClicked(t -> this.btnAction.checkMusic(this.btnSong, this.music));
    }

    @Override
    public final void leftClickHandler(final TileImpl tile, final int x, final int y) {
        if (!this.timer.isRunning()) {
            this.timerView.setTimeEventListener(new TimeEventsListenerImpl(this));
            this.timer.start();
        }
        if (!tile.isFlagged()) {
            this.engine.hit(new Pair<>(x, y));
            refreshBoard(this.engine, this.tilesMap);
        }

        if (!this.engine.getGameStatus().equals((GameStatus.PLAYING))) {
            endGame(this.engine.getGameStatus());
        } else {
            if (tile.getValue() == 0) {
                tile.audioBigClick();
            } else {
                tile.audioClick();
            }
        }
    }

    @Override
    public final void rightClickHandler(final TileImpl tile, final int x, final int y) {
        if (!tile.isFlagged()) {
            this.ccflagsP1--;
        } else {
            this.ccflagsP1++;
        }
        tile.setFlag();
        if (this.ccflagsP1 >= 0 && this.ccflagsP1 < 10) {
            this.lbFlagP1.setText("FLAGS:0" + this.ccflagsP1);
        } else {
            this.lbFlagP1.setText("FLAGS:" + this.ccflagsP1);
        }

    }

    @Override
    public final void endGame(GameStatus status) {
        closeElements();
        if (status.equals(GameStatus.LOST)) {
            writePlayer(GameStatus.LOST);
            if (this.timerOver) {
                this.alert.lostWithTimer();
            } else {
                this.alert.lost(this.firstPlayer);
            }
            try {
                backHome();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (status.equals(GameStatus.WON)) {
            writePlayer(GameStatus.WON);
            this.alert.won(this.firstPlayer);
            try {
                backHome();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public final void closeElements() {
        this.timer.stop();
        this.music.close();
    }

    @Override
    public final void writePlayer(GameStatus status) {
        if (status.equals(GameStatus.LOST)) {
            this.firstPlayer.ifPresent(Player::lost);
        } else {
            this.firstPlayer.ifPresent(player -> player.won(this.timer.getValue()));
        }
        this.firstPlayer.ifPresent(player -> this.scoreWriter.write(player));
    }

    @Override
    public final void setPlayers(Optional<Player> firstplayer, Optional<Player> secondplayer) {
        this.firstPlayer = firstplayer;
    }

    @Override
    public final String getFXML() {
        return "layouts/SinglePlayer.fxml";
    }

    /**
     * The {@link OutOfTimeEvent} class talk with the {@link TimeEventsListener}.
     * <p>
     * This method should occur if a {@link Timer} reaching its limit.
     *
     */
    public void endTimer() {
        this.timerOver = true;
        endGame(GameStatus.LOST);
    }

}
