package graphics;

import gamelogics.Pair;
import javafx.scene.layout.GridPane;
import java.util.Map;

public interface TileBuilder {

    TileBuilder withWidth(final int width);

    TileBuilder withHeight(final int height);

    TileBuilder withGrid(final GridPane grid);

    Map<Pair<Integer,Integer>, Tile> build();

}
