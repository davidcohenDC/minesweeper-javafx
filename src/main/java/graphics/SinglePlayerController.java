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
    private Map<Pair<Integer, Integer>, Tile> tilesMap;
    private HashMap<PlayerSupervisor,Boolean> playermap;
    private Optional<Player> firstplayer;
    private Optional<Player> secondplayer;
    private TimerView timerView;
    private AlertHandler alert;
    private SongAgent music;
    private ButtonReaction btnAction;
    private Boolean timerOver = false;
    private PlayerSupervisor supervisorP1;
    private int ccflagsP1;

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
        super(height,width,mines,timer);
        this.height = height;
        this.width = width;
        this.mines = mines;
        this.timer = timer;
        this.engine = new GameEngineImpl(width,height,mines);
    }

    @Override
    public void initialize() throws IOException {
        Implementations();

        lbFlagP1.setText("FLAGS:" + this.mines);
        lbMinesP1.setText("MINE:" + this.mines);
        this.supervisorP1.view(lbNameP1);
        lbTimerP1.setText(String.valueOf(timer.getValue()));
        btnSong.setText("MUTE");
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
        setClickHandler(this.engine,this.tilesMap);
    }

    private void Implementations() throws IOException{
        this.playermap = new HashMap<>();
        this.timerView = new TimerViewImpl(timer, lbTimerP1);
        this.alert = new AlertHandlerImpl();
        this.music = new SongAgentImpl(new RWSettingsImpl());
        this.btnAction = new ButtonReactionimpl(this.rootPane);
        this.supervisorP1 = new PlayerSupervisorImpl(this.firstplayer,true,this.playermap);
    }

    @Override
    public void setButtons() {
        this.btnBackHome.setOnAction(t -> {
            try {
                this.music.pause();
                this.timer.stop();
                if(this.btnAction.backHome()) {
                    closeElements();
                } else {
                    this.music.play();
                    this.timer.start();
                }
            } catch (IOException e) {
                e.printStackTrace(); //scrivi...
            }
        });
       this.btnSong.setOnMouseClicked(t -> btnAction.checkMusic(this.btnSong,this.music));
    }

    @Override
    public void leftClickHandler(final Tile tile, final int x, final int y) {
        if (!this.timer.isRunning()) {
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
        } else {
            if (tile.getValue() == 0) {
                tile.clipBigClick();
            } else {
                tile.clipAudioClick();
            }
        }
    }

    public void rightClickHandler(final Tile tile, final int x, final int y,final GameEngine engine) {
        engine.setFlag(new Pair<>(x, y));
        if (!tile.isFlagged()) {
            this.ccflagsP1--;
        } else {
            this.ccflagsP1++;
        }
        tile.setflag();
        this.lbFlagP1.setText("F:" + this.ccflagsP1);
    }

    @Override
    public void endGame(GameStatus gameStatus) {
        closeElements();
        if (gameStatus.equals(GameStatus.LOST)) {
            writePlayer(GameStatus.LOST);
            if(this.timerOver) {
                alert.lostWithTimer();
            } else {
                alert.lost(this.firstplayer);
            }
            try {
                backHome();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (gameStatus.equals(GameStatus.WON)) {
            writePlayer(GameStatus.WON);
            alert.won(this.firstplayer);
            try {
                backHome();
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
        this.supervisorP1.writePlayer(status);
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
        return "layouts/SinglePlayer.fxml";
    }






}
