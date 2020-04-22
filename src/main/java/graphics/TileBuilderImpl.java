package graphics;

import gamelogics.Pair;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The implementation of {@link TileBuilder}.
 */
public class TileBuilderImpl implements TileBuilder {

    private final Map<Pair<Integer, Integer>, TileImpl> tilesMap = new HashMap<>();
    private static final int MIN_SIZE = 25;
    private static final int MAX_SIZE = 35;
    private int height;
    private int width;
    private GridPane grid;


    @Override
    public final TileBuilder withWidth(final int width) {
        this.width = width;
        return this;
    }

    @Override
    public final TileBuilder withHeight(final int height) {
        this.height = height;
        return this;
    }

    @Override
    public final TileBuilder withGrid(final GridPane grid) {
        this.grid = grid;
        return this;
    }

    @Override
    public final Map<Pair<Integer, Integer>, TileImpl> build() {
        for (int r = 0; r < this.height; r++) {
            for (int c = 0; c < this.width; c++) {
                this.grid.add(createTile(c, r), c, r);
            }
        }
        this.grid.setAlignment(Pos.CENTER);
        this.grid.setStyle(" -fx-grid-lines-visible: true; -fx-grid-border-style: solid inside;");
        return this.tilesMap;
    }

    /**
     * Create each {@link Tile} to put in the Tilemap.
     *
     * @param x
     *              the first coordinate
     * @param y
     *              the second coordinate
     * @return active {@link Tile}
     */
    private TileImpl createTile(final int x, final int y) {
        final TileImpl tile;
        try {
            double size = MAX_SIZE;
            if (this.height > 16 || this.width > 16) {
                size = MIN_SIZE;
            }
            tile = new TileImpl(x, y, size);
            this.tilesMap.put(new Pair<>(x, y), tile);
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not create tile correctly");
        }
        return tile;
    }
}
