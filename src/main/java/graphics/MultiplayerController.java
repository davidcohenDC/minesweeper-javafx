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
import timer.Timer;
import timer.TimerView;
import timer.TimerViewImpl;
import java.io.IOException;
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
    private final Timer timerP1;
    private final Timer timerP2;
    private Map<Pair<Integer, Integer>, Tile> tilesMap1;
    private Map<Pair<Integer, Integer>, Tile> tilesMap2;
    private Optional<Player> player;
    private TimerView timerViewP1;
    private TimerView timerViewP2;
    private ScoreWriter scoreWriter;
    private AlertHandler alert;
    private SongAgent music;
    private ButtonReaction btnAction;
    private Boolean firstClick = false;
    private Boolean whoPlay = true;
    private int clickCount = 0;
    private int maxClick;

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
    private Button btnGiveUpP1;
    @FXML
    private Button btnGiveUpP2;
    @FXML
    private BorderPane firstPlayerPane;
    @FXML
    private BorderPane secondPlayerPane;
    @FXML
    private AnchorPane rootPane;

    public MultiplayerController(int height, int width, int mines, Timer timer) {
        super(height,width,mines,timer);
        this.height = height;
        this.width = width;
        this.mines = mines;
        this.timerP1 = timer;
        this.timerP2 = timer;
        this.engineP1 = new GameEngineImpl(width,height,mines);
        this.engineP2 = new GameEngineImpl(width,height,mines);
    }

    @Override
    public void initialize() throws IOException{
        this.timerViewP1 = new TimerViewImpl(timerP1,lbTimerP1);
        this.timerViewP2 = new TimerViewImpl(timerP2,lbTimerP2);
        this.scoreWriter = new ScoreWriterImpl();
        this.alert = new AlertHandlerImpl();
        this.music = new SongAgentImpl(new RWSettingsImpl());
        this.btnAction = new ButtonReactionimpl(this.rootPane);

        lbFlagP1.setText("F:" + this.mines);
        lbNameP1.setText(this.player.get().getName());
        lbTimerP1.setText(String.valueOf(timerP1.getValue()));
        lbFlagP2.setText("F:" + this.mines);
        lbNameP2.setText(this.player.get().getAdversary().get());
        lbTimerP2.setText(String.valueOf(timerP2.getValue()));

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
        this.maxClick = 2;
        this.music.play();

        setButtons();
        setClickHandler(this.engineP1,this.tilesMap1);
        setClickHandler(this.engineP2,this.tilesMap2);
    }

    @Override
    public void setButtons() {
        btnGiveUpP1.setOnAction(t -> {
            try {
                music.close();
                timerP1.stop();
                timerP2.stop();
                btnAction.backHome();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btnGiveUpP2.setOnAction(t -> {
            try {
                music.close();
                timerP1.stop();
                timerP2.stop();
                btnAction.backHome();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void leftClickHandler(final Tile tile, final int x, final int y) {
        this.clickCount++;
        if (!this.firstClick) {
            this.firstClick = true;
            timerViewP1.startDisplaying();
            timerP1.start();
            timerViewP2.startDisplaying();
        }

        if(whoPlay) {
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

        if(this.clickCount == this.maxClick) {
            switchPane();
            this.clickCount = 0;
        }
    }

    @Override
    public void rightClickHandler(Tile tile, int x, int y) {
        this.engineP1.setFlag(new Pair<>(x, y));

        if (whoPlay) {
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
            this.lbFlagP1.setText("F:" + this.ccflagsP2);
        }
    }

    @Override
    public void endGame(GameStatus gameStatus) {
        if (gameStatus.equals(GameStatus.LOST)) {
            closeElements();
            if(this.whoPlay) {
                alert.lost();//...
            } else {
                alert.lost();//...
            }
            writePlayer(GameStatus.LOST);
            try {
                btnAction.backHome();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (gameStatus.equals(GameStatus.WON)) {
            if(this.whoPlay) {
                alert.won();//...
            } else {
                alert.won();//...
            }
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
        timerP1.stop();
        timerP2.stop();
        music.close();
    }

    @Override
    public void writePlayer(GameStatus status) {
        if (this.player.isPresent()) {
            if (status.equals(GameStatus.LOST)) {
                this.player.get().lost();
            } else {
                this.player.get().won((int) this.timerP1.getValue());
            }
            if(this.whoPlay) {
                this.scoreWriter.write(this.player.get());
            }else {
                this.scoreWriter.write(this.player.get());
            }
        }
    }

    @Override
    public void setPlayer(Optional<Player> player) {
        this.player = player;
    }

    private void switchPane() {
        if(whoPlay) {
            this.firstPlayerPane.setDisable(true);
            this.secondPlayerPane.setDisable(false);
            this.whoPlay = false;
            timerViewP1.stopDisplaying();
            timerViewP2.startDisplaying();
            timerP1.stop();
            timerP2.start();
        } else {
            this.firstPlayerPane.setDisable(false);
            this.secondPlayerPane.setDisable(true);
            this.whoPlay = true;
            timerViewP1.startDisplaying();
            timerViewP2.stopDisplaying();
            timerP2.stop();
            timerP1.start();
        }

    }
}
