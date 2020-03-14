package scoresystem;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class ScoreWriterImpl implements ScoreWriter {

    //separates the player id from his score
    private static final String PLAYER_SEPARATOR = ":";
    //separates each score form each other based on the difficulty 
    private static final String DIFFICULTY_SEPARATOR = "-"; 

    private static final String SEPARATOR = System.getProperty("file.separator");
    private static final String ROOT = System.getProperty("user.home") + SEPARATOR + ".minesweeper" + SEPARATOR + "score_files" + SEPARATOR;
    
    private final Player player;
    private final int previousHighScore;
    private final File scoreFile;
    private final Set<String> fileLines;
    
    /**
     * Sets up the score writing process.
     * @param player
     * The player to register the score
     */
    protected ScoreWriterImpl(final Player player) {
        this.fileLines = null;
        this.player = player;
        this.scoreFile = new File(ROOT + player.getModality().getFileName());
        if(!scoreFile.exists()) {
            try {
                createNewScoreFile();
            } catch (IOException e) {
                System.err.println("Could not create new file");
            }
        }
//        this.fileLines = 
        this.previousHighScore = 0;
    }

    private void createNewScoreFile() throws IOException {
        scoreFile.createNewFile();
    }

    private boolean isScoreWritable() {
        if(this.previousHighScore < this.player.getScore()) {
            
        }
        return false;
    }

    @Override
    public void writeScore() {
        // TODO Auto-generated method stub
        
    }
}
