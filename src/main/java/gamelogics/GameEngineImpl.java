package gamelogics;

import java.util.HashMap;
import java.util.Map;

/**
 * The implementation of {@link GameEngine}.
 */
public class GameEngineImpl implements GameEngine {

    private final Board board;

    public GameEngineImpl(final int width, final int height, final int bombs) {
        final BoardBuilder boardBuilder = new BoardBuilderImpl();
        boardBuilder.withWidth(width).withHeight(height);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                final BombGenerator bombSystem = new BombGeneratorImpl(width, height, bombs);
                final Box box = new BoxImpl(new Pair<>(i, j), bombSystem.next());
                boardBuilder.addBox(box);
            }
        }
        this.board = boardBuilder.build();
    }

    @Override
    public final void hit(final Pair<Integer, Integer> coord) {
        final Box box = board.getBox(coord);
        if (!box.isClicked()) {
            box.hit();
        }
    }

    @Override
    public final void setFlag(final Pair<Integer, Integer> coord) {
        this.board.getBox(coord).setFlag();
    }

    @Override
    public final GameStatus getGameStatus() {
        int goodBoxCount = 0;
        for (final Box box : this.board) {
            if (box.isClicked() && box.containsBomb()) {
                return GameStatus.LOSE;
            }
            if (box.isClicked() || box.isFlagged()) {
                goodBoxCount++;
            }
        }
        return goodBoxCount == this.board.size() ? GameStatus.WIN : GameStatus.NORMAL;
    }

    @Override
    public final Map<Pair<Integer, Integer>, Integer> getBoardStatus() {
        final Map<Pair<Integer, Integer>, Integer> map = new HashMap<>();
        for (final Box box : this.board) {
            map.put(box.getPosition(), this.board.getNearBox(box).size());
        }
        return map;
    }
}
