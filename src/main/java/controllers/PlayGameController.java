package controllers;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Optional;

import controlutility.Modality;
import controlutility.AlertStyle;
import controlutility.AlertStyleImpl;
import controlutility.Difficulty;
import graphics.ContextGraphics;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * The Controller related to the playGame.fxml GUI.
 * The implementation of {@link PlayGameInterface }.
 */
public final class PlayGameController extends BackHomeController implements PlayGameInterface {
    private static final int MIN_MINES = 10;
    private static final int MAX_MINES = 667;
    private static final int HARD_MINES = 99;
    private static final int MEDIUM_MINES = 40;
    private static final int MIN_WIDTH = 9;
    private static final int MAX_WIDTH = 30;
    private static final int MIN_HEIGHT = 9;
    private static final int MAX_HEIGHT = 24;
    private static final int MEDIUM_SIZE = 16;

    private final EnumMap<Modality, Boolean> btsModality = new EnumMap<>(Modality.class);
    private final EnumMap<Difficulty, Boolean> btsDifficulty = new EnumMap<>(Difficulty.class);
    private Optional<Modality> modality;
    private Optional<Difficulty> difficulty;
    private boolean personalized; // abilita/ disabilita gli spinner
    private Alert alert;
    private int width;
    private int height;
    private int mines;

    @FXML
    private RadioButton rbtStd;
    @FXML
    private RadioButton rbtOnevsOne;
    @FXML
    private RadioButton rbtBtt;

    @FXML
    private  RadioButton rbtEasy;
    @FXML
    private  RadioButton rbtMedium;
    @FXML
    private  RadioButton rbtHard;
    @FXML
    private  RadioButton rbtPersonalized;

    @FXML
    private  TextField tfMines;
    @FXML
    private  TextField tfWidth;
    @FXML
    private  TextField tfHeight;

    @Override
    public void initialize() throws IOException {
        this.populateBtsModality();
        this.populateBtsDifficulty();

        this.personalized = false;
        this.modality = Optional.empty();
        this.difficulty = Optional.empty();
        this.enableTextField();
        final AlertStyle alStyle = new AlertStyleImpl();
        this.alert = new Alert(AlertType.ERROR);
        alStyle.setStyle(alert);
        alert.setTitle("Error");
        alert.setHeaderText(null);
    }

    /**Associate to each Difficulty button the respective difficulty and status (select or not select).*/
    private void populateBtsDifficulty() {
        this.btsDifficulty.put(Difficulty.EASY, false);
        this.btsDifficulty.put(Difficulty.MEDIUM, false);
        this.btsDifficulty.put(Difficulty.HARD, false);
        this.btsDifficulty.put(Difficulty.PERSONALIZED, false);

    }

    /**Associate to each Modality button the respective modality and status (select or not select).*/
    private void populateBtsModality() {
        this.btsModality.put(Modality.STANDARD, false);
        this.btsModality.put(Modality.ONE_VS_ONE, false);
        this.btsModality.put(Modality.BTT, false);
    }

    /**Enable and disable text field according to the selection of personalize difficulty.*/
    private void enableTextField() {
        if (this.personalized) {
            this.tfMines.setDisable(false);
            this.tfHeight.setDisable(false);
            this.tfWidth.setDisable(false);
        } else {
            this.tfMines.setText(String.valueOf(MIN_MINES));
            this.tfHeight.setText(String.valueOf(MIN_HEIGHT));
            this.tfWidth.setText(String.valueOf(MIN_WIDTH));

            this.tfMines.setDisable(true);
            this.tfHeight.setDisable(true);
            this.tfWidth.setDisable(true);
        }
    }

    /**Update modality according to the selection.
     * @param mod new chose modality*/
    private void loadModality(final Modality mod) {
        if (this.btsModality.get(mod)) {
            this.btsModality.replace(mod, false);
            this.modality = Optional.empty();
        } else {
            if (this.btsModality.containsValue(true)) {
                final Modality key = this.btsModality.entrySet().stream().filter(m -> m.getValue()).map(m -> m.getKey()).findAny()
                        .get();
                this.btsModality.replace(key, false);
            }
            this.btsModality.replace(mod, true);
            this.modality = Optional.of(mod);
        }
        setStatusMod();
    }

    /**Set and unset modalities radio buttons.*/
    private void setStatusMod() {
        this.rbtStd.setSelected(this.btsModality.get(Modality.STANDARD));
        this.rbtOnevsOne.setSelected(this.btsModality.get(Modality.ONE_VS_ONE));
        this.rbtBtt.setSelected(this.btsModality.get(Modality.BTT));
    }

    /**Update difficulty according to the selection.
     * @param diff new chose difficulty*/
    private void loadDifficulty(final Difficulty diff) {
        if (this.btsDifficulty.get(diff)) {
            this.btsDifficulty.replace(diff, false);
            this.difficulty = Optional.empty();
        } else {
            if (this.btsDifficulty.containsValue(true)) {
                final Difficulty key = this.btsDifficulty.entrySet().stream().filter(m -> m.getValue()).map(m -> m.getKey()).findAny()
                        .get();
                this.btsDifficulty.replace(key, false);
            }
            this.btsDifficulty.replace(diff, true);
            this.difficulty = Optional.of(diff);
        }
        setStatusDiff();
    }

