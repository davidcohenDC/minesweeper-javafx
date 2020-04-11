package graphics;

import graphicsutility.Tile;
import scoresystem.Player;

import java.io.IOException;
import java.util.Optional;

public interface GameController {


    void setPlayer(Optional<Player>player);

    void leftClickHandler(final Tile tile, final int x, final int y);

    void rightClickHandler(final Tile tile, final int x, final int y);

    void initialize() throws IOException;

    void btnActions();

}
