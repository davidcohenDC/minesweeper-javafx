package application;


import java.io.IOException;

import controlutility.LoadData;
import controlutility.LoadDataImpl;
import controlutility.RWSettings;
import controlutility.RWSettingsImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * This class represent the Main class of the JavaFX-based application.
 */
public final class Main extends Application {

    private static final LoadData LD = new LoadDataImpl();
    @Override
    public void start(final Stage stage) throws Exception {
        final RWSettings rwSett = new RWSettingsImpl();
        final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/home.fxml"));
        final Scene scene = new Scene(root);
        scene.getStylesheets().add(ClassLoader.getSystemResource("css/" + rwSett.getCss()).toExternalForm());
        // Stage configuration
        stage.setTitle("MINESWEEPER");
        stage.setScene(scene);
        stage.show();
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
    }

    /**
     * 
     * @param args unused
     * @throws IOException 
     */
    public static void main(final String[] args) throws IOException {
        LD.loadData();
        launch();
    }
}
