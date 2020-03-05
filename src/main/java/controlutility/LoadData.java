package controlutility;

import java.io.IOException;

/** Interface that create resources folder in local.
 * */
public interface LoadData {
    /**
     * create if doesn't exist the resource folder.
     * @throws IOException 
     */
   void loadData() throws IOException;
}
