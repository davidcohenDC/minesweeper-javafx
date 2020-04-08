package gamegraphics;

import controllers.BackHomeController;
import controlutility.AlertStyle;
import controlutility.RWSettings;
import controlutility.RWSettingsImpl;
import gamelogics.GameEngine;
import gamelogics.GameEngineImpl;
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
import timer.*;
import timer.Timer;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.stream.IntStream;

/**
 * The Controller related to the playGame.fxml GUI.
 *
 */
public class SinglePlayerController implements ModalityController {
    private int mines;
    private int ccflags = 0;
    private int height;
    private int width;
    private final GridPane grid = new GridPane();
    private AlertStyle alStyle;
    private Clip clip;
    private Boolean firstClick = false;
    //private GameEngine engine;
    private final TimerFactory timerFactory = new TimerFactoryImpl();
    private final Timer timer;
    private RWSettings rwSett;

    //private final Map<Pair<Integer,Integer>,Tile> tilesMap;



    @FXML
    private Label lbFlags;
    @FXML
    private Label lbMines;
    @FXML
    private Label lbTimer = new Label();
    @FXML
    private Button btnStartGame;
    @FXML
    private AnchorPane rootPane;

    @FXML
    private BorderPane mainBorderPane;



    public SinglePlayerController(final int height, final int width, final int mines, final Timer timer) {
        this.height = height;
        this.width = width;
        this.mines = mines;
        this.timer = timer;
        grid.setStyle(" -fx-grid-lines-visible: true; -fx-grid-border-style: solid inside;");


    }

    @Override
    public void initialize() throws IOException {
        this.rwSett = new RWSettingsImpl();
        setLabel();
        //setupTimer();
        buildTiles();

    }

    private void setLabel() {
        lbFlags.setText("Flags:" + 0);
        lbMines.setText("Mines:" + mines);
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
            //this.tilesMap.put(new Pair<>(x, y),tile);
            tile.setOnMouseClicked(e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    leftClickHandler(tile);
                } else if (e.getButton() == MouseButton.SECONDARY){
                    rightClickHandler(tile);
                }
            });
            return tile;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not create tile correctly");
        }

    }

    private void leftClickHandler(final Tile tile) {
        if(!this.firstClick) {
            this.firstClick = true;
            setupTimer();
        }
        if(!tile.isFlagged()) {
            tileEffect(tile);
            tile.disable();
            //tile.getValue();
        }
    }

    protected void rightClickHandler(final Tile tile) {
            //this.engine.setFlag(new Pair<>(x,y));

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

    protected void tileEffect(final Tile tile) {

        FadeTransition fade = new FadeTransition();
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setDuration(Duration.millis(300));
        fade.setNode(tile);

        TranslateTransition transition = new TranslateTransition();
        transition.setByY(300);
        transition.setDuration(Duration.millis(1000));
        transition.setNode(tile);
        transition.play();
        fade.play();

    }






/*    private void refreshBoard() {
        for(Pair<Integer,Integer> tile: this.engine.getGameStatus().keySet()){
            if(this.engine.getGameStatus().get(tile) != -1) {
                final Tile tmpTile = this.tilesMap.get(tile);
                //tmpTile.disable();
                tmpTile.setValue(this.engine.ga().get(tile));
            }
        }
    }*/
}