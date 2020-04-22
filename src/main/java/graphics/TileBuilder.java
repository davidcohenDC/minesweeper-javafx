package graphics;

import gamelogics.Pair;
import javafx.scene.layout.GridPane;
import java.util.Map;

/**
 * The Builder of {@link TileImpl} grid.
 * <p>
 * This class build the grid of the game<br>
 * </p>
 */
public interface TileBuilder {

    /**
     * Set the {@link TileBuilder} height.
     *
     * @param width
     *                  width of the {@link TileBuilder}
     * @return {@link TileBuilder}
     */
    TileBuilder withWidth(final int width);

    /**
     * Set the {@link TileBuilder} height.
     *
     * @param height
     *                   height of the {@link TileBuilder}
     * @return {@link TileBuilder}
     */
    TileBuilder withHeight(final int height);

    /**
     * Set the {@link TileBuilder} grid.
     *
     * @param grid
     *                 grid of the {@link TileBuilder}
     * @return {@link TileBuilder}
     */
    TileBuilder withGrid(final GridPane grid);

    /**
     * Build the {@link TileBuilder}.
     *
     * @return A builded {@link TileBuilder}
     */
    Map<Pair<Integer, Integer>, TileImpl> build();

}
