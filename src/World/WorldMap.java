package World;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class WorldMap {
    private Location currentLocation;
    private Map<Integer, Location> locations;


    public WorldMap() {
        resetLocations(); // Reset pred startem hry
        locations = new HashMap<>();
        loadLocations();
        currentLocation = locations.get(1);
    }

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

    public void reloadLocations() {
        locations.clear();
        loadLocations();
    }

    public void printLocations() {
        for (Location location : locations.values()) {
            System.out.println(location);
        }
    }

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

    public void printCurrentLocation() {
        System.out.println("Aktualni mistnost: " + currentLocation.getName());
    }

    // Presun mezi mistnostmi (dle ID)
    public void moveTo(Location newLocation) {
        this.currentLocation = newLocation;
    }

    // Kontrola, zda je propojeni ve smeru na jinou mistnost
    public boolean canMoveTo(int destinationId) {
        int[] connections = currentLocation.getLocations();
        for (int i = 0; i < connections.length; i++) {
            if (connections[i] == destinationId) {
                return true;
            }
        }
        return false;
    }

    // Ziskani cisla smeru pro danou mistnost
    public int getDirectionToLocation(Location destination) {
        int[] connections = currentLocation.getLocations();
        for (int i = 0; i < connections.length; i++) {
            if (connections[i] == destination.getId()) {
                return i; // 0 = Sever, 1 = Vychod, 2 = Jih, 3 = Zapad
            }
        }
        return -1;
    }

    public Map<Integer, Location> getLocations() {
        return locations;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public int getCurrentRoomId() {
        return currentLocation.getId();
    }
}
