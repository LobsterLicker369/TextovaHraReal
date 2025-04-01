package command;

import World.Location;
import World.WorldMap;

/**
 * Trida pro vykonani prikazu presunu do jine mistnosti ve hre.
 */
public class GoToCommand {
    private final WorldMap worldMap;
    private final String destinationName;

    /**
     * Konstruktor pro vytvoreni prikazu presunu.
     *
     * @param worldMap mapa sveta
     * @param destinationName nazev cilove mistnosti
     */
    public GoToCommand(WorldMap worldMap, String destinationName) {
        this.worldMap = worldMap;
        this.destinationName = destinationName;
    }

    /**
     * Vykona presun do zadane mistnosti, pokud je dostupna.
     *
     * @return zpravu o vysledku presunu
     */
    public String execute() {
        Location currentLocation = worldMap.getCurrentLocation();
        Location targetLocation = null;

        // Hledani cilove mistnosti
        for (Location location : worldMap.getLocations().values()) {
            if (location.getName().equalsIgnoreCase(destinationName)) {
                targetLocation = location;
                break;
            }
        }

        // Pokud mistnost neexistuje
        if (targetLocation == null) {
            return "Tato mistnost neexistuje.";
        }

        // Kontrola, zda je uzivatel v cilove mistnosti
        if (currentLocation.getId() == targetLocation.getId()) {
            return "Uz se nachazis v teto mistnosti.";
        }

        // Kontrola, zda je mozne se presunout
        if (worldMap.canMoveTo(targetLocation.getId())) {
            worldMap.moveTo(targetLocation);
            return "Presunuto na mistnost: " + targetLocation.getName();
        } else {
            return "Tato mistnost neni primo dostupna.";
        }
    }
}
