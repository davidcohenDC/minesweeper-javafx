package gamegraphics;

import controlutility.RWSettings;
import controlutility.RWSettingsImpl;

import gamelogics.GameEngine;
import gamelogics.GameEngineImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;



public class SinglePlayerController implements ModalityController{
    private GameEngine engine;

    @FXML
    private AnchorPane rootPane;

}