    /**Set and unset difficulties radio buttons.*/
    private void setStatusDiff() {
        this.rbtEasy.setSelected(this.btsDifficulty.get(Difficulty.EASY));
        this.rbtMedium.setSelected(this.btsDifficulty.get(Difficulty.MEDIUM));
        this.rbtHard.setSelected(this.btsDifficulty.get(Difficulty.HARD));
        this.rbtPersonalized.setSelected(this.btsDifficulty.get(Difficulty.PERSONALIZED));
        this.personalized = this.btsDifficulty.get(Difficulty.PERSONALIZED);
    }

    @FXML
    @Override
    public void rbtStandard() {
        check();
        this.loadModality(Modality.STANDARD);
    }

    @FXML
    @Override
    public void rbtOnevsOne() {
        check();
        this.loadModality(Modality.ONE_VS_ONE);
    }

    @FXML
    @Override
    public void rbtBtt() {
        check();
        this.loadModality(Modality.BTT);
    }

    @FXML
    @Override
    public void rbtEasy() {
        checkDiff();
        this.loadDifficulty(Difficulty.EASY);
        this.width = MIN_HEIGHT;
        this.height = MIN_WIDTH;
        this.mines = MIN_MINES;
        this.enableTextField();
    }

    @FXML
    @Override
    public void rbtMedium() {
        checkDiff();
        this.loadDifficulty(Difficulty.MEDIUM);
        this.width = MEDIUM_SIZE;
        this.height = MEDIUM_SIZE;
        this.mines = MEDIUM_MINES;
        this.enableTextField();
    }

    @FXML
    @Override
    public void rbtHard() {
        checkDiff();
        this.loadDifficulty(Difficulty.HARD);
        this.width = MAX_WIDTH;
        this.height = MEDIUM_SIZE;
        this.mines = HARD_MINES;
        this.enableTextField();
    }

    @FXML
    @Override
    public void rbtPersonalized() {
        checkDiff();
        this.mines = Integer.parseInt(this.tfMines.getText());
        this.height = Integer.parseInt(this.tfHeight.getText());
        this.width = Integer.parseInt(this.tfWidth.getText());
        this.loadDifficulty(Difficulty.PERSONALIZED);
        this.enableTextField();
    }

    @FXML
    @Override
    public void tfCheckMines() {
        try {
            if (!this.tfMines.getText().isEmpty()) {
                Integer.parseInt(this.tfMines.getText());
                this.mines = Integer.parseInt(this.tfMines.getText());
            }
        } catch (NumberFormatException e) {
            this.alertNumberFormat();
            this.tfMines.setText(String.valueOf(MIN_MINES));
        }
    }

    @FXML
    @Override
    public void tfCheckHeight() {
        try {
            if (!this.tfHeight.getText().isEmpty()) {
                Integer.parseInt(this.tfHeight.getText());
                this.height = Integer.parseInt(this.tfHeight.getText());
            }
        } catch (NumberFormatException e) {
            this.alertNumberFormat();
            this.tfHeight.setText(String.valueOf(MIN_HEIGHT));
        }

    }

    @FXML
    @Override
    public void tfCheckWidth() {
        try {
            if (!this.tfWidth.getText().isEmpty()) {
                Integer.parseInt(this.tfWidth.getText());
                this.width = Integer.parseInt(this.tfWidth.getText());
            }
        } catch (NumberFormatException e) {
            this.alertNumberFormat();
            this.tfWidth.setText(String.valueOf(MIN_WIDTH));
        }
    }

    /**Set text and show the alert for 'only number format'.*/
    private void alertNumberFormat() {
        alert.setContentText("Only number format");
        alert.showAndWait();
    }

    @FXML
    @Override
    public void btPlay() {
        if (this.modality.equals(Optional.empty()) || this.difficulty.equals(Optional.empty())) {
            alert.setContentText("You haven't select the modality or the difficulty");
            alert.showAndWait();
        } else {
            if (this.checkRange()) {
                try {
                    final Stage stage = (Stage) this.rbtStd.getScene().getWindow();
                    new ContextGraphics(this.modality.get(), this.difficulty.get(), this.mines, this.height, this.width, stage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**Check if parameters of personalized modality are in the range.*/
    private boolean checkRange() {
        if (this.mines < MIN_MINES || this.mines > MAX_MINES || this.height < MIN_HEIGHT || this.height > MAX_HEIGHT || this.width < MIN_WIDTH
                || this.width > MAX_WIDTH) {
            alert.setContentText("number of Mines or Height or Width out of range");
            alert.showAndWait();
        } else if (this.mines >= this.height * this.width) {
            alert.setContentText("there are too much mines");
            alert.showAndWait();
        }
        return this.mines >= MIN_MINES && this.mines <= MAX_MINES && this.height >= MIN_HEIGHT && this.height <= MAX_HEIGHT
                && this.width >= MIN_WIDTH && this.width <= MAX_WIDTH && this.mines <= this.height * this.width;
    }

    /**Check if one modality radio button is select and the field is not empty.*/
    private void check() {
        if (this.btsModality.containsValue(true) && this.modality.equals(Optional.empty())) {
            throw new IllegalStateException();
        }
    }

    /**Check if one difficulty radio button is select and the field is not empty.*/
    private void checkDiff() {
        if (this.btsDifficulty.containsValue(true) && this.difficulty.equals(Optional.empty())) {
            throw new IllegalStateException();
        }
    }

}
