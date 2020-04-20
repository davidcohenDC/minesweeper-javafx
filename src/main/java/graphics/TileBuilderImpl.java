package graphics;

import gamelogics.Pair;
import graphics.Tile;
import graphics.TileBuilder;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
        for(int r = 0; r < this.height;r++) {
            for(int c = 0; c < this.width;c++) {
                this.grid.add(createTile(c, r), r, c);
            }
        }
        this.grid.setAlignment(Pos.CENTER);
        this.grid.setStyle(" -fx-grid-lines-visible: true; -fx-grid-border-style: solid inside;");
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
