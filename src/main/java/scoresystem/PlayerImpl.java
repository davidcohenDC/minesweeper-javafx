package scoresystem;

import java.util.Optional;

import controlutility.Difficulty;
import controlutility.Modality;
import gamelogics.GameStatus;

/**
 * The implementation of {@link Player}.
 */
public class PlayerImpl implements Player {

    private final String name;
    private final Modality gameMode;
    private final Difficulty difficuly;
    private Optional<Long> score = Optional.empty();
    private Optional<GameStatus> result = Optional.empty();
    private final Optional<String> adversary;

    /**
     * Creates the {@link Player}.
     *
     * @param name
     *                          The name of the Player.
     * @param gameMode
     *                          The {@link Modality} he is playing.
     * @param difficulty
     *                          The {@link Difficulty} he chose.
     * @param adversaryName
     *                          The adversary he is playing against. If he is
     *                          playing alone the adversary field will be empty.
     */
    protected PlayerImpl(final String name, final Modality gameMode, final Difficulty difficulty,
                         final Optional<String> adversaryName) {
        check(adversaryName.isPresent() && name.equals(adversaryName.get()), "Two different players can't have the same name");
        this.name = name;
        this.gameMode = gameMode;
        this.difficuly = difficulty;
        this.adversary = adversaryName;
    }

    @Override
    public final void won(final long score) {
        check(!this.result.isEmpty(), "Player's result cannot be modified after its initial registration");
        this.result = Optional.of(GameStatus.WON);
        this.score = Optional.of(score);
    }

    @Override
    public final void lost() {
        check(!this.result.isEmpty(), "Player's result cannot be modified after its initial registration");
        this.result = Optional.of(GameStatus.LOST);
    }

    @Override
    public final long getScore() {
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

    /**
     * The method checks if an expression is correct.<br>
     * If the expression is true it will throw an <code>IllegalStateExeption</code>.
     *
     * @param expression
     *                         The <code>boolean</code> expression to check.
     * @param errorMessage
     *                         The message to show if the exception gets thrown.
     */
    private void check(final boolean expression, final String errorMessage) {
        if (expression) {
            throw new IllegalStateException(errorMessage);
        }
    }
}
