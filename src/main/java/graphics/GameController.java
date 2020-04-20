package graphics;

import gamelogics.GameEngine;
import gamelogics.GameStatus;
import scoresystem.Player;
import timer.Timer;

import java.io.IOException;
import java.util.Optional;

public interface GameController {

    void leftClickHandler(final Tile tile, final int x, final int y);

    void rightClickHandler(final Tile tile, final int x, final int y,final GameEngine engine);

    void initialize() throws IOException;

    void setButtons();

    void endGame(final GameStatus gameStatus);

    void writePlayer(final GameStatus status);

    void closeElements();

    void setPlayers(Optional<Player> firstplayer, Optional<Player> secondplayer);

    //void backHome() throws IOException;

    int getWidth();

    int getHeight();

    int getMines();

    Timer getTimer();

    String getFXML();



}
