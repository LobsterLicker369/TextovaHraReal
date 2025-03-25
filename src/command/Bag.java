package command;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Bag {
    private static final String FILE_NAME = "inventory.txt";
    public boolean containsItem(String item) {
        List<String> items = loadItems();
        return items.contains(item);
    }

    public void openBag() {
        List<String> items = loadItems();
        if (items.isEmpty()) {
            System.out.println("Inventar je prazdny.");
        } else {
            System.out.println("Obsah inventare:");
            for (String item : items) {
                System.out.println("- " + item);
            }
        }
    }

    public void addItem(String item) {
        List<String> items = loadItems();
        items.add(item);
        saveItems(items);
    }

    public int getItemCount(String itemName) {
        List<String> items = loadItems();
        int count = 0;
        for (String item : items) {
            if (item.equalsIgnoreCase(itemName)) {
                count++;
            }
        }
        return count;
    }
    public boolean removeItem(String item) {
        List<String> items = loadItems();
        if (items.remove(item)) {
            saveItems(items);
            return true;
        } else {
            System.out.println("Polozka nebyla nalezena v inventari.");
            return false;
        }
    }

    private List<String> loadItems() {
        List<String> items = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                items.add(line);
            }
        } catch (IOException e) {
            System.out.println("Inventar zatim neexistuje nebo je prazdny.");
        }
        return items;
    }
    public void resetInventory() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write(""); // Vymaze obsah inventare
        } catch (IOException e) {
            System.out.println("Chyba pri resetovani inventare: " + e.getMessage());
        }
    }

    private void saveItems(List<String> items) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String item : items) {
                writer.write(item);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Chyba pri ukladani inventare.");
        }
    }

    public boolean canAssembleFlare() {
        int count = getItemCount("soucastky svetlice");
        return count >= 2; // Pokud ma hrac 2 soucastky, muze svetlici sestavit
    }

}
