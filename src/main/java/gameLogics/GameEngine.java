package gameLogics;

import java.util.HashMap;

public interface GameEngine {

    void hit(Pair<Integer, Integer> coord);

    void setFlag(Pair<Integer, Integer> coord);

    HashMap<Pair<Integer, Integer>, Integer> getBoard();
}
