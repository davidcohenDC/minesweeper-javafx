package graphics;

import controlutility.RWSettingsImpl;
import gamelogics.GameEngine;
import gamelogics.GameEngineImpl;
import gamelogics.GameStatus;
import gamelogics.Pair;
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
import timer.DoubleTimer;
import timer.TimerView;
import timer.TimerViewImpl;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The Controller related to the Multiplayer.fxml GUI.
 */
public class MultiplayerController extends AbstractGameController {
    private final int height;
    private final int width;
    private final int mines;
    private final GameEngine engineP1;
    private final GameEngine engineP2;
    private final DoubleTimer timer;
    private static final int MAX_CLICK = 1;
    private Map<Pair<Integer, Integer>, TileImpl> tilesMap1;
    private Map<Pair<Integer, Integer>, TileImpl> tilesMap2;
    private HashMap<PlayerSupervisor, Boolean> playersMap = new HashMap<>();
    private Optional<Player> firstPlayer;
    private Optional<Player> secondPlayer;
    private PlayerSupervisor supervisorP1;
    private ScoreWriter scoreWriter;
    private AlertHandler alert;
    private SongAgent music;
    private ButtonReaction btnAction;
    private int clickCount = 0;
    private int ccflagsP1;
    private int ccflagsP2;

    @FXML
    private Label lbTimerP2 = new Label();
    @FXML
    private Label lbTimerP1 = new Label();
    @FXML
    private Label lbFlagP1;
    @FXML
    private Label lbNameP1;
    @FXML
    private Label lbFlagP2;
    @FXML
    private Label lbNameP2;
    @FXML
    private Label lbMinesP1;
    @FXML
    private Label lbMinesP2;
    @FXML
    private Button btnGiveUpP1;
    @FXML
    private Button btnGiveUpP2;
    @FXML
    private Button btnSongP1;
    @FXML
    private Button btnSongP2;
    @FXML
    private BorderPane firstPlayerPane;
    @FXML
    private BorderPane secondPlayerPane;
    @FXML
    private AnchorPane rootPane;

    public MultiplayerController(int height, int width, int mines, DoubleTimer timer) {
        super(height, width, mines, timer);
        this.height = height;
        this.width = width;
        this.mines = mines;
        this.timer = timer;
        this.engineP1 = new GameEngineImpl(width, height, mines);
        this.engineP2 = new GameEngineImpl(width, height, mines);
    }

    @Override
    public final void initialize() throws IOException {
        TimerView timerViewP1 = new TimerViewImpl(this.timer.getPlayer1Timer(), this.lbTimerP1);
        TimerView timerViewP2 = new TimerViewImpl(this.timer.getPlayer2Timer(), this.lbTimerP2);
        this.alert = new AlertHandlerImpl();
        this.music = new SongAgentImpl(new RWSettingsImpl());
        this.btnAction = new ButtonReactionimpl(this.rootPane);
        this.supervisorP1 = new PlayerSupervisorImpl(this.firstPlayer, true, this.playersMap);
        PlayerSupervisor supervisorP2 = new PlayerSupervisorImpl(this.secondPlayer, false, this.playersMap);
        this.scoreWriter = new ScoreWriterImpl();

        this.lbMinesP1.setText("M:" + this.mines);
        this.lbMinesP1.setText("M:" + this.mines);
        this.lbFlagP1.setText("F:" + this.mines);
        timerViewP1.startDisplaying();
        this.supervisorP1.view(this.lbNameP1);
        this.lbTimerP1.setText(String.valueOf(this.timer.getPlayer1Timer().getValue()));
        this.lbFlagP2.setText("F:" + this.mines);
        timerViewP2.startDisplaying();
        supervisorP2.view(this.lbNameP2);
        this.lbTimerP2.setText(String.valueOf(this.timer.getPlayer2Timer().getValue()));

        GridPane grid1 = new GridPane();
        final TileBuilder tb1 = new TileBuilderImpl();
        tb1.withHeight(this.height);
        tb1.withWidth(this.width);
        tb1.withGrid(grid1);
        this.tilesMap1 = tb1.build();

        GridPane grid2 = new GridPane();
        final TileBuilder tb2 = new TileBuilderImpl();
        tb2.withHeight(this.height);
        tb2.withWidth(this.width);
        tb2.withGrid(grid2);
        this.tilesMap2 = tb2.build();

        this.firstPlayerPane.setCenter(grid1);
        this.secondPlayerPane.setCenter(grid2);
        this.secondPlayerPane.setDisable(true);

        setButtons();
        setClickHandler(this.engineP1, this.tilesMap1);
        setClickHandler(this.engineP2, this.tilesMap2);

        this.ccflagsP1 = this.mines;
        this.ccflagsP2 = this.mines;
        timerViewP1.startDisplaying();
        timerViewP2.startDisplaying();
        this.music.play();
    }

    @Override
    public final void setButtons() {
        this.btnGiveUpP1.setOnAction(t -> {
            try {
                stopElements();
                if (this.btnAction.backHome()) {
                    closeElements();
                } else {
                    resumeElements();
                }
            } catch (IOException e) {
                e.printStackTrace(); // scrivi...
            }
        });
        this.btnGiveUpP2.setOnAction(t -> {
            try {
                stopElements();
                if (this.btnAction.backHome()) {
                    closeElements();
                } else {
                    resumeElements();
                }
            } catch (IOException e) {
                e.printStackTrace(); // scrivi...
            }
        });
        this.btnSongP1.setOnMouseClicked(t -> this.btnAction.checkDualMusic(this.btnSongP1, this.btnSongP2, this.music));
        this.btnSongP2.setOnMouseClicked(t -> this.btnAction.checkDualMusic(this.btnSongP1, this.btnSongP2, this.music));
    }

