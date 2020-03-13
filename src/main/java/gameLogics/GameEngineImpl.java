package gameLogics;

import java.util.HashMap;

public class GameEngineImpl implements GameEngine{

    private final Board board;

    public GameEngineImpl(int width, int height, int bombs) {
        final BoardBuilder bb = new BoardBuilderImpl().setWidth(width).setHeight(height);
        for(int i=0; i<width; i++) {
            for(int j=0; j<height; j++) {
                final BombGenerator bombSystem = new BombGeneratorImpl(width*height, bombs);
                final Box box = new BoxImpl(new Pair<>(i, j), bombSystem.next());
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
        this.board.getBox(coord).setFlag();
    }

    public GameStatus getGameStatus() {
        int goodBoxCount = 0;
        for(Box box : this.board) {
            if(box.isClicked() && box.containsBomb()) {
                return GameStatus.LOSE;
            }
            if(box.isClicked() || box.isFlagged()) {
                goodBoxCount++;
            }
        }
        return goodBoxCount == this.board.size() ? GameStatus.WIN : GameStatus.NORMAL;
    }

    public HashMap<Pair<Integer, Integer>, Integer> getBoardStatus() {
        final HashMap<Pair<Integer, Integer>, Integer> map = new HashMap<>();
        for(Box box : this.board) {
            map.put(box.getPosition(), this.board.getNearBox(box).size());
        }
        return map;
    }
}
