package scoresystem;

import java.util.Optional;

import controlutility.Difficulty;
import controlutility.Modality;
import gameLogics.GameStatus;

public class PlayerImpl implements Player {

    private final String name;
    private final Modality gameMode;
    private final Difficulty difficuly;
    private Optional<Integer> score = Optional.empty();
    private Optional<GameStatus> result = Optional.empty();

    public PlayerImpl(final String name, final Modality gameMode, final Difficulty difficulty) {
        this.name = name;
        this.gameMode = gameMode;
        this.difficuly = difficulty;
    }

    @Override
    public final void won(final int score) {
        this.result = Optional.of(GameStatus.WIN);
        this.score = Optional.of(score);
    }

    @Override
    public final void lost() {
        this.result = Optional.of(GameStatus.LOSE);
    }

    @Override
    public final int getScore() {
        check(this.score.isEmpty(), "Nothing to score");
        return this.score.get();
    }

    @Override
    public final Modality getModality() {
        return this.gameMode;
    }

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public final Difficulty getDifficuly() {
        return this.difficuly;
    }

    @Override
    public final GameStatus getResult() {
        check(this.result.isEmpty(), "Player's result was accessed before finishing the game");
        return this.result.get();
    }

    private void check(final boolean expression, final String errorMessage) {
        if (expression) {
            throw new IllegalStateException(errorMessage);
        }
    }

}
