package controlutility;

/**
 * read and write on settings.txt file.
 */
public interface RWSettings {
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
     * set css stylesheet.
     * @param css styleshet
     **/
    void setCss(String css);

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

    /**
     * @return css stylesheet saved.
     */
    String getCss();

    /**
     * @return the click's sound saved.
     */
    String getClick();


}
