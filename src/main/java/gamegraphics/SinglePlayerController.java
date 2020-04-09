package gamegraphics;

import controllers.BackHomeController;
import controlutility.AlertStyle;
import controlutility.AlertStyleImpl;
import controlutility.RWSettings;
import controlutility.RWSettingsImpl;
import gamelogics.*;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;
import scoresystem.Player;
import scoresystem.ScoreWriter;
import scoresystem.ScoreWriterImpl;
import timer.*;
import timer.Timer;

import javax.sound.sampled.*;
import javax.swing.text.StyledEditorKit;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * The Controller related to the playGame.fxml GUI.
 *
 */
public class SinglePlayerController implements ModalityController {
    private static final String SEPARATOR = System.getProperty("file.separator");
    private final String urlSound = System.getProperty("user.home") + SEPARATOR + ".minesweeper" + SEPARATOR + "sound" + SEPARATOR;
    private final String urlImgFlag = System.getProperty("user.home") + SEPARATOR + ".minesweeper" + SEPARATOR + "image" + SEPARATOR
            + "obj" + SEPARATOR;
    private RWSettings rwSett;

    private AlertStyle alStyle;
    private Clip clip;
    private final GridPane grid = new GridPane();

    private int mines;
    private int height;
    private int width;

    private int ccflags;
    private Boolean firstClick = false;
    private Boolean playing = false;

    private GameEngine engine;
    private final Map<Pair<Integer,Integer>,Tile> tilesMap;
    private final TimerFactory timerFactory = new TimerFactoryImpl();
    private final Timer timer;
    private Optional<Player> player;

    @FXML
    private Label lbFlags;
    @FXML
    private Label lbMines;
    @FXML
    private Label lbTimer = new Label();
    @FXML
    private Button btnRestart;
    @FXML
    private Button btnBackHome;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private Button btnSong;

    public SinglePlayerController(final int height, final int width, final int mines, final Timer timer) throws IOException{
        this.height = height;
        this.width = width;
        this.mines = mines;
        this.timer = timer;
        this.rwSett = new RWSettingsImpl();
        this.alStyle = new AlertStyleImpl();
        grid.setStyle(" -fx-grid-lines-visible: true; -fx-grid-border-style: solid inside;");
        this.engine = new GameEngineImpl(width,height,mines);
        this.tilesMap = new HashMap<>();
        this.ccflags = this.mines;
    }

    @Override
    public void initialize() {
        setLabel();
        startSong();
        buildTiles();
        btnActions();
    }

