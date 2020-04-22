package graphicsutility;

import graphics.TileImpl;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

/**
 * The implementation of {@link NodeEffect}.
 */
public class NodeEffectImpl implements NodeEffect {
    private FadeTransition fade;
    private static final int SHIFT_AMOUNT = 200;
    private static final int DURATION_AMOUNT = 3000;
    
    @Override
    public final void fallingTiles(TileImpl tile) {
        TranslateTransition transition = new TranslateTransition();
        this.fade = new FadeTransition();
        this.fade.setFromValue(1.0);
        this.fade.setToValue(0.0);
        this.fade.setDuration(Duration.millis(1000));
        this.fade.setNode(tile);

        transition.setByY(SHIFT_AMOUNT);
        transition.setDuration(Duration.millis(DURATION_AMOUNT));
        transition.setNode(tile);

        transition.play();
        this.fade.play();
    }

}