    @Override
    public final void leftClickHandler(final TileImpl tile, final int x, final int y) {
        this.clickCount++;
        if (!this.timer.getPlayer2Timer().isRunning()) {
            this.timer.start();
        }
        if (this.supervisorP1.isMaster()) {
            if (!tile.isFlagged()) {
                this.engineP1.hit(new Pair<>(x, y));
                refreshBoard(this.engineP1, this.tilesMap1);
            }

            if (!this.engineP1.getGameStatus().equals(GameStatus.PLAYING)) {
                endGame(this.engineP1.getGameStatus());
            }
        } else {
            if (!tile.isFlagged()) {
                this.engineP2.hit(new Pair<>(x, y));
                refreshBoard(this.engineP2, this.tilesMap2);
            }

            if (!this.engineP2.getGameStatus().equals(GameStatus.PLAYING)) {
                endGame(this.engineP2.getGameStatus());
            } else {
                if (tile.getValue() == 0) {
                    tile.audioBigClick();
                } else {
                    tile.audioClick();
                }
            }
        }

        if (this.clickCount == MAX_CLICK) {
            switchPane();
            this.clickCount = 0;
        }
    }

    @Override
    public final void rightClickHandler(final TileImpl tile, final int x, final int y) {
        if (this.supervisorP1.isMaster()) {
            if (!tile.isFlagged()) {
                this.ccflagsP1--;
            } else {
                this.ccflagsP1++;
            }
            tile.setFlag();
            if (this.ccflagsP1 >= 0 && this.ccflagsP1 < 10) {
                this.lbFlagP1.setText("F:0" + this.ccflagsP1);
            } else {
                this.lbFlagP1.setText("F:" + this.ccflagsP1);
            }
        } else {
            if (!tile.isFlagged()) {
                this.ccflagsP2--;
            } else {
                this.ccflagsP2++;
            }
            tile.setFlag();
            if ((this.ccflagsP2 >= 0) && (this.ccflagsP2 < 10)) {
                this.lbFlagP2.setText("F:0" + this.ccflagsP2);
            } else {
                this.lbFlagP2.setText("F:" + this.ccflagsP2);
            }
        }
    }

    @Override
    public final void endGame(GameStatus status) {
        closeElements();
        if (status.equals(GameStatus.LOST)) {
            writePlayer(status);
            this.alert.lost(this.supervisorP1.isMaster() ? this.firstPlayer : this.secondPlayer);
        } else if (status.equals(GameStatus.WON)) {
            writePlayer(status);
            this.alert.won(this.supervisorP1.isMaster() ? this.firstPlayer : this.secondPlayer);
        }
        try {
            backHome();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public final void writePlayer(GameStatus status) {
        switch (status) {
        case LOST:
            if (this.supervisorP1.isMaster()) {
                this.firstPlayer.ifPresent(Player::lost);
                this.secondPlayer.ifPresent(player -> player.won(this.timer.getPlayer1Timer().getValue()));
            } else {
                this.firstPlayer.ifPresent(player -> player.won(this.timer.getPlayer2Timer().getValue()));
                this.secondPlayer.ifPresent(Player::lost);
            }
            break;

        case WON:
            if (this.supervisorP1.isMaster()) {
                this.firstPlayer.ifPresent(player -> player.won(this.timer.getPlayer1Timer().getValue()));
                this.secondPlayer.ifPresent(Player::lost);
            } else {
                this.firstPlayer.ifPresent(Player::lost);
                this.secondPlayer.ifPresent(player -> player.won(this.timer.getPlayer2Timer().getValue()));
            }
            break;
        default:
            break;
        }
        this.firstPlayer.ifPresent(player -> this.scoreWriter.write(player));
        this.secondPlayer.ifPresent(player -> this.scoreWriter.write(player));
    }

    @Override
    public final void setPlayers(Optional<Player> playerP1, Optional<Player> playerP2) {
        this.firstPlayer = playerP1;
        this.secondPlayer = playerP2;
    }

    @Override
    public final String getFXML() {
        return "layouts/Multiplayer.fxml";
    }

    @Override
    public final void closeElements() {
        this.timer.stop();
        music.close();
    }

    /**
     * Stop the elements of the Game.
     *
     */
    private void stopElements() {
        if (supervisorP1.isMaster()) {
            this.timer.getPlayer1Timer().stop();
        } else {
            this.timer.getPlayer2Timer().stop();
        }
        this.music.pause();

    }

    /**
     * Resume the elements of the Game.
     *
     */
    private void resumeElements() {
        if (supervisorP1.isMaster()) {
            this.timer.getPlayer1Timer().start();
        } else {
            this.timer.getPlayer2Timer().start();
        }
        this.music.play();
    }

    /**
     * Supported with {@link PlayerSupervisor} switch the Pane.
     *
     */
    private void switchPane() {
        if (this.supervisorP1.isMaster()) {
            this.firstPlayerPane.setDisable(true);
            this.secondPlayerPane.setDisable(false);
        } else {
            this.firstPlayerPane.setDisable(false);
            this.secondPlayerPane.setDisable(true);
        }
        this.supervisorP1.giveMaster(this.playersMap);
        this.timer.switchTurn();
    }

}
