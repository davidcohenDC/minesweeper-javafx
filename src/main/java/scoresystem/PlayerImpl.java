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
    private GameStatus result;

    public PlayerImpl(final String name, final Modality gameMode, final Difficulty difficulty) {
        this.name = name;
        this.gameMode = gameMode;
        this.difficuly = difficulty;
    }

    @Override
    public final void won(final Optional<Integer> score) {
        this.result = GameStatus.WIN;
        this.score = score;
    }

    @Override
    public final void lost() {
        this.result = GameStatus.LOSE;
    }

    @Override
    public final int getScore() {
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
        if (!(this.result.equals(GameStatus.LOSE)) && !(this.result.equals(GameStatus.LOSE))) {
            throw new IllegalStateException("Player's result was accessed before finishing the game");
        }
        return this.result;
    }

}
