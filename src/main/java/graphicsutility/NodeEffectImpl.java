package graphicsutility;

import graphics.TileImpl;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

/**
 * The implementation of {@link NodeEffect}.
 */
public class NodeEffectImpl implements NodeEffect {

    private static final int SHIFT_AMOUNT = 200;
    private static final int DURATION_AMOUNT = 3000;
    @Override
    public final void fallingTiles(final TileImpl tile) {
        final FadeTransition fade;
        final TranslateTransition transition = new TranslateTransition();
        fade = new FadeTransition();
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setDuration(Duration.millis(1000));
        fade.setNode(tile);

        transition.setByY(SHIFT_AMOUNT);
        transition.setDuration(Duration.millis(DURATION_AMOUNT));
        transition.setNode(tile);

        transition.play();
        fade.play();
    }

}
