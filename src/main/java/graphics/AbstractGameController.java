package graphics;

import controlutility.RWSettings;
import controlutility.RWSettingsImpl;
import gamelogics.*;
import graphicsutility.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import timer.Timer;
import java.io.IOException;
import java.util.Map;


public abstract class AbstractGameController implements GameController{
    private final int height;
    private final int width;
    private final int mines;
    private Timer timer;

    private NodeEffect effect;

    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private AnchorPane rootPane;

    public AbstractGameController(final int height, final int width, final int mines, final Timer timer) {
        this.height = height;
        this.mines = mines;
        this.width = width;
        this.timer = timer;
    }

    @Override
    public void setClickHandler(final GameEngine engine,final Map<Pair<Integer, Integer>,Tile>tilesMap) {
        for (final Box box : engine.getBoard()) {
            final Tile tmpTile = tilesMap.get(box.getPosition());
            System.out.println(box.getPosition());
            tmpTile.setOnMouseClicked(e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    leftClickHandler(tmpTile, tmpTile.getX(), tmpTile.getY());
                } else if (e.getButton() == MouseButton.SECONDARY) {
                    rightClickHandler(tmpTile, tmpTile.getX(), tmpTile.getY(),engine);
                }
            });
        }
    }

    @Override
    public void refreshBoard(final GameEngine engine,final Map<Pair<Integer, Integer>,Tile>tilesMap) {
        for (final Box box : engine.getBoard()) {
            final Tile tmpTile = tilesMap.get(box.getPosition());
            if (box.isClicked()) {
                if (box.containsBomb()) {
                    tmpTile.setMine();
                } else {
                    tmpTile.setValue(box.getBombNear());
                    tmpTile.disable();
                    tmpTile.style(box.getBombNear());
                }
            }
            if (engine.getGameStatus().equals((GameStatus.LOST))) {
                if (box.containsBomb()) {
                    tmpTile.setMine();
                } else {
                    tmpTile.fallingEffect();
                }
            }
        }
    }

    @Override
    public void backHome() throws IOException{
        final RWSettings rwSett = new RWSettingsImpl();
        final Parent pane = FXMLLoader.load(ClassLoader.getSystemResource("layouts/playGame.fxml"));
        final Stage stage = (Stage) this.rootPane.getScene().getWindow();
        final Scene scene = new Scene(pane, stage.getScene().getWidth(), stage.getScene().getHeight());
        scene.getStylesheets().add(ClassLoader.getSystemResource("css/" + rwSett.getCss()).toExternalForm());
        stage.setScene(scene);
    }


    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public int getMines() {
        return this.mines;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public Timer getTimer() {
        return this.timer;
    }

    @Override
    public abstract void rightClickHandler(final Tile tile, final int x, final int y,final GameEngine engine);

    @Override
    public abstract void initialize() throws IOException;

    @Override
    public abstract void setButtons();

    @Override
    public abstract void leftClickHandler(final Tile tile, final int x, final int y);

    @Override
    public abstract void endGame(final GameStatus gameStatus);

    @Override
    public abstract void closeElements();

    @Override
    public abstract String getFXML();

}
