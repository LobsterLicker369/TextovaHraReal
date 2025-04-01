package World;

import java.util.Arrays;

/**
 * Trida reprezentujici mistnost ve hre.
 * Obsahuje informace o nazvu mistnosti, jejim ID a propojenych mistnostech.
 */
public class Location {
    private String name;
    private int id;
    private int[] locations;

    /**
     * Konstruktor tridy Location.
     *
     * @param name název mistnosti
     * @param id ID mistnosti
     * @param locations seznam propojenych mistnosti (pomoci jejich ID)
     */
    public Location(String name, int id, int[] locations) {
        this.name = name;
        this.id = id;
        this.locations = locations;
    }

    /**
     * Vraci nazev mistnosti.
     *
     * @return nazev mistnosti
     */
    public String getName() {
        return name;
    }

    /**
     * Vraci ID mistnosti.
     *
     * @return ID mistnosti
     */
    public int getId() {
        return id;
    }

    /**
     * Vraci seznam propojenych mistnosti podle jejich ID.
     *
     * @return pole ID propojenych mistnosti
     */
    public int[] getLocations() {
        return locations;
    }

    /**
     * Vraci retezec obsahujici informace o mistnosti.
     * Zahrnuje nazev mistnosti, ID a propojene mistnosti.
     *
     * @return informace o mistnosti ve formatu retezce
     */
    @Override
    public String toString() {
        return "Mistnost: " + name + ", ID: " + id + ", propojeno: " + Arrays.toString(locations);
    }
}
