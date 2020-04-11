package graphics;

import controlutility.RWSettings;
import controlutility.RWSettingsImpl;
import gamelogics.*;
import graphicsutility.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import scoresystem.Player;
import scoresystem.ScoreWriter;
import scoresystem.ScoreWriterImpl;
import timer.*;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class AbstractGameController implements GameController{


    @FXML
    private AnchorPane rootPane;
    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private Label lbTimer = new Label();

    public AbstractGameController(final int height, final int width, final int mines, final Timer timer){

    }



    @Override
    public abstract void leftClickHandler(final Tile tile, final int x, final int y);

    @Override
    public abstract void rightClickHandler(final Tile tile, final int x, final int y);

    @Override
    public abstract void initialize() throws IOException;

    @Override
    public abstract void btnActions();

    public abstract void setClickHandler();

    public abstract void refreshBoard();

    public abstract void showLostBoard(final Box box, final Tile tmpTile);

    public abstract void endGame(final GameStatus gameStatus);

    public abstract void setPlayer(final GameStatus status);

    public abstract void closeElements();


}
