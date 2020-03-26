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
    public final void won(final Optional<Integer> score) {
        this.result = Optional.of(GameStatus.WIN);
        this.score = score;
    }

    @Override
    public final void lost() {
        this.result = Optional.of(GameStatus.LOSE);
    }

    @Override
    public final int getScore() {
        if (this.score.isEmpty()) {
            throw new IllegalStateException("Nothing to score");
        }
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
        if (this.result.isEmpty()) {
            throw new IllegalStateException("Player's result was accessed before finishing the game");
        }
        return this.result.get();
    }

}
