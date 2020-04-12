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
    private Map<Pair<Integer, Integer>, Tile> tilesMap;
    private Optional<Player> player;
    private TimerView timerView;
    private ScoreWriter scoreWriter;
    private AlertHandler alert;
    private SongAgent music;
    private ButtonReaction btnAction;
    private Boolean timerOver = false;
    private Boolean firstClick = false;

    @FXML
    private Label lbTimer = new Label();
    @FXML
    private Label lbFlags;
    @FXML
    private Label lbMines;
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
        super(height,width,mines,timer);
        this.height = height;
        this.width = width;
        this.mines = mines;
        this.timer = timer;
        this.engine = new GameEngineImpl(width,height,mines);
    }

    @Override
    public void initialize() throws IOException {
        this.timerView = new TimerViewImpl(timer, lbTimer);
        this.scoreWriter = new ScoreWriterImpl();
        this.alert = new AlertHandlerImpl();
        this.music = new SongAgentImpl(new RWSettingsImpl());
        this.btnAction = new ButtonReactionimpl(this.rootPane);

        lbFlags.setText("Flags:" + this.mines);
        lbMines.setText("Mines:" + this.mines);
        lbTimer.setText(String.valueOf(timer.getValue()));
        btnSong.setText("MUTE");

        GridPane grid = new GridPane();
        final TileBuilder tb = new TileBuilderImpl();
        tb.withHeight(height);
        tb.withWidth(width);
        tb.withGrid(grid);
        this.tilesMap = tb.build();

        this.mainBorderPane.setCenter(grid);
        this.music.play();

        setButtons();
        setClickHandler(this.engine,this.tilesMap);

    }

    @Override
    public void setButtons() {
        btnBackHome.setOnAction(t -> {
            try {
                music.close();
                timer.stop();
                btnAction.backHome();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btnRestart.setOnAction(t -> {
            try {
                music.close();
                timer.stop();
                btnAction.restartGame();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        btnSong.setOnMouseClicked(t -> music.checkSong(btnSong));
    }

    @Override
    public void leftClickHandler(final Tile tile, final int x, final int y) {
        if (!this.firstClick) {
            timerView.setTimeEventListener(new TimeEventsListenerImpl(this));
            timerView.startDisplaying();
            timer.start();
        }

        if (!tile.isFlagged()) {
            this.engine.hit(new Pair<>(x,y));
            refreshBoard(this.engine,this.tilesMap);

        }

        if (!this.engine.getGameStatus().equals((GameStatus.PLAYING))) {
            endGame(this.engine.getGameStatus());
        }
    }

    @Override
    public void rightClickHandler(final Tile tile, final int x, final int y) {
        this.engine.setFlag(new Pair<>(x, y));

        if (!tile.isFlagged()) {
            this.ccflagsP1--;
        } else {
            this.ccflagsP1++;
        }
        tile.setflag();
        this.lbFlags.setText("F:" + this.ccflagsP1);
    }

    @Override
    public void endGame(GameStatus gameStatus) {
        if (gameStatus.equals(GameStatus.LOST)) {
            closeElements();
            if(this.timerOver) {
                alert.lostWithTimer();
            } else {
                alert.lost();
            }
            writePlayer(GameStatus.LOST);
            try {
                btnAction.backHome();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (gameStatus.equals(GameStatus.WON)) {
            alert.won();
            writePlayer(GameStatus.WON);
            closeElements();
            try {
                btnAction.backHome();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void closeElements() {
        timer.stop();
        music.close();
    }

    @Override
    public void writePlayer(GameStatus status) {
        if (this.player.isPresent()) {
            if (status.equals(GameStatus.LOST)) {
                this.player.get().lost();
            } else {
                this.player.get().won((int) this.timer.getValue());
            }
            scoreWriter.write(this.player.get());
        }
    }

    @Override
    public void setPlayer(Optional<Player> player) {
        this.player = player;
    }

    void endTimer(GameStatus gameStatus) {
        this.timerOver= true;
        endGame(GameStatus.LOST);
    }



}
