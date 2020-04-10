package graphics;

import controlutility.AlertStyle;
import controlutility.AlertStyleImpl;
import controlutility.RWSettings;
import controlutility.RWSettingsImpl;
import gamelogics.*;
import gamelogics.Box;
import graphicsutility.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import scoresystem.Player;
import scoresystem.ScoreWriter;
import scoresystem.ScoreWriterImpl;
import timer.*;
import timer.Timer;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 * The Controller related to the playGame.fxml GUI.
 *
 */
public class SinglePlayerController implements GameController {
    private final GridPane grid = new GridPane();
    private int mines;
    private int height;
    private int width;
    private int ccflags;
    private Boolean firstClick = false;
    private GameEngine engine;
    private final Map<Pair<Integer,Integer>, Tile> tilesMap;
    private final TimerFactory timerFactory = new TimerFactoryImpl();
    private final Timer timer;
    private Optional<Player> player;
    private SongAgent music;
    private ButtonReaction btnAction;
    private TimerView timerView;
    private ScoreWriter scoreWriter;
    private NodeEffectImpl effect;
    private AlertHandler alert;

    @FXML
    private Label lbFlags;
    @FXML
    private Label lbMines;
    @FXML
    private Label lbTimer = new Label();
    @FXML
    private Button btnRestart;
    @FXML
    private Button btnBackHome;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private Button btnSong;

    public SinglePlayerController(final int height, final int width, final int mines, final Timer timer){
        this.height = height;
        this.width = width;
        this.mines = mines;
        this.timer = timer;
        final TileBuilder tb = new TileBuilderImpl();
        tb.withHeight(height);
        tb.withWidth(width);
        tb.withGrid(grid);
        this.tilesMap = tb.build();
    }

    @Override
    public void setPlayer(Optional<Player> player) {
        this.player = player;
    }

    public void initialize() throws IOException{
        RWSettings rwSett = new RWSettingsImpl();
        this.alert = new AlertHandlerImpl();
        this.music = new SongAgentImpl(rwSett);
        this.engine = new GameEngineImpl(width,height,mines);
        this.scoreWriter = new ScoreWriterImpl();
        this.btnAction = new ButtonReactionimpl(this.rootPane);
        this.timerView = new TimerViewImpl(timer, lbTimer);
        this.effect = new NodeEffectImpl();

        this.ccflags = this.mines;
        this.mainBorderPane.setCenter(grid);

        lbFlags.setText("Flags:" + this.ccflags);
        lbMines.setText("Mines:" + mines);
        lbTimer.setText(String.valueOf(timer.getValue()));
        btnSong.setText("MUTE");

        this.music.play();
        btnActions();
        setClickHandler();
    }

    private void btnActions() {
        btnBackHome.setOnAction(t -> {
            try {
                btnAction.backHome();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btnRestart.setOnAction(t -> {
            try {
                btnAction.restartGame();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        btnSong.setOnMouseClicked(t -> music.checkSong(btnSong));
    }

    private void setClickHandler() {

        for (final Box box : this.engine.getBoard()) {
            final Tile tmpTile = this.tilesMap.get(box.getPosition());
            tmpTile.setOnMouseClicked(e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    leftClickHandler(tmpTile, tmpTile.getX(), tmpTile.getY());
                } else if (e.getButton() == MouseButton.SECONDARY) {
                    rightClickHandler(tmpTile, tmpTile.getX(), tmpTile.getY());
                }
            });
        }
    }

    private void leftClickHandler(final Tile tile, final int x, final int y) {

        if(!this.firstClick) {
            this.firstClick = true;
            timerView.startDisplaying();
            timer.start();
        }

        if(!tile.isFlagged()) {
            this.engine.hit(new Pair<>(x,y));
            refreshBoard();

        }

        if(!this.engine.getGameStatus().equals((GameStatus.PLAYING))) {
            endGame();
        }
    }

    private void rightClickHandler(final Tile tile, final int x, final int y) {

        this.engine.setFlag(new Pair<>(x,y));

        if(!tile.isFlagged()) {
            this.ccflags--;
            tile.setflag();
            this.lbFlags.setText("FLags:" + this.ccflags);
        }
        else {
            this.ccflags++;
            tile.setflag();
            this.lbFlags.setText("FLags:" + this.ccflags);
        }
    }

    private void refreshBoard() {
        for (final Box box : this.engine.getBoard()) {
            final Tile tmpTile = this.tilesMap.get(box.getPosition());
            if (box.isClicked()) {
                if (box.containsBomb()) {
                    tmpTile.setMine();
                } else {
                    tmpTile.setValue(box.getBombNear());
                    tmpTile.disable();
                }
            }
            if (this.engine.getGameStatus().equals((GameStatus.LOST))) {
                if (box.containsBomb()) {
                    tmpTile.setMine();

                } else {
                    effect.fallingTiles(tmpTile);
                }
            }
        }
    }

    private void endGame() {
        if(this.engine.getGameStatus().equals(GameStatus.LOST)) {
            alert.lost();
            setPlayer(GameStatus.LOST);
            closeElements();
            try {
                btnAction.backHome();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if(this.engine.getGameStatus().equals(GameStatus.WON)) {
            alert.won();
            setPlayer(GameStatus.WON);
            closeElements();
            try {
                btnAction.backHome();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void setPlayer(final GameStatus status) {
            if(this.player.isPresent()) {
                if (status.equals(GameStatus.LOST)) {
                    this.player.get().lost();
                } else {
                    this.player.get().won((int) this.timer.getValue());
                }
                this.player.ifPresent(scoreWriter::write);
            }
    }

    private void closeElements() {
        timer.stop();
        music.stop();
    }





}