package controllers;

import java.util.EnumMap;
import java.util.Optional;

import controlutility.Modality;
import controlutility.Difficulty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Alert.AlertType;

/**
 * The Controller related to the playGame.fxml GUI.
 *
 */
public class PlayGameController extends BackHomeController {
    /**number of min mines.
     * */
    public static final int MIN_MINES = 10;
    /** number of max mines.
     * */
    public static final int MAX_MINES = 667;
    /**number of min width.
     * */
    public static final int MIN_WIDTH = 9;
    /** number of max width.
     * */
    public static final int MAX_WIDTH = 30;
    /**number of min height.
     * */
    public static final int MIN_HEIGHT = 9;
    /** number of max height.
     * */
    public static final int MAX_HEIGHT = 24;

    private EnumMap<Modality, Boolean> btsModality = new EnumMap<>(Modality.class);
    private EnumMap<Difficulty, Boolean> btsDifficulty = new EnumMap<>(Difficulty.class);
    private Optional<Modality> modality;
    private Optional<Difficulty> difficulty;
    private boolean personalized; // abilita/ disabilita gli spinner

    @FXML
    private RadioButton rbtStd = new RadioButton();
    @FXML
    private RadioButton rbtOnevsOne = new RadioButton();
    @FXML
    private RadioButton rbtBtt = new RadioButton();

    @FXML
    private RadioButton rbtEasy = new RadioButton();
    @FXML
    private RadioButton rbtMedium = new RadioButton();
    @FXML
    private RadioButton rbtHard = new RadioButton();
    @FXML
    private RadioButton rbtPersonalized = new RadioButton();

    @FXML
    private Spinner<Integer> sMines = new Spinner<>();
    @FXML
    private Spinner<Integer> sWidth = new Spinner<>();
    @FXML
    private Spinner<Integer> sHeight = new Spinner<>();

    /**
     * Initialize fiels to start.
     */

    public void initialize() {
        this.btsModality.put(Modality.STANDARD, false);
        this.btsModality.put(Modality.ONE_VS_ONE, false);
        this.btsModality.put(Modality.BTT, false);

        this.btsDifficulty.put(Difficulty.EASY, false);
        this.btsDifficulty.put(Difficulty.MEDIUM, false);
        this.btsDifficulty.put(Difficulty.HARD, false);
        this.btsDifficulty.put(Difficulty.PERSONALIZED, false);
        this.personalized = false;
        this.modality = Optional.empty();
        this.difficulty = Optional.empty();
        //
        this.sMines.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(MIN_MINES, MAX_MINES, MIN_MINES));
        this.sWidth.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(MIN_WIDTH, MAX_WIDTH, MIN_WIDTH));
        this.sHeight.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(MIN_HEIGHT, MAX_HEIGHT, MIN_HEIGHT));
        this.setSpinner();
    }

    private void loadModality(final Modality mod) {
        if (this.btsModality.get(mod)) {
            this.btsModality.replace(mod, false);
            this.modality = Optional.empty();
        } else {
            if (this.btsModality.containsValue(true)) {
                Modality key = this.btsModality.entrySet().stream().filter(m -> m.getValue()).map(m -> m.getKey()).findAny()
                        .get();
                this.btsModality.replace(key, false);
            }
            this.btsModality.replace(mod, true);
            this.modality = Optional.of(mod);
        }
        setStatusMod();
    }

    private void setStatusMod() {
        this.rbtStd.setSelected(this.btsModality.get(Modality.STANDARD));
        this.rbtOnevsOne.setSelected(this.btsModality.get(Modality.ONE_VS_ONE));
        this.rbtBtt.setSelected(this.btsModality.get(Modality.BTT));
        //System.out.println(modality);
    }

    private void loadDifficulty(final Difficulty diff) {
        if (this.btsDifficulty.get(diff)) {
            this.btsDifficulty.replace(diff, false);
            this.difficulty = Optional.empty();
        } else {
            if (this.btsDifficulty.containsValue(true)) {
                Difficulty key = this.btsDifficulty.entrySet().stream().filter(m -> m.getValue()).map(m -> m.getKey()).findAny()
                        .get();
                this.btsDifficulty.replace(key, false);
            }
            this.btsDifficulty.replace(diff, true);
            this.difficulty = Optional.of(diff);
        }
        setStatusDiff();
    }

    private void setStatusDiff() {
        this.rbtEasy.setSelected(this.btsDifficulty.get(Difficulty.EASY));
        this.rbtMedium.setSelected(this.btsDifficulty.get(Difficulty.MEDIUM));
        this.rbtHard.setSelected(this.btsDifficulty.get(Difficulty.HARD));
        this.rbtPersonalized.setSelected(this.btsDifficulty.get(Difficulty.PERSONALIZED));
        this.personalized = this.btsDifficulty.get(Difficulty.PERSONALIZED);
        //System.out.println(difficulty + "personalized = " + personalized);
    }

    @FXML
    private void rbtStandard(final ActionEvent event) {
        event.consume();
        check();
        this.loadModality(Modality.STANDARD);
    }

    @FXML
    private void rbtOnevsOne(final ActionEvent event) {
        event.consume();
        check();
        this.loadModality(Modality.ONE_VS_ONE);
    }

    @FXML
    private void rbtBtt(final ActionEvent event) {
        event.consume();
        check();
        this.loadModality(Modality.BTT);
    }

    @FXML
    private void rbtEasy(final ActionEvent event) {
        event.consume();
        checkDiff();
        this.loadDifficulty(Difficulty.EASY);
    }

    @FXML
    private void rbtMedium(final ActionEvent event) {
        event.consume();
        checkDiff();
        this.loadDifficulty(Difficulty.MEDIUM);
    }

    @FXML
    private void rbtHard(final ActionEvent event) {
        event.consume();
        checkDiff();
        this.loadDifficulty(Difficulty.HARD);
    }

    @FXML
    private void rbtPersonalized(final ActionEvent event) {
        event.consume();
        checkDiff();
        this.loadDifficulty(Difficulty.PERSONALIZED);
        this.setSpinner();
    }


    private void setSpinner() {
        if (this.personalized) {
            this.sMines.setDisable(false);
            this.sHeight.setDisable(false);
            this.sWidth.setDisable(false);
        } else {
            this.sMines.setDisable(true);
            this.sHeight.setDisable(true);
            this.sWidth.setDisable(true);
        }
    }

    @FXML
    private void btPlay(final ActionEvent event) {
        event.consume();
        if (this.modality.equals(Optional.empty()) || this.difficulty.equals(Optional.empty())) {
            System.out.println("non puoi giocare");
            final Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error...");
            alert.setContentText("You haven't select the modality or the difficulty");
            alert.showAndWait();
        } else {
            System.out.println("sono playButton");
            System.out.println("modalità " + modality + " difficoltà " + difficulty);
            if (this.personalized) {
                System.out.println("mines: " + sMines.getValue());
                System.out.println("width: " + sWidth.getValue());
                System.out.println("height: " + sHeight.getValue());
            }
            // TODO chiama metodo per gioco
        }
    }

    private void check() {
        if (this.btsModality.containsValue(true) && this.modality.equals(Optional.empty())) {
            throw new IllegalStateException();
        }
    }

    private void checkDiff() {
        if (this.btsDifficulty.containsValue(true) && this.difficulty.equals(Optional.empty())) {
            throw new IllegalStateException();
        }
    }

}
