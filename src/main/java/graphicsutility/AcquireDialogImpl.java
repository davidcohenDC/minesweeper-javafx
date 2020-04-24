package graphicsutility;

import java.util.Optional;

import javafx.scene.control.TextInputDialog;

/**
 * The implementation of {@link AcquireDialog}.
 */
public class AcquireDialogImpl implements AcquireDialog {
    private final TextInputDialog dialogSinglePlayer = new TextInputDialog("");

    public AcquireDialogImpl() {
        dialogSinglePlayer.setResizable(false);
    }

    @Override
    public final Optional<String> acquireFirst() {
        this.dialogSinglePlayer.setTitle("| PLAYER | ");
        this.dialogSinglePlayer.setHeaderText("Input your nickname to save your score");
        this.dialogSinglePlayer.setContentText("Player: ");
        return dialogSinglePlayer.showAndWait();
    }

    @Override
    public final Optional<String> acquireSecond() {
        this.dialogSinglePlayer.setTitle("| PLAYER 2 | ");
        this.dialogSinglePlayer.setHeaderText("Input your nickname to save your score ");
        this.dialogSinglePlayer.setContentText("Player2: ");
        return dialogSinglePlayer.showAndWait();
    }

}
