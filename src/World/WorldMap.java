package World;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Trida reprezentujici mapu sveta.
 * Umoznuje nahravat, ukládat, a manipulovat s lokaci.
 */
public class WorldMap {
    private Location currentLocation;
    private Map<Integer, Location> locations;

    /**
     * Konstruktor tridy WorldMap.
     * Inicializuje mapu, resetuje a nahrava lokace, nastavuje pocatecni lokaci.
     */
    public WorldMap() {
        resetLocations(); // Reset pred startem hry
        locations = new HashMap<>();
        loadLocations();
        currentLocation = locations.get(1);
    }

    /**
     * Resetuje soubor "locations.txt" s preddefinovanymi hodnotami.
     * Zajistuje, ze pri spusteni hry budou k dispozici vychozi lokace.
     */
    private void resetLocations() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("locations.txt"))) {
            writer.write("paluba,1,1,2,3,6\n");
            writer.write("kajuta,2,1,0,3,4\n");
            writer.write("kuchyn,3,1,2,0,4\n");
            writer.write("strojovna,4,0,2,3,0\n");
            writer.write("zachrannyclun,5,0,0,0,0\n");
        } catch (IOException e) {
            System.err.println("Chyba pri resetovani lokaci: " + e.getMessage());
        }
    }

    /**
     * Nahrava lokace ze souboru "locations.txt".
     * Vytvari objekty Location a uklada je do mapy podle jejich ID.
     */
    public void loadLocations() {
        try (BufferedReader reader = new BufferedReader(new FileReader("locations.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                int id = Integer.parseInt(parts[1]);
                int[] connectedRooms = new int[4];

                for (int i = 0; i < 4; i++) {
                    connectedRooms[i] = Integer.parseInt(parts[i + 2]);
                }

                Location location = new Location(name, id, connectedRooms);
                locations.put(id, location);
            }
        } catch (IOException e) {
            System.err.println("Chyba pri nacitani souboru: " + e.getMessage());
        }
    }

    /**
     * Znovu nacte lokace z "locations.txt" po vymazani stavajicich hodnot.
     */
    public void reloadLocations() {
        locations.clear();
        loadLocations();
    }

    /**
     * Vytiskne seznam vsech lokaci na konzoli.
     */
    public void printLocations() {
        for (Location location : locations.values()) {
            System.out.println(location);
        }
    }

    /**
     * Ulozi vsechny lokace do souboru "locations.txt".
     */
    public void saveLocations() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("locations.txt"))) {
            for (Location location : locations.values()) {
                StringBuilder sb = new StringBuilder();
                sb.append(location.getName()).append(",")
                        .append(location.getId()).append(",")
                        .append(location.getLocations()[0]).append(",")
                        .append(location.getLocations()[1]).append(",")
                        .append(location.getLocations()[2]).append(",")
                        .append(location.getLocations()[3]);
                writer.write(sb.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Chyba pri ukladani lokaci: " + e.getMessage());
        }
    }

    /**
     * Napise do konzole jmeno aktualni mistnosti.
     */
    public void printCurrentLocation() {
        System.out.println("Aktualni mistnost: " + currentLocation.getName());
    }

    /**
     * Presun do nove mistnosti.
     *
     * @param newLocation nova mistnost, do ktere se presuneme
     */
    public void moveTo(Location newLocation) {
        this.currentLocation = newLocation;
    }

    /**
     * Zjisti, zda je mozne se presunout do mistnosti s danym ID.
     *
     * @param destinationId ID cilove mistnosti
     * @return true, pokud je mozny presun, jinak false
     */
    public boolean canMoveTo(int destinationId) {
        int[] connections = currentLocation.getLocations();
        for (int i = 0; i < connections.length; i++) {
            if (connections[i] == destinationId) {
                return true;
            }
        }
        return false;
    }

    /**
     * Ziska smer (index) pro presun do zadane mistnosti.
     *
     * @param destination cilova mistnost
     * @return index smeru (0 = Sever, 1 = Vychod, 2 = Jih, 3 = Zapad), nebo -1, pokud nelze
     */
    public int getDirectionToLocation(Location destination) {
        int[] connections = currentLocation.getLocations();
        for (int i = 0; i < connections.length; i++) {
            if (connections[i] == destination.getId()) {
                return i; // 0 = Sever, 1 = Vychod, 2 = Jih, 3 = Zapad
            }
        }
        return -1;
    }

    /**
     * Vraci mapu vsech lokaci v hre.
     *
     * @return mapa ID -> Location
     */
    public Map<Integer, Location> getLocations() {
        return locations;
    }

    /**
     * Vraci aktualni mistnost.
     *
     * @return aktualni mistnost
     */
    public Location getCurrentLocation() {
        return currentLocation;
    }

    /**
     * Vraci ID aktualni mistnosti.
     *
     * @return ID aktualni mistnosti
     */
    public int getCurrentRoomId() {
        return currentLocation.getId();
    }
}
