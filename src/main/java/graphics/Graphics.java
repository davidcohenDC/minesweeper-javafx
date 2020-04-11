package graphics;

import controlutility.Difficulty;
import controlutility.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**interface for GraphicsController.*/

public interface Graphics {

    Modality getModatily();

    Difficulty getdifficulty();

    Integer getWidth();

    Integer getHeight();

    public Stage getStage();


}
