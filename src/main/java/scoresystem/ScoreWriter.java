package scoresystem;

import java.util.Map;

import controlutility.Difficulty;
import controlutility.Modality;

public interface ScoreWriter extends Writer {
 
    Map<String, Integer> getScoreBoard(Modality gameMode, Difficulty difficulty);
}
