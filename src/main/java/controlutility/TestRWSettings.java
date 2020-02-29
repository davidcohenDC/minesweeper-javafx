package controlutility;
import static org.junit.Assert.assertTrue;
import java.io.IOException;

//import org.junit.Test;
import org.junit.jupiter.api.Test;

class TestRWSettings {
    private static RWSettings rwSett;

    @org.junit.jupiter.api.BeforeAll
    public static void initialize() {
        final LoadData ld = new LoadDataImpl();
        try {
            ld.loadData();
            rwSett = new RWSettingsImpl();
        } catch (IOException e) {
            e.printStackTrace();
        }
     }

    @Test
    public void cssTest() throws IOException {
        assertTrue(rwSett.getCss().contains(".css"));
        rwSett.setCss("pink.css");
        assertTrue(rwSett.getCss().equals("pink.css"));
        rwSett.setCss("orange.css");
        assertTrue(rwSett.getCss().equals("orange.css"));
        rwSett.setCss("green.css");
        assertTrue(rwSett.getCss().equals("green.css"));
    }

    @Test
    public void imgMineTest() throws IOException {
        assertTrue(rwSett.getMines().contains(".png") || rwSett.getMines().contains(".jpg") || rwSett.getMines().contains(".gif"));
        rwSett.setMines("bomba.png");
        assertTrue(rwSett.getMines().equals("bomba.png"));
    }

    @Test
    public void imgFlagsTest() throws IOException {
        assertTrue(rwSett.getFlags().contains(".png") || rwSett.getFlags().contains(".jpg") || rwSett.getFlags().contains(".gif"));
        rwSett.setFlags("puntina_verde.png");
        assertTrue(rwSett.getFlags().equals("puntina_verde.png"));
    }

    @Test
    public void imgSoundTest() throws IOException {
        assertTrue(rwSett.getSong().contains(".wav"));
        rwSett.setSong("prova1.wav");
        assertTrue(rwSett.getSong().equals("prova1.wav"));
    }

    @Test
    public void allTest() throws IOException {
        rwSett.setSong("prova2.wav");
        rwSett.setCss("blue.css");
        rwSett.setMines("panda.png");
        rwSett.setFlags("bianca.png");
        final RWSettings rwSett2 = new RWSettingsImpl();
        assertTrue(rwSett2.getCss().equals("blue.css"));
        assertTrue(rwSett2.getFlags().equals("bianca.png"));
        assertTrue(rwSett2.getMines().equals("panda.png"));
        assertTrue(rwSett2.getSong().equals("prova2.wav"));
    }

}
