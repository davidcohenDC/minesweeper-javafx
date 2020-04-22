package graphicsutility;


/**
 * The interface for manage Song.
 */
public interface SongAgent {

     /**
      * Play the song.
      */
     void play();

     /**
      * Pause the song.
      */
     void pause();

     /**
      * Check if the Song is playing.
      *
      * @return the status of the music
      */
     Boolean isPlaying();

     /**
      * Close the song.
      */
     void close();

}
