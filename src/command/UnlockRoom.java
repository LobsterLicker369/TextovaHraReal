package command;

import World.WorldMap;


import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class UnlockRoom {

    private final WorldMap worldMap;
    private boolean doorUnlocked = false;

    public UnlockRoom(WorldMap worldMap) {
        this.worldMap = worldMap;
    }


    public void AddRoomToTxt() {
        try {
            // Nejprve nacist vsechny radky ze souboru
            List<String> lines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader("locations.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Pokud je to paluba, uprav propojeni
                    if (line.startsWith("paluba")) {
                        String[] parts = line.split(",");
                        parts[5] = "6"; // nastavit zapadni vystup z paluby na radiovamistnost (id 6)
                        line = String.join(",", parts);
                    }
                    lines.add(line);
                }
            }

            // Pridat novou mistnost
            lines.add("radiovamistnost,6,1,0,0,0");

            // Prepsat soubor
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

    public void InteractWithDoor() {
        if (!doorUnlocked) {
            System.out.println("Odemkl jsi radiovou mistnost.");
            doorUnlocked = true;

            AddRoomToTxt();
            worldMap.reloadLocations();
        } else {
            System.out.println("Radiova mistnost je uz odemcena.");
        }
    }
}
