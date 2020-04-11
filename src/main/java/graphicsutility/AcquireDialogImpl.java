package graphicsutility;


import javafx.geometry.Pos;
import javafx.scene.control.TextInputDialog;


import java.util.Optional;

public class AcquireDialogImpl implements AcquireDialog{
    private TextInputDialog dialog = new TextInputDialog("");

    public AcquireDialogImpl() {
        dialog.setResizable(false);
    }

    @Override
    public Optional<String> Acquire() {

        this.dialog.setTitle("| SAVE YOUR SCORE |");
        this.dialog.setHeaderText("Input your nickname if you want to save your score :)");
        this.dialog.setContentText("Enter your nickname: ");

        final Optional<String> playerName = dialog.showAndWait();
        return playerName;
    }


}
