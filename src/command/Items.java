package command;

import java.io.*;
import java.util.*;

public class Items {
    private static final String ITEMS_FILE = "items.txt";
    private Map<String, Integer> items; // Název předmětu -> ID místnosti

    public Items() {
        items = new HashMap<>();
        loadItems();
    }

    private void loadItems() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ITEMS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String itemName = parts[0].trim();
                    int roomId = Integer.parseInt(parts[1].trim());
                    items.put(itemName, roomId);
                }
            }
        } catch (IOException e) {
            System.err.println("Chyba při načítání předmětů: " + e.getMessage());
        }
    }

    public List<String> getItemsInRoom(int roomId) {
        List<String> roomItems = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            if (entry.getValue() == roomId) {
                roomItems.add(entry.getKey());
            }
        }
        return roomItems;
    }

    public boolean pickUpItem(String itemName, int currentRoomId, Bag bag) {
        if (items.containsKey(itemName) && items.get(itemName) == currentRoomId) {
            bag.addItem(itemName);
            items.remove(itemName);
            saveItems();
            return true;
        }
        return false;
    }

    public void putItemInRoom(String itemName, int roomId) {
        items.put(itemName, roomId);
        saveItems();
    }

    private void saveItems() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ITEMS_FILE))) {
            for (Map.Entry<String, Integer> entry : items.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Chyba při ukládání předmětů: " + e.getMessage());
        }
    }
}
