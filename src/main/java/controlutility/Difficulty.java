package controlutility;
/**
 **/
public enum Difficulty {
    /**
     * Type of difficulty.
     * */
    EASY("Easy"), MEDIUM("Medium"), HARD("Hard"), PERSONALIZED("Personalized");
    private final String name;

    Difficulty(final String name) {
        this.name = name;
    }
    /**
     * @return the name of modality*/
    public String getName() {
        return name;
    }
}