    public void btnActions() {

        btnBackHome.setOnMouseClicked(t -> {
            try {
                backHome();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

/*        btnRestart.setOnAction(t -> {
            try {
                restart();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });*/

        btnSong.setOnMouseClicked(t -> checkSong());
    }

    private void setLabel() {
        lbFlags.setText("Flags:" + this.ccflags);
        lbMines.setText("Mines:" + mines);
        lbTimer.setText(String.valueOf(timer.getValue()));
        btnSong.setText("STOP MUSIC");
    }

    public void startSong() {
        this.playing = true;
        try {
            this.clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        final String path = urlSound + rwSett.getSong();
        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile())) {
            clip.open(audioStream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
            ex.printStackTrace();
        }
    }

    private void buildTiles() {
        IntStream.range(0, this.height)
                .forEach(r -> IntStream.range(0, this.width).forEach(c -> grid.add(createTile(r,c), c, r)));
        grid.setAlignment(Pos.CENTER);
        this.mainBorderPane.setCenter(grid);
    }

    private Tile createTile(final int x, final int y) {
        final Tile tile;

        try {
            tile = new Tile(x,y);
            this.tilesMap.put(new Pair<>(x, y),tile);
            tile.setOnMouseClicked(e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    leftClickHandler(tile,x,y);
                } else if (e.getButton() == MouseButton.SECONDARY){
                    rightClickHandler(tile,x,y);
                }
            });
            return tile;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not create tile correctly");
        }

    }

    private void leftClickHandler(final Tile tile,final int x, final int y) {

        if(!this.firstClick) {
            this.firstClick = true;
            startTimer();
        }

        if(!tile.isFlagged()) {
            this.engine.hit(new Pair<>(x,y));
            refreshBoard();

        }

        if(!this.engine.getGameStatus().equals((GameStatus.PLAYING))) {
            endGame();
        }

    }

    private void rightClickHandler(final Tile tile, final int x, final int y) {

        this.engine.setFlag(new Pair<>(x,y));

        if(!tile.isFlagged()) {
            this.ccflags--;
            tile.setflag();
            this.lbFlags.setText("FLags:" + this.ccflags);
        }
        else {
            this.ccflags++;
            tile.setflag();
            this.lbFlags.setText("FLags:" + this.ccflags);
        }
    }

    public void startTimer() {
        final TimerView timerView = new TimerViewImpl(timer, lbTimer);
        timerView.startDisplaying();
        timer.start();
    }

    private void refreshBoard() {
        for (final Box box : this.engine.getBoard()) {
            final Tile tmpTile = this.tilesMap.get(box.getPosition());
            if (box.isClicked()) {
                if (box.containsBomb()) {
                    tmpTile.setMine();
                    System.out.println(box.containsBomb());
                } else {
                    tmpTile.setValue(box.getBombNear());
                    tmpTile.disable();
                    System.out.println(box.getBombNear());
                }
            }
            if (this.engine.getGameStatus().equals((GameStatus.LOST))) {
                if (box.containsBomb()) {
                    tmpTile.setMine();

                } else {
                    tileEffect(tmpTile);
                }


            }


        }
    }


    private void endGame() {
        if(this.engine.getGameStatus().equals(GameStatus.LOST)) {
            finalAlert(GameStatus.LOST);
            setPlayer(GameStatus.LOST);
            closeElements();
            try {
                backHome();
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else if(this.engine.getGameStatus().equals(GameStatus.WON)) {
            finalAlert(GameStatus.WON);
            setPlayer(GameStatus.WON);
            closeElements();
            try {
                backHome();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void finalAlert(final GameStatus status) {
        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(status.equals(GameStatus.LOST)) {
            alert.setTitle("YOU LOST!");
            alert.setContentText("GAME OVER");
        } else {
            alert.setTitle("YOU WON!");
            alert.setContentText("GAME OVER");
        }
        alert.setHeaderText(null);
        this.alStyle.setStyle(alert);
        alert.showAndWait();
    }

    private void setPlayer(final GameStatus status) {
            if(this.player.isPresent()) {
                if (status.equals(GameStatus.LOST)) {
                    this.player.get().lost();
                } else {
                    this.player.get().won((int) this.timer.getValue());
                }
                writePLayerScore();
            }
    }

    private void writePLayerScore() {
        final ScoreWriter scoreWriter = new ScoreWriterImpl();
        this.player.ifPresent(scoreWriter::write);
    }


    //BtnBAckHome
    public void backHome() throws IOException {
        closeElements();
        final RWSettings rwSett = new RWSettingsImpl();
        final Parent pane = FXMLLoader.load(ClassLoader.getSystemResource("layouts/playGame.fxml"));
        final Stage stage = (Stage) this.rootPane.getScene().getWindow();
        final Scene scene = new Scene(pane, stage.getScene().getWidth(), stage.getScene().getHeight());
        scene.getStylesheets().add(ClassLoader.getSystemResource("css/" + rwSett.getCss()).toExternalForm());
        stage.setScene(scene);
    }

    //BtnRestart
/*    public void restart() throws IOException{
    }*/

    //BtnSong
    private void checkSong() {
        if(playing) {
            btnSong.setText("PLAY MUSIC");
            clip.stop();
        } else {
            btnSong.setText("STOP MUSIC");
            clip.start();
        }
        this.playing = !this.playing ;
    }

    private void tileEffect(final Tile tile) {

        FadeTransition fade = new FadeTransition();
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setDuration(Duration.millis(1000));
        fade.setNode(tile);

        TranslateTransition transition = new TranslateTransition();
        transition.setByY(200);
        transition.setDuration(Duration.millis(3000));
        transition.setNode(tile);
        transition.play();
        fade.play();


    }
    private void tileFade(final Tile tile) {

        FadeTransition fade = new FadeTransition();
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setDuration(Duration.millis(500));
        fade.setNode(tile);
    }

    private void closeElements() {
        closeTimer();
        closeSong();
    }

    private void closeTimer() {
        timer.stop();
    }

    public void closeSong() {
        clip.stop();
        clip.close();
    }



    @Override
    public void setPlayer(Optional<Player> player) {
        this.player = player;

    }
}