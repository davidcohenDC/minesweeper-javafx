package graphics;

import gamelogics.*;
import graphicsutility.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import scoresystem.Player;
import timer.Timer;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;


public abstract class AbstractGameController implements GameController{
    private int ccflagsP1;
    private int ccflagsP2;
    private Optional<Player> firstplayer;
    private Optional<Player> secondplayer;
    private NodeEffect effect;
    private Boolean whoPlay = true;

    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private Label lbFlagP1;
    @FXML
    private Label lbFlagP2;

    public AbstractGameController(final int height, final int width, final int mines, final Timer timer){
        this.effect = new NodeEffectImpl();
        this.ccflagsP1 = mines;
        this.ccflagsP2 = mines;
    }

    @Override
    public void setClickHandler(final GameEngine engine,final Map<Pair<Integer, Integer>,Tile>tilesMap) {
        for (final Box box : engine.getBoard()) {
            final Tile tmpTile = tilesMap.get(box.getPosition());
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
                    tmpTile.style();
                }
            }
            if (engine.getGameStatus().equals((GameStatus.LOST))) {
                if (box.containsBomb()) {
                    tmpTile.setMine();
                } else {
                    effect.fallingTiles(tmpTile);
                }
            }
        }
    }

    @Override
    public  void rightClickHandler(final Tile tile, final int x, final int y,final GameEngine engine) {
        engine.setFlag(new Pair<>(x, y));
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
            this.lbFlagP2.setText("F:" + this.ccflagsP2);
        }
    }

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



}
