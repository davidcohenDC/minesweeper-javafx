package graphics;

import gamelogics.GameEngine;
import graphicsutility.SongAgent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.SubScene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import scoresystem.Player;
import timer.Timer;

import java.io.IOException;
import java.util.Optional;

public class MultiplayerController  implements GameController {
    private static final int SIZE = 600;
    private final SinglePlayerController gameControllerP1;
    private final SinglePlayerController gameControllerP2;
    private Boolean whatPlayer = true;
    private SongAgent music;

    @FXML
    private SplitPane splitPane;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private BorderPane bpPlayer1;
    @FXML
    private BorderPane bpPlayer2;
    private boolean firstClick;
    private GameEngine engine;

    public MultiplayerController(int height, int width, int mines, Timer timer) throws IOException {
        this.gameControllerP1 = new SinglePlayerController(height,width,mines,timer);
        this.gameControllerP2 = new SinglePlayerController(height,width,mines,timer);
    }


    public void initialize() {
        final FXMLLoader loader1 = new FXMLLoader(ClassLoader.getSystemResource("layouts/SinglePlayer.fxml"));
        final FXMLLoader loader2 = new FXMLLoader(ClassLoader.getSystemResource("layouts/SinglePlayer.fxml"));
        loader1.setController(gameControllerP1);
        loader2.setController(gameControllerP2);

        try {
            final Pane pane1 = loader1.load();
            final SubScene sub1 = new SubScene(pane1, SIZE, SIZE);
            this.bpPlayer1.setCenter(sub1);
            sub1.setVisible(true);
            //Palyer2
            final Pane pane2 = loader2.load();
            final SubScene sub2 = new SubScene(pane2, SIZE, SIZE);
            this.bpPlayer2.setCenter(sub2);
            sub2.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.bpPlayer2.setDisable(false);
    }

    @Override
    public void setPlayer(Optional<Player> player) {

    }

    }
