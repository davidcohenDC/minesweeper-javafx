package controllers;

import java.io.IOException;

import controlutility.Modality;
import controlutility.RWSettings;
import controlutility.RWSettingsImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * The Controller related to the mainStatistics.fxml GUI.
 */
public class MainStatisticsController extends BackHomeController implements MainStatisticsControllerInterface {
    private RWSettings rwSett;


    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button btStandard;
    @FXML
    private Button bt1vs1;
    @FXML
    private Button btBtt;

    @Override
    public final void initialize() throws IOException {
        this.rwSett = new RWSettingsImpl();
    }

    /** used to switch scene on the same stage. 
     * @throws IOException */
    private void switchScene(final Modality modality, final String buttonText) throws IOException {
        final FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource("layouts/statistics.fxml"));
        final StatisticsControllerInterface statController = new StatisticsController(modality, buttonText);
        loader.setController(statController);
        //final Parent pane = loader.load();
        final Stage stage = (Stage) this.rootPane.getScene().getWindow();
        final Scene scene = new Scene(loader.load(), stage.getScene().getWidth(), stage.getScene().getHeight());
        scene.getStylesheets().add(ClassLoader.getSystemResource("css/" + rwSett.getCss()).toExternalForm());
        stage.setScene(scene);
        // centro lo stage nello schermo
        final Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);

    }

    @Override
    public final void btStandard() throws IOException {
        this.switchScene(Modality.STANDARD, this.btStandard.getText());
    }

    @Override
    public final void bt1vs1() throws IOException {
        this.switchScene(Modality.ONE_VS_ONE, this.bt1vs1.getText());
    }

    @Override
    public final void btBtt() throws IOException {
        this.switchScene(Modality.BTT, this.btBtt.getText());
    }

}
