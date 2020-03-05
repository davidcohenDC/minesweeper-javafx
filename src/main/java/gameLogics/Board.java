package gameLogics;

import java.util.HashMap;

/**
 * Interface for Board managing
 */
public interface Board {

    void hit(Pair<Integer, Integer> coord);

    void setFlag(Pair<Integer, Integer> coord);

    HashMap<Pair<Integer, Integer>, Integer> getBoard();
}
