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
public class MultiplayerController extends AbstractGameController{
    private final int height;
    private final int width;
    private final int mines;
    private final GameEngine engineP1;
    private final GameEngine engineP2;
    private final DoubleTimer timer;
    private Map<Pair<Integer, Integer>, Tile> tilesMap1;
    private Map<Pair<Integer, Integer>, Tile> tilesMap2;
    private Optional<Player> firstplayer;
    private Optional<Player> secondplayer;
    private TimerView timerViewP1;
    private TimerView timerViewP2;
    private AlertHandler alert;
    private SongAgent music;
    private ButtonReaction btnAction;
    private int clickCount = 0;
    private static final int MAX_CLICK = 1;
    private PlayerSupervisor supervisorP1;
    private PlayerSupervisor supervisorP2;
    final HashMap<PlayerSupervisor,Boolean> playermap = new HashMap<>();
    private int ccflagsP1;
    private int ccflagsP2;
    private Boolean firstclick = false;


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
    private Label lbMines;
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
        super(height,width,mines,timer);
        this.height = height;
        this.width = width;
        this.mines = mines;
        this.timer = timer;
        this.engineP1 = new GameEngineImpl(width,height,mines);
        this.engineP2 = new GameEngineImpl(width,height,mines);
    }

    @Override
    public void initialize() throws IOException{
        this.timerViewP1 = new TimerViewImpl(timer.getPlayer1Timer(),lbTimerP1);
        this.timerViewP2 = new TimerViewImpl(timer.getPlayer2Timer(),lbTimerP2);
        this.alert = new AlertHandlerImpl();
        this.music = new SongAgentImpl(new RWSettingsImpl());
        this.btnAction = new ButtonReactionimpl(this.rootPane);
        this.supervisorP1 = new PlayerSupervisorImpl(this.firstplayer,true,this.playermap);
        this.supervisorP2 = new PlayerSupervisorImpl(this.secondplayer,false,this.playermap);

        lbMines.setText("M:" + this.mines);
        lbFlagP1.setText("F:" + this.mines);
        this.supervisorP1.view(lbNameP1);
        lbTimerP1.setText(String.valueOf(timer.getPlayer1Timer().getValue()));
        lbFlagP2.setText("F:" + this.mines);
        this.supervisorP2.view(lbNameP2);
        lbTimerP2.setText(String.valueOf(timer.getPlayer2Timer().getValue()));
        this.ccflagsP1 = this.mines;
        this.ccflagsP2 = this.mines;
        timerViewP1.startDisplaying();
        timerViewP2.startDisplaying();

        GridPane grid1 = new GridPane();
        final TileBuilder tb1 = new TileBuilderImpl();
        tb1.withHeight(height);
        tb1.withWidth(width);
        tb1.withGrid(grid1);
        this.tilesMap1 = tb1.build();

        GridPane grid2 = new GridPane();
        final TileBuilder tb2 = new TileBuilderImpl();
        tb2.withHeight(height);
        tb2.withWidth(width);
        tb2.withGrid(grid2);
        this.tilesMap2 = tb2.build();

        this.firstPlayerPane.setCenter(grid1);
        this.secondPlayerPane.setCenter(grid2);

        this.secondPlayerPane.setDisable(true);
        this.music.play();

        setButtons();
        setClickHandler(this.engineP1,this.tilesMap1);
        setClickHandler(this.engineP2,this.tilesMap2);
    }

    @Override
    public void setButtons() {
        this.btnGiveUpP1.setOnAction(t -> {
            try {
                stopElements();
                if(this.btnAction.backHome()) {
                    closeElements();
                } else {
                resumeElements();
                }
            } catch (IOException e) {
                e.printStackTrace(); //scrivi...
            }
        });
        this.btnGiveUpP2.setOnAction(t -> {
            try {
                stopElements();
                if(this.btnAction.backHome()) {
                    closeElements();
                } else {
                    resumeElements();
                }
            } catch (IOException e) {
                e.printStackTrace(); //scrivi...
            }
        });
        this.btnSongP1.setOnMouseClicked(t -> this.btnAction.checkDualMusic(this.btnSongP1,this.btnSongP2,this.music));
        this.btnSongP2.setOnMouseClicked(t -> this.btnAction.checkDualMusic(this.btnSongP1,this.btnSongP2,this.music));
    }

    @Override
    public void leftClickHandler(final Tile tile, final int x, final int y){
        this.clickCount++;
        if (!this.timer.getPlayer2Timer().isRunning()) {
            timer.start();
        }
        tile.clipAudioClick();
        if(this.supervisorP1.isMaster()) {
            if (!tile.isFlagged()) {
                this.engineP1.hit(new Pair<>(x,y));
                refreshBoard(this.engineP1,this.tilesMap1);
            }

            if (!this.engineP1.getGameStatus().equals((GameStatus.PLAYING))) {
                endGame(this.engineP1.getGameStatus());
            }
        } else {
            if (!tile.isFlagged()) {
                this.engineP2.hit(new Pair<>(x,y));
                refreshBoard(this.engineP2,this.tilesMap2);
            }

            if (!this.engineP2.getGameStatus().equals((GameStatus.PLAYING))) {
                endGame(this.engineP2.getGameStatus());
            }
        }

        if(this.clickCount == MAX_CLICK) {
            switchPane();
            this.clickCount = 0;
        }
    }

    public void rightClickHandler(final Tile tile, final int x, final int y,final GameEngine engine) {
        engine.setFlag(new Pair<>(x, y));
        if (this.supervisorP1.isMaster()) {
            if (!tile.isFlagged()) {
                this.ccflagsP1--;
            } else {
                this.ccflagsP1++;
            }
            tile.setflag();
            this.lbFlagP1.setText("F:" + this.ccflagsP1);
        } else {
            if (!tile.isFlagged()) {
                this.ccflagsP2--;
            } else {
                this.ccflagsP2++;
            }
            tile.setflag();
            this.lbFlagP2.setText("F:" + this.ccflagsP2);
        }
    }

    @Override
    public void endGame(GameStatus gameStatus){
        closeElements();
        if (gameStatus.equals(GameStatus.LOST)) {
            writePlayer(GameStatus.LOST);
            if(supervisorP1.isMaster()) {
                alert.lost(this.firstplayer);
            } else {
                alert.lost(this.secondplayer);
            }
            try {
                backHome();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (gameStatus.equals(GameStatus.WON)) {
            writePlayer(GameStatus.WON);
            if(this.supervisorP1.isMaster()) {
                alert.won(this.firstplayer);
            } else {
                alert.won(this.secondplayer);
            }
            try {
                backHome();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public void writePlayer(GameStatus status) {
        if (this.firstplayer.isPresent()) {
            if (status.equals(GameStatus.LOST)) {
                if (this.supervisorP1.isMaster()) {
                    this.supervisorP1.writePlayer(status);
                } else {
                    this.supervisorP2.writePlayer(status);
                }
            } else {
                if(this.supervisorP1.isMaster()) {
                    this.supervisorP1.writePlayer(status);
                } else {
                    this.supervisorP2.writePlayer(status);
                }
            }
        }
    }

    @Override
    public void setPlayers(Optional<Player> firstplayer, Optional<Player> secondplayer) {
        this.firstplayer = firstplayer;
        this.secondplayer = secondplayer;
    }

    private void switchPane() {
        if(this.supervisorP1.isMaster()) {
            this.firstPlayerPane.setDisable(true);
            this.secondPlayerPane.setDisable(false);
        } else {
            this.firstPlayerPane.setDisable(false);
            this.secondPlayerPane.setDisable(true);
        }
        supervisorP1.giveMaster();
        timer.switchTurn();

    }

    public String getFXML() {
        return "layouts/Multiplayer.fxml";
    }

    @Override
    public void closeElements() {
        this.timer.stop();
        music.close();
    }

    private void stopElements() {
        if(supervisorP1.isMaster()){
            this.timer.getPlayer1Timer().stop();
        } else {
            this.timer.getPlayer2Timer().stop();
        }
        this.music.pause();

    }

    private void resumeElements() {
        if(supervisorP1.isMaster()){
            this.timer.getPlayer1Timer().start();
        } else {
            this.timer.getPlayer2Timer().start();
        }
        this.music.play();
    }

}
