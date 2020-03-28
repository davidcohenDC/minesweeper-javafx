package controllers;

import java.io.IOException;

/**Interface for the main Statistic Controller.*/
public interface MainStatisticsControllerInterface {

    /** initialize fields.
     * @throws IOException */
    void initialize() throws IOException;
    /**
     * The handler for 'STANDARD' button.
     * @exception IOException
     *                            if an I/O error occurs.
     */
    void btStandard() throws IOException;
    /**
     * The handler for  '1 VS 1' button.
     * @exception IOException
     *                            if an I/O error occurs.
     */
    void bt1vs1() throws IOException;
    /**
     * The handler for 'BEAT THE TIMER' button.
     * @exception IOException
     *                            if an I/O error occurs.
     */
    void btBtt() throws IOException;
}
