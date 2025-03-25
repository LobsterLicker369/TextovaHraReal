package command;

import World.WorldMap;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UnlockRoom {

    private final WorldMap worldMap;
    private boolean doorUnlocked = false;
    private boolean radioUnlocked = false; // Added: variable for unlocking the radio

    public UnlockRoom(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    public void AddRoomToTxt() {
        try {
            List<String> lines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader("locations.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("paluba")) {
                        String[] parts = line.split(",");
                        parts[5] = "6"; // Setting connection to the radio room (id 6)
                        line = String.join(",", parts);
                    }
                    lines.add(line);
                }
            }

            lines.add("radiovamistnost,6,1,0,0,0");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("locations.txt"))) {
                for (String l : lines) {
                    writer.write(l);
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            System.out.println("Chyba pri uprave souboru: " + e.getMessage());
        }
    }

    public void InteractWithDoor(Bag bag) {
        if (!doorUnlocked) {
            if (bag.containsItem("klic")) {
                System.out.println("Pouzil jsi klic a odemkl jsi radiovou mistnost.");
                doorUnlocked = true;
                radioUnlocked = true; // Now correctly set

                bag.removeItem("klic");
                AddRoomToTxt();
                worldMap.reloadLocations();
            } else {
                System.out.println("Dvere jsou zamcene. Potrebujes klic!");
            }
        } else {
            System.out.println("Radiova mistnost je uz odemcena.");
        }
    }

    public boolean isRadioUnlocked() {
        return radioUnlocked;
    }
}
