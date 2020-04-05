package controllers;

import java.io.IOException;

/**Interface for the Statistic Controller.*/
public interface StatisticsControllerInterface {

    /**
     * initialize fields.
     */
    void initialize();
    /**
     * The handler for 'STATISTIC' button.
     * @exception IOException
     *                            if an I/O error occurs.
     */
    void btBackStat() throws IOException;
}
