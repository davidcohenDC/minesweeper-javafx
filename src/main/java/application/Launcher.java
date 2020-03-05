package application;

import java.io.IOException;

/**
 * This class represents the Launcher of the system, to bypass JAVA 11 modules constraints.
 */
public final class Launcher {


    private Launcher() { }

    /**
     * @param args unused
     * @throws IOException 
     */
    public static void main(final String[] args) throws IOException {
        Main.main(args);
    }

}
