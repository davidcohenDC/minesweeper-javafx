package graphicsutility;

import graphics.TileImpl;

/**
 * The interface for manage the effects.
 */
public interface NodeEffect {

    /**
     * A falling effect for the {@link graphics.Tile}.
     *
     * @param  tile
     *              The {@link graphics.Tile} that used the effect
     */
    void fallingTiles(TileImpl tile);

}
