package gamelogics;

/**
 * The implementation of {@link GameEngine}.
 */
public class GameEngineImpl implements GameEngine {

    private final Board board;
    private boolean lost;

    /**
     * Setup the game engine.
     * 
     * @param width
     *                   The width of the board
     * @param height
     *                   The height of the board
     * @param bombs
     *                   The number of bombs in the board
     */
    public GameEngineImpl(final int width, final int height, final int bombs) {
        this.lost = false;

        final BoardBuilder boardBuilder = new BoardBuilderImpl();
        boardBuilder.withWidth(width).withHeight(height);

        final BombGenerator bombSystem = new BombGeneratorImpl(width, height, bombs);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                final Box box = new BoxImpl(new Pair<>(i, j), bombSystem.next());
                boardBuilder.addBox(box);
            }
        }
        this.board = boardBuilder.build();
    }

    @Override
    public final void hit(final Pair<Integer, Integer> coord) {
        if (!board.getBox(coord).isClicked() && !board.getBox(coord).isFlagged()) {
            this.board.getBox(coord).hit();
            if (board.getBox(coord).containsBomb()) {
                this.lost = true;
            } else {
                if (this.board.getBox(coord).getBombNear() == 0) {
                    this.expand(coord);
                }
            }
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
            if (this.lost) {
                return GameStatus.LOST;
            }
            if (!box.isClicked() && box.containsBomb() || box.isClicked() || box.isFlagged()) {
                goodBoxCount++;
            }
        }
        return goodBoxCount == this.board.size() ? GameStatus.WON : GameStatus.PLAYING;
    }

    @Override
    public final Board getBoard() {
        return this.board;
    }

    private void expand(final Pair<Integer, Integer> coord) {
        this.board.getBox(coord).hit();

        if (this.board.getBox(coord).getBombNear() == 0) {
            for (final Box box : this.board.getNearBox(this.board.getBox(coord))) {
                if (!box.isClicked() && !box.isFlagged() && !box.containsBomb()) {
                    box.hit();
                    this.expand(box.getPosition());
                }
            }
        }
    }
}
