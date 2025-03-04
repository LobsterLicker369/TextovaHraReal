package World;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WorldMap {
    private Location currentLocation;
    private Map<Integer, Location> locations;

    public WorldMap() {
        locations = new HashMap<>();
        loadLocations(); // Nacitame mistnosti z textoveho souboru
        currentLocation = locations.get(1); // Vychozi mistnost je 'paluba'
    }

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

    public void printLocations() {
        for (Location location : locations.values()) {
            System.out.println(location);
        }
    }


    public void printCurrentLocation() {
        System.out.println("Aktualni mistnost: " + currentLocation.getName());
    }

    // Presun mezi mistnostmi (dle ID)
    public void moveTo(Location newLocation) {
        this.currentLocation = newLocation;
    }

    // Kontrola, zda je propojeni ve smeru na jinou mistnost
    public boolean canMoveTo(int direction) {
        int[] connections = currentLocation.getLocations();
        return connections[direction] != 0; // Pokud je 0, neni propojeni
    }



    // Ziskani cisla smeru pro danou mistnost
    public int getDirectionToLocation(Location destination) {
        int[] connections = currentLocation.getLocations();
        for (int i = 0; i < connections.length; i++) {
            if (connections[i] == destination.getId()) {
                return i; // Vrati index smeru (0 = Sever, 1 = Vychod, 2 = Jih, 3 = Zapad)
            }
        }
        return -1; // Pokud neni propojeno
    }

    // Nova metoda pro ziskani vsech mistnosti
    public Map<Integer, Location> getLocations() {
        return locations;
    }
}
