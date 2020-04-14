package graphicsutility;

import controlutility.RWSettings;
import controlutility.RWSettingsImpl;
import gamelogics.GameStatus;
import graphics.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ButtonReactionimpl implements ButtonReaction {
    private AnchorPane rootPane;
    private AlertHandler alert;
    //private AbstractGameController controller;

    public ButtonReactionimpl(final AnchorPane rootPane) throws IOException{
        this.rootPane = rootPane;
        this.alert = new AlertHandlerImpl();

    }

    @Override
    public void backHome() throws IOException {
        //alert.lost();
        final RWSettings rwSett = new RWSettingsImpl();
        final Parent pane = FXMLLoader.load(ClassLoader.getSystemResource("layouts/playGame.fxml"));
        final Stage stage = (Stage) this.rootPane.getScene().getWindow();
        final Scene scene = new Scene(pane, stage.getScene().getWidth(), stage.getScene().getHeight());
        scene.getStylesheets().add(ClassLoader.getSystemResource("css/" + rwSett.getCss()).toExternalForm());
        stage.setScene(scene);
    }

    @Override
    public void restartGame(final GameController controller)  throws IOException{
/*        final FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource(controller.getFXML()));
        SinglePlayerController gameController = new SinglePlayerController(controller.getHeight(),controller.getWidth(),controller.getMines(),controller.getTimer());
        loader.setController(controller);
        final Parent pane = loader.load();
        final Stage stage = (Stage) this.rootPane.getScene().getWindow();
        final Scene scene = new Scene(pane, stage.getScene().getWidth(), stage.getScene().getHeight());
        scene.getStylesheets().add(ClassLoader.getSystemResource("css/" + rwSett.getCss()).toExternalForm());
        stage.setScene(scene);
        stage.show();*/
    }


}
