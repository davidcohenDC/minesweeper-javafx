package gameLogics;

import java.util.HashMap;

public class GameEngineImpl implements GameEngine{

    private final Board board;

    public GameEngineImpl(int width, int height) {
        final BoardBuilder bb = new BoardBuilderImpl().setWidth(width).setHeight(height);
        for(int i=0; i<width; i++) {
            for(int j=0; j<height; j++) {
                final Box box = new BoxImpl(new Pair<>(i, j));
                bb.addBox(box);
            }
        }
        this.board = bb.build();
    }

    public void hit(Pair<Integer, Integer> coord) {

    }

    public void setFlag(Pair<Integer, Integer> coord) {

    }

    public GameStatus status() {
        return null;
    }

    public HashMap<Pair<Integer, Integer>, Integer> getBoard() {
        return null;
    }
}
