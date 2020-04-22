package graphics;

import gamelogics.GameStatus;
import scoresystem.Player;
import timer.Timer;

import java.io.IOException;
import java.util.Optional;

/**
 * The GameController.
 * <p>
 * This class sets the base of the playing field for every modality<br>
 *</p>
 */
public interface GameController {

    /**
     * The handler for the left click.
     *
     * @param tile
     *                  the {@link Tile} selected to perform operations
     * @param x
     *                  the first coordinate
     * @param y
     *                  the second coordinate
     */
    void leftClickHandler(final TileImpl tile, final int x, final int y);

    /**
     * The handler for the Right click.
     *
     * @param tile
     *                  the {@link Tile} selected to perform operations
     * @param x
     *                  the first coordinate
     * @param y
     *                  the second coordinate
     *
     */
    void rightClickHandler(final TileImpl tile, final int x, final int y);

    /**
     * The handler for Initialize all the elements of the game.
     * 
     * @exception IOException
     *                            if an I/O error occurs.
     */
    void initialize() throws IOException;

    /**
     *  Set the buttons used in the game.
     */
    void setButtons();

    /**
     *  The handler when engine status of the game change to won or lost.
     * @param status
     *              the actual {@link GameStatus} of the game
     *
     */
    void endGame(final GameStatus status);

    /**
     *  Write the player that win or lost the game.
     * @param status
     *              the actual {@link GameStatus} of the game
     *
     */
    void writePlayer(final GameStatus status);

    /**
     *  Remove or close all elements used in the game.
     *
     */
    void closeElements();

    /**
     *  Used for set the Player in the game.
     * @param  firstplayer
     *                  the first player
     * @param secondplayer
     *                  the second player
     *
     */
    void setPlayers(Optional<Player> firstplayer, Optional<Player> secondplayer);

    /**
     * @return Returns the width.
     */
    int getWidth();

    /**
     * @return Returns the height.
     */
    int getHeight();

    /**
     * @return Returns mines.
     */
    int getMines();

    /**
     * @return Returns {@link Timer}.
     */
    Timer getTimer();

    /**
     * @return Returns the string of FXML file.
     */
    String getFXML();



}
