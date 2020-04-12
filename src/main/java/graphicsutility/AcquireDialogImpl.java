package graphicsutility;


import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import org.checkerframework.checker.nullness.Opt;


import java.util.Optional;

public class AcquireDialogImpl implements AcquireDialog{
    private TextInputDialog dialogSinglePlayer = new TextInputDialog("");
    //Dialog<Pair<String, String>> dialogMultiplayer = new Dialog<>();

    public AcquireDialogImpl() {
        dialogSinglePlayer.setResizable(false);

    }

    @Override
    public Optional<String> acquireFirst() {

        this.dialogSinglePlayer.setTitle("| PLAYER | ");
        this.dialogSinglePlayer.setHeaderText("Input your nickname to save your score");
        this.dialogSinglePlayer.setContentText("Player: ");
        return dialogSinglePlayer.showAndWait();
    }

    public Optional<String> acquireSecond() {

        this.dialogSinglePlayer.setTitle("| PLAYER 2 | ");
        this.dialogSinglePlayer.setHeaderText("Input your nickname to save your score ");
        this.dialogSinglePlayer.setContentText("Player2: ");

        return dialogSinglePlayer.showAndWait();
    }

}
