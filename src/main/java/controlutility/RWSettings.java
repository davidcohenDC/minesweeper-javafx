package controlutility;

/**
 * read and write on settings.txt file.
 */
public interface RWSettings {
    /**
     * set the first color.
     * 
     * @param color is the first color
     */
    void setFirstColor(String color);

    /**
     * set the second color.
     * 
     * @param color is the second color
     */
    void setSecondColor(String color);

    /**
     * set the song.
     * 
     * @param song is the song
     */
    void setSong(String song);

    /**
     * set the mine.
     * 
     * @param mine is the mine
     */
    void setMines(String mine);

    /**
     * set the flag.
     * 
     * @param flag is the flag
     */
    void setFlags(String flag);

    /**
     * @return the first color saved.
     */
    String getFirstColor();

    /**
     * @return the second color saved.
     */
    String getSecondColor();

    /**
     * @return the song saved.
     */
    String getSong();

    /**
     * @return the mine image saved.
     */
    String getMines();

    /**
     * @return the flag image saved.
     */
    String getFlags();

}
