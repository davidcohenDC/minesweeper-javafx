package gameLogics;

import java.util.HashMap;

public class GameEngineImpl implements GameEngine{

    private final Board board;

    public GameEngineImpl(int width, int height, int bombs) {
        final BoardBuilder bb = new BoardBuilderImpl().setWidth(width).setHeight(height);
        for(int i=0; i<width; i++) {
            for(int j=0; j<height; j++) {
                final Box box = new BoxImpl(new Pair<>(i, j));
                //impl the bombs sistem
                bb.addBox(box);
            }
        }
        this.board = bb.build();
    }

    public void hit(Pair<Integer, Integer> coord) {
        final Box box = board.getBox(coord);
        if(!box.isClicked()) {
            box.hit();
        }
    }

    public void setFlag(Pair<Integer, Integer> coord) {
        board.getBox(coord).setFlag();
    }

    public GameStatus getGameStatus() {
        return null;
    }

    public HashMap<Pair<Integer, Integer>, Integer> getBoardStatus() {
        final HashMap<Pair<Integer, Integer>, Integer> map = new HashMap<>();
        for(Box box : board) {
            map.put(box.getPosition(), board.getNearBox(box).size());
        }
        return map;
    }
}
