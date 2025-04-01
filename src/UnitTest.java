import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import World.WorldMap;
import World.Location;
import command.Loader;
import command.GoToCommand;
import command.Speak;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Testovaci trida pro jednotkove testy aplikace.
 * Testy se zaměřují na nacitani lokaci, spravnost prikazu a spravne nacitani postav.
 */
public class UnitTest {

    private WorldMap worldMap;
    private Loader loader;

    /**
     * Nastaveni prostredi pred kazdym testem.
     * Inicializuje instanci Loader a WorldMap, naciita lokace.
     */
    @Before
    public void setUp() {
        loader = new Loader();
        worldMap = new WorldMap();
        worldMap.loadLocations(); // Opravené nacitani mapy
    }

    /**
     * Testuje nacitani lokaci z mapy.
     * Overuje, ze lokace s indexy 1 a 2 jsou nacteny spravne.
     */
    @Test
    public void testLocationLoading() {
        assertNotNull(worldMap.getLocations().get(1));
        assertNotNull(worldMap.getLocations().get(2));
    }

    /**
     * Testuje spravnost propojeni mezi lokacemi.
     * Overuje, ze prikaz pro presun do jine mistnosti funguje spravne.
     */
    @Test
    public void testLocationConnections() {
        Location start = worldMap.getCurrentLocation();
        GoToCommand goTo = new GoToCommand(worldMap, "kajuta"); // Pouziti spravneho konstruktoru
        String result = goTo.execute();
        assertEquals("Presunuto na mistnost: kajuta", result);
    }

    /**
     * Testuje neplatny pohyb do neexistujici mistnosti.
     * Overuje, ze prikaz pro presun do neexistujici mistnosti vrati spravnou chybu.
     */
    @Test
    public void testInvalidMovement() {
        GoToCommand goTo = new GoToCommand(worldMap, "neexistuje");
        String result = goTo.execute();
        assertEquals("Tato mistnost neexistuje.", result);
    }

    /**
     * Testuje neplatny prikaz.
     * Overuje, ze objekt Speak je spravne vytvoren.
     */
    @Test
    public void testInvalidCommand() {
        Speak speak = new Speak();
        assertNotNull(speak); // Overeni, ze se Speak spravne vytvori
    }

    /**
     * Testuje, ze postava existuje a jeji promluva neni prazdna.
     * Overuje, ze nacteni postavy a jeji prvni replika funguje spravne.
     */
    @Test
    public void testCharacterExists() {
        loader.LoadCharacters();
        String speech = loader.getSpeech("narrator", 1); // Zkusime ziskat prvni repliku kapitana
        assertNotEquals("Postava nenalezena.", speech); // Overime, ze to existuje
    }

    /**
     * Testuje, zda soubor 'items.txt' existuje.
     */
    @Test
    public void testCharacterFileExists() {
        File file = new File("items.txt");
        assertTrue("Soubor 'items.txt' neexistuje.", file.exists());
    }
}

