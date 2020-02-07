package controlutility;

import java.util.List;

/**
 * Interface to read rules.txt.
 * */

public interface ReadRules {
    /**
     * @return a list of all lines of a file.
     * */
    List<String> getAllLines();
}
