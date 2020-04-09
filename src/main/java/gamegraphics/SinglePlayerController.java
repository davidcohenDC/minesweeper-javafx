package gamegraphics;

import controllers.BackHomeController;
import controlutility.AlertStyle;
import controlutility.AlertStyleImpl;
import controlutility.RWSettings;
import controlutility.RWSettingsImpl;
import gamelogics.*;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;
import scoresystem.Player;
import scoresystem.ScoreWriter;
import scoresystem.ScoreWriterImpl;
import timer.*;
import timer.Timer;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * The Controller related to the playGame.fxml GUI.
 *
 */
public class SinglePlayerController implements ModalityController {
    //
    private int mines;
    private int ccflags = 0;
    private int height;
    private int width;
    private Boolean firstClick = false;
    private final GridPane grid = new GridPane();
    private AlertStyle alStyle;
    private Clip clip;
    private RWSettings rwSett;
    private GameEngine engine;
    private final Map<Pair<Integer,Integer>,Tile> tilesMap;
    private final TimerFactory timerFactory = new TimerFactoryImpl();
    private final Timer timer;


    //private AlertStyle alStyle;
    //prelevare con alert una stringa





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


    private Optional<Player> player;


    public SinglePlayerController(final int height, final int width, final int mines, final Timer timer) {
        this.height = height;
        this.width = width;
        this.mines = mines;
        this.timer = timer;

        grid.setStyle(" -fx-grid-lines-visible: true; -fx-grid-border-style: solid inside;");
        this.engine = new GameEngineImpl(width,height,mines);
        this.tilesMap = new HashMap<>();


    }

    @Override
    public void initialize() throws IOException {
        this.alStyle = new AlertStyleImpl();
        this.rwSett = new RWSettingsImpl();
        setLabel();
        //setupTimer();
        buildTiles();
        btnBackHome.setOnMouseClicked(t -> {
            try {
                backHome();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btnRestart.setOnAction(t -> restart());

    }

    @Override
    public void setPlayer(Optional<Player> player) {
        this.player = player;

    }

    private void setLabel() {
        lbFlags.setText("Flags:" + 0);
        lbMines.setText("Mines:" + mines);
        lbTimer.setText(String.valueOf(timer.getValue()));
    }

    public void setupTimer() {
        final TimerView timerView = new TimerViewImpl(timer, lbTimer);
        timerView.startDisplaying();
        timer.start();
    }

    private void buildTiles() {
        IntStream.range(0, this.height)
                .forEach(r -> IntStream.range(0, this.width).forEach(c -> grid.add(createTile(r,c), c, r)));
        grid.setAlignment(Pos.CENTER);
        this.mainBorderPane.setCenter(grid);
    }

    protected Tile createTile(final int x, final int y) {
        final Tile tile;

        try {
            tile = new Tile(x,y);
            this.tilesMap.put(new Pair<>(x, y),tile);
            tile.setOnMouseClicked(e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    leftClickHandler(tile,x,y);
                } else if (e.getButton() == MouseButton.SECONDARY){
                    rightClickHandler(tile,x,y);
                }
            });
            return tile;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not create tile correctly");
        }

    }

    private void leftClickHandler(final Tile tile,final int x, final int y) {

        if(!this.firstClick) {
            this.firstClick = true;
            setupTimer();
        }
        if(!tile.isFlagged()) {
            this.engine.hit(new Pair<>(x,y));
            refreshBoard();

        }
        if(!this.engine.getGameStatus().equals((GameStatus.PLAYING))) {
            endGame();
        }

    }

    private void endGame() {
        if(this.engine.getGameStatus().equals(GameStatus.LOST)) {
            //lost(); ,alert
            if(this.player.isPresent()) {
                this.player.get().lost();
                writePLayerScore();
            }
            System.out.println("Lost");
            //ShowBombs();
        } else if(this.engine.getGameStatus().equals(GameStatus.WON)) {
            //Won();
            if(this.player.isPresent()) {
                this.player.get().won((int) this.timer.getValue());
                writePLayerScore();
            }
            System.out.println("Won");

        }

    }

    private void writePLayerScore() {
        final ScoreWriter scoreWriter = new ScoreWriterImpl();
        scoreWriter.write(this.player.get());
    }

    protected void rightClickHandler(final Tile tile, final int x, final int y) {

        this.engine.setFlag(new Pair<>(x,y));

        if(!tile.isFlagged()) {
                this.ccflags++;
                tile.flag();
                this.lbFlags.setText("FLags:" + this.ccflags);
            }
            else {
                this.ccflags--;
                tile.flag();
                this.lbFlags.setText("FLags:" + this.ccflags);
            }
    }

    private void tileEffect(final Tile tile) {

        FadeTransition fade = new FadeTransition();
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setDuration(Duration.millis(300));
        fade.setNode(tile);

        TranslateTransition transition = new TranslateTransition();
        transition.setByY(100);
        transition.setDuration(Duration.millis(1000));
        transition.setNode(tile);
        transition.play();
        fade.play();


    }

    private void closeGame() {
        clip.stop();
        clip.close();
        timer.stop();
    }

    public void backHome() throws IOException {
        final RWSettings rwSett = new RWSettingsImpl();
        final Parent pane = FXMLLoader.load(ClassLoader.getSystemResource("layouts/playGame.fxml"));
        final Stage stage = (Stage) this.rootPane.getScene().getWindow();
        final Scene scene = new Scene(pane, stage.getScene().getWidth(), stage.getScene().getHeight());
        scene.getStylesheets().add(ClassLoader.getSystemResource("css/" + rwSett.getCss()).toExternalForm());
        stage.setScene(scene);
    }

    public void restart() {
        timer.stop();
        clip.stop();
        //ricreare la scena

        buildTiles();
    }


   private void refreshBoard() {
        for(final Box box : this.engine.getBoard()) {
            if(box.isClicked()) {
                final Tile tmpTile = this.tilesMap.get(box.getPosition());
                tmpTile.setValue(box.getBombNear());
                //tileEffect(tmpTile);
                tmpTile.disable();
            }
        }
    }


}