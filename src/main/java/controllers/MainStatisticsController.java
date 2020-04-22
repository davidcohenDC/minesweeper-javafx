package controllers;

import java.io.IOException;

import controlutility.Modality;
import controlutility.RWSettings;
import controlutility.RWSettingsImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The Controller related to the mainStatistics.fxml GUI.
 * The implementation of {@link MainStatisticsControllerInterface }.
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
     * @param modality represent the select modality
     * @param buttonText represent the text of button select. 
     * @throws IOException */
    private void switchScene(final Modality modality, final String buttonText) throws IOException {
        final FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource("layouts/statistics.fxml"));
        final Stage stage = (Stage) this.rootPane.getScene().getWindow();
        final double oldH = stage.getHeight();
        final double oldW = stage.getWidth();
        final StatisticsControllerInterface statController = new StatisticsController(modality, buttonText);
        loader.setController(statController);
        final Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(ClassLoader.getSystemResource("css/" + rwSett.getCss()).toExternalForm());
        stage.setScene(scene);
        stage.sizeToScene();
        if (oldH > stage.getHeight() && oldW > stage.getWidth()) {
            stage.setWidth(oldW);
            stage.setHeight(oldH);
        }
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
