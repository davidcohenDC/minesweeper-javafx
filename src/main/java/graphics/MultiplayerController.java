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
    private ScoreWriter scoreWriter;
    private AlertHandler alert;
    private SongAgent music;
    private ButtonReaction btnAction;
    private Boolean firstClick = false;
    private Boolean whoPlay = true;
    private int clickCount = 0;
    private static final int MAX_CLICK = 1;

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
        this.scoreWriter = new ScoreWriterImpl();
        this.alert = new AlertHandlerImpl();
        this.music = new SongAgentImpl(new RWSettingsImpl());
        this.btnAction = new ButtonReactionimpl(this.rootPane);

        lbFlagP1.setText("F:" + this.mines);
        if(this.firstplayer.isPresent())
            lbNameP1.setText(this.firstplayer.get().getName());
        else
            lbNameP1.setText("No One");
        lbTimerP1.setText(String.valueOf(timer.getPlayer1Timer().getValue()));
        lbFlagP2.setText("F:" + this.mines);
        if(this.firstplayer.isPresent())
            lbNameP2.setText(this.firstplayer.get().getAdversary().get());
        else
            lbNameP2.setText("No One");
        lbTimerP2.setText(String.valueOf(timer.getPlayer2Timer().getValue()));

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
        btnGiveUpP1.setOnAction(t -> {
            try {
                music.close();
                this.timer.stop();
                alert.lost();
                btnAction.backHome();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btnGiveUpP2.setOnAction(t -> {
            try {
                music.close();
                this.timer.stop();
                btnAction.restartGame(this);
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
            timer.start();
            timerViewP2.startDisplaying();
        }
        tile.clipAudioClick();
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

        if(this.clickCount == MAX_CLICK) {
            switchPane();
            this.clickCount = 0;
        }
    }

    @Override
    public void endGame(GameStatus gameStatus) {
        closeElements();
        if (gameStatus.equals(GameStatus.LOST)) {
            writePlayer(GameStatus.LOST);
            if(this.whoPlay) {
                alert.lost();//...
            } else {
                alert.lost();//...
            }
            try {
                btnAction.backHome();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (gameStatus.equals(GameStatus.WON)) {
            writePlayer(GameStatus.WON);
            if(this.whoPlay) {
                alert.wonWithPlayer(this.firstplayer);
            } else {
                alert.wonWithPlayer(this.secondplayer);
            }
            try {
                btnAction.backHome();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void closeElements() {
        this.timer.stop();
        music.close();
    }


    @Override
    public void writePlayer(GameStatus status) {
        System.out.println(this.firstplayer.isPresent());
        System.out.println(this.secondplayer.isPresent());
        if (this.firstplayer.isPresent()) {
            if (status.equals(GameStatus.LOST)) {
                if (whoPlay) {
                    this.firstplayer.get().lost();
                } else {
                    this.firstplayer.get().won((int) this.timer.getPlayer1Timer().getValue());
                }
            }
            this.scoreWriter.write(this.firstplayer.get());
        }
            if (this.secondplayer.isPresent()) {
                if (status.equals(GameStatus.LOST)) {
                    if (whoPlay) {
                        this.secondplayer.get().lost();
                    } else {
                        this.secondplayer.get().won((int) this.timer.getPlayer2Timer().getValue());
                    }
                }
                this.scoreWriter.write(this.secondplayer.get());
            }
    }

    @Override
    public void setPlayers(Optional<Player> firstplayer, Optional<Player> secondplayer) {
        this.firstplayer = firstplayer;
        this.secondplayer = secondplayer;
    }

    private void switchPane() {
        if(whoPlay) {
            this.firstPlayerPane.setDisable(true);
            this.secondPlayerPane.setDisable(false);
            this.whoPlay = false;
            timer.switchTurn();
        } else {
            this.firstPlayerPane.setDisable(false);
            this.secondPlayerPane.setDisable(true);
            this.whoPlay = true;
            timer.switchTurn();
        }

    }

    public String getFXML() {
        final String layout = "layouts/Multiplayer.fxml";
        return layout;
    }

}
