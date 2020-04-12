package graphics;

import gamelogics.GameEngine;
import gamelogics.GameStatus;
import gamelogics.Pair;
import graphicsutility.Tile;
import scoresystem.Player;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public interface GameController {

    void leftClickHandler(final Tile tile, final int x, final int y);

    void rightClickHandler(final Tile tile, final int x, final int y);

    void initialize() throws IOException;

    void setButtons();

    void refreshBoard(final GameEngine engine,final Map<Pair<Integer, Integer>,Tile> tilesMap);

    void endGame(final GameStatus gameStatus);

    void writePlayer(final GameStatus status);

    void closeElements();

    void setClickHandler(final GameEngine engine,final Map<Pair<Integer, Integer>,Tile>tilesMap);

    void setPlayer(Optional<Player> player);

}
