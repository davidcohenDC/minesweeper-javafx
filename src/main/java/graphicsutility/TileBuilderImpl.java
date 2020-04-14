package graphicsutility;

import gamelogics.Pair;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class TileBuilderImpl implements TileBuilder {

    private final Map<Pair<Integer, Integer>, Tile> tilesMap = new HashMap<>();
    private int height;
    private int width;

    private GridPane grid;

    public TileBuilder withWidth(final int width) {
        this.width = width;
        return this;
    }

    public TileBuilder withHeight(final int height) {
        this.height = height;
        return this;
    }

    public TileBuilder withGrid(final GridPane grid) {
        this.grid = grid;
        return this;
    }

    @Override
    public Map<Pair<Integer, Integer>, Tile> build() {
        IntStream.range(0, this.height)
                .forEach(r -> IntStream.range(0, this.width).forEach(c -> grid.add(createTile(r, c), c, r)));
        grid.setAlignment(Pos.CENTER);
        grid.setStyle(" -fx-grid-lines-visible: true; -fx-grid-border-style: solid inside;");

        return this.tilesMap;
    }

    private Tile createTile(final int x, final int y) {
        final Tile tile;
        try {
            tile = new Tile(x,y);
            this.tilesMap.put(new Pair<>(x, y),tile);
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not create tile correctly");
        }

        return tile;
    }
}
