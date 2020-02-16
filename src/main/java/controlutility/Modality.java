package controlutility;
/**
 * */

public enum Modality {
    /**
     * Type of modality.
     */
    STANDARD("Standard"), ONE_VS_ONE("One vs One"), BTT("Beat the Timer");

    private final String name;

    Modality(final String name) {
        this.name = name;
    }
    /**
     * @return the name of modality*/
    public String getName() {
        return name;
    }

}
