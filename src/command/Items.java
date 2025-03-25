package command;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Items {
    private static final String ITEMS_FILE = "items.txt";
    private static final String BACKUP_FILE = "items_backup.txt";
    private Map<String, List<Integer>> items;

    public Items() {
        items = new HashMap<>();
        backupItems(); // Zaloha puvodniho items.txt pri spusteni hry
        loadItems();
    }

    private void loadItems() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ITEMS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s*,\\s*");
                if (parts.length < 2) continue;

                String itemName = parts[0].trim();
                List<Integer> locations = new ArrayList<>();

                for (int i = 1; i < parts.length; i++) {
                    try {
                        int roomId = Integer.parseInt(parts[i]);
                        locations.add(roomId);
                    } catch (NumberFormatException e) {
                        System.err.println("Chyba pri parovani mistnosti pro " + itemName + ": " + parts[i]);
                    }
                }
                items.put(itemName, locations);
            }
        } catch (IOException e) {
            System.err.println("Chyba pri nacitani predmetu: " + e.getMessage());
        }
    }

    private void saveItems() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ITEMS_FILE))) {
            for (Map.Entry<String, List<Integer>> entry : items.entrySet()) {
                String itemName = entry.getKey();
                List<Integer> locations = entry.getValue();
                String line = itemName + "," + locations.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(","));
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Chyba pri ukladani predmetu: " + e.getMessage());
        }
    }

    public void resetItems() {
        try (BufferedReader reader = new BufferedReader(new FileReader(BACKUP_FILE));
             BufferedWriter writer = new BufferedWriter(new FileWriter(ITEMS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Chyba pri resetovani predmetu: " + e.getMessage());
        }

        loadItems();
    }

    public boolean pickUpItem(String itemName, int currentRoomId, Bag bag) {
        if (items.containsKey(itemName) && items.get(itemName).contains(currentRoomId)) {
            bag.addItem(itemName);
            items.get(itemName).remove((Integer) currentRoomId); // Odstranime lokaci z listu

            // Pokud uz item nema zadne lokace, uplne ho smazeme
            if (items.get(itemName).isEmpty()) {
                items.remove(itemName);
            }

            saveItems(); // Ulozime zmeny
            return true;
        }
        return false;
    }

    public void putItemInRoom(String itemName, int roomId) {
        items.putIfAbsent(itemName, new ArrayList<>()); // Pokud item neexistuje, vytvorime ho
        items.get(itemName).add(roomId); // Pridame lokaci
        saveItems();
    }

    public List<String> getItemsInRoom(int roomId) {
        List<String> roomItems = new ArrayList<>();
        for (Map.Entry<String, List<Integer>> entry : items.entrySet()) {
            if (entry.getValue().contains(roomId)) {
                roomItems.add(entry.getKey());
            }
        }
        return roomItems;
    }

    private void backupItems() {
        File file = new File(BACKUP_FILE);
        if (file.exists()) return; // Pokud uz zaloha existuje, neprepisujeme ji

        try (BufferedReader reader = new BufferedReader(new FileReader(ITEMS_FILE));
             BufferedWriter writer = new BufferedWriter(new FileWriter(BACKUP_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Chyba pri zalohovani predmetu: " + e.getMessage());
        }
    }
}
