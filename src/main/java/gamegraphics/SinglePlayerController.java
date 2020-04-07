package gamegraphics;

import controlutility.AlertStyle;
import controlutility.Modality;
import gamelogics.Board;
import gamelogics.GameEngine;
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
import java.util.stream.IntStream;

/**
 * The Controller related to the playGame.fxml GUI.
 *
 */
public class SinglePlayerController implements ModalityController {
    private GameEngine engine;
    private final TimerFactory timerFactory = new TimerFactoryImpl();
    private final TimerViewFactory timerViewFactory = new TimerViewFactoryImpl();
    private final GridPane grid = new GridPane();
    private AlertStyle alStyle;
    private Clip clip;
    private Board board;
    private int mines;
    private int countflags = 0;
    private int height;
    private int width;

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

    public SinglePlayerController(final int height, final int width, final int mines) {
        this.height = height;
        this.width = width;
        this.mines = mines;
    }

    @Override
    public void initialize() throws IOException {
        setLabel();
        createTimer();
        makeTiles();

    }

    private void setLabel() {
        lbFlags.setText("FLags:" + 0);
        lbMines.setText("Mines:" + mines);
    }

    public void createTimer() {
        final Timer timer = timerFactory.createTimerForStandardMode();
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
        Tile tile  = null;
        try {
            tile = new Tile(x,y);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Tile finalTile = tile;
        tile.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                finalTile.getValue();
                finalTile.disable();
                if(finalTile.isFlagged()) {
                    finalTile.flag();
                }
            } else if (e.getButton() == MouseButton.SECONDARY){
                if(!finalTile.isFlagged()) {
                    countflags++;
                    finalTile.flag();
                    lbFlags.setText("FLags:" + countflags);
                }
                else {
                    countflags--;
                    finalTile.flag();
                    lbFlags.setText("FLags:" + countflags);
                }
            }
        });
        return tile;
    }
}