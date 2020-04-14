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
    private Optional<Player> firstplayer;
    private Optional<Player> secondplayer;
    private TimerView timerView;
    private ScoreWriter scoreWriter;
    private AlertHandler alert;
    private SongAgent music;
    private ButtonReaction btnAction;
    private Boolean timerOver = false;
    private Boolean firstClick = false;

    @FXML
    private Label lbTimerP1 = new Label();
    @FXML
    private Label lbFlagP1;
    @FXML
    private Label lbNameP1;
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
        this.timerView = new TimerViewImpl(timer, lbTimerP1);
        this.scoreWriter = new ScoreWriterImpl();
        this.alert = new AlertHandlerImpl();
        this.music = new SongAgentImpl(new RWSettingsImpl());
        this.btnAction = new ButtonReactionimpl(this.rootPane);

        lbFlagP1.setText("Flags:" + this.mines);
        lbNameP1.setText("No One");
        if(this.firstplayer.isPresent()) {
            lbNameP1.setText(this.firstplayer.get().getName());
        }
        else {
            lbNameP1.setText("No One");
        }
        lbTimerP1.setText(String.valueOf(timer.getValue()));
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
                timer.start();
                btnAction.restartGame(this);
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

        System.out.println(engine.getGameStatus());
        if (!this.engine.getGameStatus().equals((GameStatus.PLAYING))) {
            endGame(this.engine.getGameStatus());
        } else {
            if (tile.getValue() == 0) {
                tile.clipBigClick();
            } else {
                tile.clipAudioClick();
            }
        }
    }

    @Override
    public void endGame(GameStatus gameStatus) {
        closeElements();
        if (gameStatus.equals(GameStatus.LOST)) {
            writePlayer(GameStatus.LOST);
            if(this.timerOver) {
                alert.lostWithTimer();
            } else {
                alert.lost();
            }
            try {
                btnAction.backHome();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (gameStatus.equals(GameStatus.WON)) {
            writePlayer(GameStatus.WON);
            alert.wonWithPlayer(this.firstplayer);
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
        if (this.firstplayer.isPresent()) {
            if (status.equals(GameStatus.LOST)) {
                this.firstplayer.get().lost();
            } else {
                this.firstplayer.get().won(timer.getValue());
                System.out.println(this.firstplayer.get().getScore());
            }
            scoreWriter.write(this.firstplayer.get());
        }
    }
    @Override
    public void setPlayers(Optional<Player> firstplayer, Optional<Player> secondplayer) {
        this.firstplayer = firstplayer;
        this.secondplayer = secondplayer;
    }

    void endTimer() {
        this.timerOver= true;
        endGame(GameStatus.LOST);
    }

    public String getFXML() {
        final String layout = "layouts/SinglePlayer.fxml";
        return layout;
    }






}
