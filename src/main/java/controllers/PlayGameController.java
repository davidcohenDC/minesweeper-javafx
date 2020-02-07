package controllers;

import java.util.EnumMap;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Alert.AlertType;

/**
 * The Controller related to the playGame.fxml GUI.
 *
 */
public class PlayGameController extends BackHomeController {

    private enum Modality {
        STANDARD, ONE_VS_ONE, BTT;
    }

    private EnumMap<Modality, Boolean> btsModality = new EnumMap<>(Modality.class);
    private Optional<Modality> modality;
    private Optional<String> difficulty;

    @FXML
    private RadioButton rbtStd = new RadioButton();
    @FXML
    private RadioButton rbtOnevsOne = new RadioButton();
    @FXML
    private RadioButton rbtBtt = new RadioButton();

    /**
     * Initialize fiels to start.
     */

    public void initialize() {
        this.btsModality.put(Modality.STANDARD, false);
        this.btsModality.put(Modality.ONE_VS_ONE, false);
        this.btsModality.put(Modality.BTT, false);
        this.modality = Optional.empty();
        this.difficulty = Optional.empty();
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
        System.out.println(modality);
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
    private void btPlay(final ActionEvent event) {
        event.consume();
        if (this.modality.equals(Optional.empty()) || this.difficulty.equals(Optional.empty())) {
            System.out.println("non puoi giocare");
            final Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error...");
            alert.setContentText("You haven't select the modality or the difficulty");
            alert.showAndWait();
        }
        System.out.println("sono playButton");
        // TODO chiama metodo per gioco
    }

    private void check() {
        if (this.btsModality.containsValue(true) && this.modality.equals(Optional.empty())) {
            throw new IllegalStateException();
        }
    }

}
