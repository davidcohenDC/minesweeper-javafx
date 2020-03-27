package scoresystem;

import java.util.Map;

public interface ScoreWriter extends Writer {

    Map<String, Integer> getScoreBoard();
}
