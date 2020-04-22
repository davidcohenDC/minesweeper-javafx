package graphics;

import controllers.PlayGameInterface;
import controlutility.RWSettings;
import controlutility.RWSettingsImpl;
import gamelogics.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import timer.Timer;
import java.io.IOException;
import java.util.Map;

/**
 * The Abstract Class of {@link GameController}.
 */
public abstract class AbstractGameController implements GameController {
    private final int height;
    private final int width;
    private final int mines;
    private final Timer timer;

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

    /**
     * set the functions to handle when user press left/right click in the
     * {@link GameController}.
     *
     * @param engine
     *                     the {@link GameEngine} for handle it
     * @param tilesMap
     *                     the Map of {@link TileImpl}
     *
     */
    protected void setClickHandler(final GameEngine engine, final Map<Pair<Integer, Integer>, TileImpl> tilesMap) {
        for (final Box box : engine.getBoard()) {
            final TileImpl tmpTile = tilesMap.get(box.getPosition());
            tmpTile.setOnMouseClicked(e -> {
                switch (e.getButton()) {
                case PRIMARY:
                    leftClickHandler(tmpTile, tmpTile.getX(), tmpTile.getY());
                    break;
                case SECONDARY:
                    engine.setFlag(new Pair<>(tmpTile.getX(), tmpTile.getY()));
                    rightClickHandler(tmpTile, tmpTile.getX(), tmpTile.getY());
                    break;
                default:
                    break;
                }
            });
        }
    }

    /**
     * Update the {@link Board} and the Map of {@link Tile} associated.
     *
     * @param engine
     *                     the {@link GameEngine} for handle it and check the status
     * @param tilesMap
     *                     the Map of {@link Tile} for handle each of them
     *
     */
    protected void refreshBoard(final GameEngine engine, final Map<Pair<Integer, Integer>, TileImpl> tilesMap) {
        for (final Box box : engine.getBoard()) {
            final TileImpl tmpTile = tilesMap.get(box.getPosition());
            if (box.isClicked()) {
                if (box.containsBomb()) {
                    tmpTile.setMine();
                } else {
                    tmpTile.setValue(box.getBombNear());
                    tmpTile.setDisable();
                    tmpTile.setStyle(box.getBombNear());
                }
            }
            if (engine.getGameStatus().equals(GameStatus.LOST)) {
                if (box.containsBomb()) {
                    tmpTile.setMine();
                } else {
                    tmpTile.setEffect();
                }
            }
        }
    }

    /**
     * Set the {@link Scene} to the {@link PlayGameInterface}.
     *
     * @exception IOException
     *                            if an I/O error occurs.
     */
    protected void backHome() throws IOException {
        final RWSettings rwSett = new RWSettingsImpl();
        final Parent pane = FXMLLoader.load(ClassLoader.getSystemResource("layouts/playGame.fxml"));
        final Stage stage = (Stage) this.rootPane.getScene().getWindow();
        final Scene scene = new Scene(pane, stage.getScene().getWidth(), stage.getScene().getHeight());
        scene.getStylesheets().add(ClassLoader.getSystemResource("css/" + rwSett.getCss()).toExternalForm());
        stage.setScene(scene);
    }

    @Override
    public final int getHeight() {
        return this.height;
    }

    @Override
    public final int getMines() {
        return this.mines;
    }

    @Override
    public final int getWidth() {
        return this.width;
    }

    @Override
    public final Timer getTimer() {
        return this.timer;
    }

    @Override
    public abstract void rightClickHandler(final TileImpl tile, final int x, final int y);

    @Override
    public abstract void leftClickHandler(final TileImpl tile, final int x, final int y);

    @Override
    public abstract void initialize() throws IOException;

    @Override
    public abstract void endGame(final GameStatus status);

    @Override
    public abstract void setButtons();

    @Override
    public abstract void closeElements();

    @Override
    public abstract String getFXML();

}
