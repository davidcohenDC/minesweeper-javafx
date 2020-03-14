package scoresystem;

import java.util.Optional;

import controlutility.Difficulty;
import controlutility.Modality;

public class PlayerImpl implements Player {

    private final String name;
    private final Modality gameMode;
    private final Difficulty difficuly;
    private final int score;
    private final Optional<Player> adversary;

    private boolean hasWon;

    protected PlayerImpl(final String name, final Modality gameMode, final Difficulty difficulty, final int score, final Optional<Player> adversary) {
        this.name = name;
        this.score = score;
        this.gameMode = gameMode;
        this.difficuly = difficulty;
        this.adversary = adversary;
    }

    @Override
    public final void won() {
        this.hasWon = true;
    }

    @Override
    public final void lost() {
        this.hasWon = false;
    }

    @Override
    public final int getScore() {
        return this.score;
    }

    @Override
    public final Modality getModality() {
        return this.gameMode;
    }
}
