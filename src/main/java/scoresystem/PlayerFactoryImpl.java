package scoresystem;

import java.util.Optional;

import controlutility.Difficulty;
import controlutility.Modality;

/**
 * The implementation of {@link PlayerFactory}.
 */
public class PlayerFactoryImpl implements PlayerFactory {

    @Override
    public final Player createPlayerForStandardMode(final String name, final Difficulty difficulty) {
        return new PlayerImpl(name, Modality.STANDARD, difficulty, Optional.empty());
    }

    @Override
    public final Player createPlayerForBeatTheTimerMode(final String name, final Difficulty difficulty) {
        return new PlayerImpl(name, Modality.BTT, difficulty, Optional.empty());
    }

    @Override
    public final Player createPlayerFor1vs1Mode(final String name, final Difficulty difficulty, final String adversaryName) {
        return new PlayerImpl(name, Modality.ONE_VS_ONE, difficulty, Optional.of(adversaryName));
    }

}
