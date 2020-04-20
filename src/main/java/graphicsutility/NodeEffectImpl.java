package graphicsutility;

import graphics.TileImpl;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

/**
 * The implementation of {@link NodeEffect}.
 */
public class NodeEffectImpl implements NodeEffect {
    public FadeTransition fade;

    @Override
    public void fallingTiles(TileImpl tile) {
        TranslateTransition transition = new TranslateTransition();
        this.fade = new FadeTransition();
        this.fade.setFromValue(1.0);
        this.fade.setToValue(0.0);
        this.fade.setDuration(Duration.millis(1000));
        this.fade.setNode(tile);

        transition.setByY(200);
        transition.setDuration(Duration.millis(3000));
        transition.setNode(tile);

        transition.play();
        this.fade.play();
    }

}
