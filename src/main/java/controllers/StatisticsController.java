package controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * The Controller related to the statistics.fxml GUI.
 */
public class StatisticsController extends BackHomeController implements StatisticsControllerInterface {
    private final String buttonText;
    @FXML
    private Text title;

    /**constructor that initialize field.
     * @param buttonText set title in order the modality chosen.
    */
    public StatisticsController(final String buttonText) {
        this.buttonText = buttonText;
    }

    /****/
    public void initialize() {
        this.title.setText("STATISTICS " + this.buttonText);
    }

}
