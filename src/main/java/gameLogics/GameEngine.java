package gameLogics;

/**
 * Interface for game managing
 * */
public interface GameEngine {

    /**
     * Set the kind of game engine per Modality
     * @param mod indicates game modality
     */
    void setGameModality(GameModality mod);
}
