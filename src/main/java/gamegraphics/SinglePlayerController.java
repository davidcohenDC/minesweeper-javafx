package gamegraphics;

import controlutility.AlertStyle;
import controlutility.Modality;
import gamelogics.Board;
import gamelogics.GameEngine;
import gamelogics.GameEngineImpl;
import gamelogics.Pair;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import timer.*;
import timer.Timer;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * The Controller related to the playGame.fxml GUI.
 *
 */
public class SinglePlayerController implements ModalityController {
    private GameEngine engine;
    private final TimerFactory timerFactory = new TimerFactoryImpl();
    private final GridPane grid = new GridPane();
    private final Map<Pair<Integer,Integer>,Tile> tilesMap;
    private AlertStyle alStyle;
    private Clip clip;
    private Board board;
    private int mines;
    private int countflags = 0;
    private int height;
    private int width;
    private final Timer timer;

    @FXML
    private Label lbFlags;
    @FXML
    private Label lbMines;
    @FXML
    private Label lbTimer = new Label();
    @FXML
    private Button btnRestart;
    @FXML
    private Button btnStartGame;
    @FXML
    private BorderPane mainBorderPane;



    public SinglePlayerController(final int height, final int width, final int mines, final Timer timer) {
        this.height = height;
        this.width = width;
        this.mines = mines;
        this.timer = timer;

        this.tilesMap = new HashMap<>();
        this.engine = new GameEngineImpl(this.width,this.height,this.mines);
    }

    @Override
    public void initialize() throws IOException {
        setLabel();
        createTimer();
        makeTiles();

    }

    private void setLabel() {
        lbFlags.setText("Flags:" + 0);
        lbMines.setText("Mines:" + mines);
    }

    public void createTimer() {
        //final Timer timer = timerFactory.createTimerForStandardMode();
        final TimerView timerView = new TimerViewImpl(timer, lbTimer);
        timerView.startDisplaying();
        timer.start();
    }

    private void makeTiles() {
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
                    if(!tile.isFlagged()) {
                        tile.disable();
                        tile.getValue();
                        this.engine.hit(new Pair<>(x,y));
                        refreshBoard();
                    }
                } else if (e.getButton() == MouseButton.SECONDARY){
                    this.engine.setFlag(new Pair<>(x,y));

                    if(!tile.isFlagged()) {
                        this.countflags++;
                        tile.flag();
                        this.lbFlags.setText("FLags:" + this.countflags);
                    }
                    else {
                        this.countflags--;
                        tile.flag();
                        this.lbFlags.setText("FLags:" + this.countflags);
                    }
                }

            });
            return tile;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not create tile correctly");
        }

    }

    private void refreshBoard() {
        for(Pair<Integer,Integer> tile: this.engine.getBoardStatus().keySet()){
            if(this.engine.getBoardStatus().get(tile) != -1) {
                final Tile tmpTile = this.tilesMap.get(tile);
                //tmpTile.disable();
                tmpTile.setValue(this.engine.getBoardStatus().get(tile));
            }
        }
    }
}