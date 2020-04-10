package graphics;

import java.io.IOException;
import java.util.Optional;

import controlutility.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import scoresystem.Player;
import scoresystem.PlayerFactory;
import scoresystem.PlayerFactoryImpl;
import timer.*;

/**
 * The Controller related to the SinglePlayer.fxml GUI.
 */

public final class GraphicsImpl implements Graphics {
    private final PlayerFactory playerFactory;
    private final Difficulty difficulty;
    private GameController modalityController;
    private Modality modality;
    protected TextInputDialog dialog = new TextInputDialog("");
    public RWSettings rwSett;
    protected final TimerFactory timerFactory = new TimerFactoryImpl();

    public GraphicsImpl(final Modality modality, final Difficulty difficulty, final int mines, final int height, final int width, final Stage stage) throws IOException {
        this.difficulty = difficulty;
        this.playerFactory = new PlayerFactoryImpl();
        this.rwSett = new RWSettingsImpl();
        this.modality = modality;

        this.dialog.setTitle("| SAVE YOUR SCORE |");
        this.dialog.setHeaderText("Input your nickname if you want to save your score :)");
        this.dialog.setContentText("Enter your nickname: ");

        final Optional<String> playerName = dialog.showAndWait();
        final Parent parentPane;
        final FXMLLoader loader;

        //modality check for panel
        switch (modality) {
            case STANDARD:
                loader = new FXMLLoader(ClassLoader.getSystemResource("layouts/SinglePlayer.fxml"));
                this.modalityController = new SinglePlayerController(height, width, mines,this.timerFactory.createTimerForStandardMode());
                setPlayer(playerName);
                loader.setController(modalityController);
                parentPane = loader.load();
                final Scene singlePlayerScene = new Scene(parentPane, stage.getScene().getWidth(), stage.getScene().getHeight());
                singlePlayerScene.getStylesheets().add(ClassLoader.getSystemResource("css/" + rwSett.getCss()).toExternalForm());
                stage.setScene(singlePlayerScene);
                stage.show();
                break;

            case ONE_VS_ONE:
                loader = new FXMLLoader(ClassLoader.getSystemResource("layouts/OneVsOne.fxml"));
                this.modalityController = new MultiplayerController(height, width, mines,this.timerFactory.createTimersFor1vs1Mode());
                setPlayer(playerName);
                loader.setController(modalityController);
                parentPane = loader.load();
                final Scene oneVsOneScene = new Scene(parentPane, stage.getScene().getWidth(), stage.getScene().getHeight());
                oneVsOneScene.getStylesheets().add(ClassLoader.getSystemResource("css/" + rwSett.getCss()).toExternalForm());
                stage.setScene(oneVsOneScene);
                stage.setFullScreen(true);
                stage.show();
                break;

            case BTT:
                loader = new FXMLLoader(ClassLoader.getSystemResource("layouts/SinglePlayer.fxml"));
                this.modalityController = new SinglePlayerController(height, width, mines,this.timerFactory.createTimerForBeatTheTimerMode(100));
                loader.setController(modalityController);
                parentPane = loader.load();
                final Scene beatTheTimeScene = new Scene(parentPane, stage.getScene().getWidth(), stage.getScene().getHeight());
                beatTheTimeScene.getStylesheets().add(ClassLoader.getSystemResource("css/" + rwSett.getCss()).toExternalForm());
                stage.setScene(beatTheTimeScene);
                stage.show();
                break;
        }

    }

    private void setPlayer(final Optional<String> playerName) {
        if(playerName.isPresent()) {
            final Player player = playerFactory.createPlayerForStandardMode(playerName.get(),difficulty);
            this.modalityController.setPlayer(Optional.of(player));
        } else {
            this.modalityController.setPlayer(Optional.empty());
        }
    }


}
