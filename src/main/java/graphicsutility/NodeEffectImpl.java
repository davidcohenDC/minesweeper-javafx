package graphicsutility;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.util.Duration;

public class NodeEffectImpl implements NodeEffect {
    FadeTransition fade;
    TranslateTransition transition;

    public NodeEffectImpl() {

    }
    @Override
    public void fallingTiles(Tile tile) {
        this.transition = new TranslateTransition();
        this.fade = new FadeTransition();
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setDuration(Duration.millis(1000));
        fade.setNode(tile);

        transition.setByY(200);
        transition.setDuration(Duration.millis(3000));
        transition.setNode(tile);

        transition.play();
        fade.play();

    }

}
