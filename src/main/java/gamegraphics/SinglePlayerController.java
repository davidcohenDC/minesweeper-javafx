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
    private int mineCount;
    private int tileCount;
    private int effectiveMine;
    private Clip clip;
    private Board board;

    @FXML
    private Label lbFlags;
    @FXML
    private Label lbTimer = new Label();
    @FXML
    private Button btnRestart;
    @FXML
    private Button btnStartGame;
    @FXML
    private BorderPane mainBorderPane;

    private int height;
    private int width;

    public SinglePlayerController(final int height, final int width, final int mines) {
        this.height = height;
        this.width = width;
    }

    @Override
    public void initialize() throws IOException {
        createTimer();
        makeTiles();

    }

    @Override
    public void startGame() {

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
            } else if (e.getButton() == MouseButton.SECONDARY){
                if(!finalTile.isFlagged())
                finalTile.flag();
                else {
                    finalTile.flag();
                }
            }
        });
        return tile;
    }
}