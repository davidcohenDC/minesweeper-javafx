package controllers;

import java.io.IOException;

import controlutility.Modality;
import controlutility.RWSettings;
import controlutility.RWSettingsImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The Controller related to the statistics.fxml GUI.
 */
public class StatisticsController implements StatisticsControllerInterface {
    private final Modality modality;
    @FXML
    private Text title;
    @FXML 
    private AnchorPane rootPane;

    /**constructor that initialize field.
     * @param buttonText set title in order the modality chosen.
    */
    public StatisticsController(final Modality buttonText) {
        this.modality = buttonText;
    }

    /****/
    public void initialize() {
        this.title.setText("STATISTICS " + this.modality);
    }


    @FXML
    @Override
    public  final void btBackStat() throws IOException {
        final RWSettings rwSett = new RWSettingsImpl();
        final Parent pane = FXMLLoader.load(ClassLoader.getSystemResource("layouts/mainStatistics.fxml"));
        final Stage stage = (Stage) this.rootPane.getScene().getWindow();
        final Scene scene = new Scene(pane, stage.getScene().getWidth(), stage.getScene().getHeight());
        scene.getStylesheets().add(ClassLoader.getSystemResource("css/" + rwSett.getCss()).toExternalForm());
        stage.setScene(scene);
    }
}
