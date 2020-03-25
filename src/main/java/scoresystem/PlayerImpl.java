package scoresystem;

import java.util.Optional;

import controlutility.Difficulty;
import controlutility.Modality;

public class PlayerImpl implements Player {

    private final String name;
    private final Modality gameMode;
    private final Difficulty difficuly;
    private Optional<Integer> score = Optional.empty();
//    private ... result;

    protected PlayerImpl(final String name, final Modality gameMode, final Difficulty difficulty) {
        this.name = name;
        this.gameMode = gameMode;
        this.difficuly = difficulty;
    }

    @Override
    public final void won(final Optional<Integer> score) {
//      this.result = null;
        this.score = score;
    }

    @Override
    public final void lost() {
//        this.result = null;
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
        return name;
    }

    @Override
    public final Difficulty getDifficuly() {
        return difficuly;
    }

}
