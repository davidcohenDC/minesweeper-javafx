package scoresystem;

import java.util.Optional;

import controlutility.Difficulty;
import controlutility.Modality;
import gameLogics.GameStatus;

/**
 * The implementation of {@link Player}.
 */
public class PlayerImpl implements Player {

    private final String name;
    private final Modality gameMode;
    private final Difficulty difficuly;
    private Optional<Integer> score = Optional.empty();
    private Optional<GameStatus> result = Optional.empty();
    private final Optional<String> adversary;

    protected PlayerImpl(final String name, final Modality gameMode, final Difficulty difficulty, final Optional<String> adversaryName) {
        this.name = name;
        this.gameMode = gameMode;
        this.difficuly = difficulty;
        this.adversary = adversaryName;
    }

    @Override
    public final void won(final int score) {
        check(!this.result.isEmpty(), "Player's result cannot be modified after its initial registration");
        this.result = Optional.of(GameStatus.WIN);
        if (!this.difficuly.equals(Difficulty.PERSONALIZED)) {
            this.score = Optional.of(score);
        }
    }

    @Override
    public final void lost() {
        check(!this.result.isEmpty(), "Player's result cannot be modified after its initial registration");
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

    @Override
    public final Optional<String> getAdversary() {
        return this.adversary;
    }
 
    private void check(final boolean expression, final String errorMessage) {
        if (expression) {
            throw new IllegalStateException(errorMessage);
        }
    }
}
