package command;

import World.WorldMap;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Trida pro odemknuti mistnosti a interakci s dvermi.
 * Umoznuje odemknuti radiove mistnosti a upravu souboru s lokaci.
 */
public class UnlockRoom {

    private final WorldMap worldMap;
    private boolean doorUnlocked = false;
    private boolean radioUnlocked = false; // Pridano: promenna pro odemknuti radiove mistnosti

    /**
     * Konstruktor tridy UnlockRoom.
     *
     * @param worldMap mapa sveta, ke ktere je pripojena mistnost
     */
    public UnlockRoom(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    /**
     * Prida radiovou mistnost do souboru locations.txt.
     * Upravuje soubor tak, aby byla pridana mistnost s ID 6 (radiovamistnost)
     * a propojeni mezi mistnostmi.
     */
    public void AddRoomToTxt() {
        try {
            List<String> lines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader("locations.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("paluba")) {
                        String[] parts = line.split(",");
                        parts[5] = "6"; // Nastaveni propojeni na radiovou mistnost (id 6)
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

    /**
     * Interakce s dvermi. Pokud je dvere zamcene a uzivatel ma klic,
     * dvere jsou odemknuty a radiova mistnost je zpřístupněna.
     *
     * @param bag batoh, ktery obsahuje predmety, v tomto pripade klic
     */
    public void InteractWithDoor(Bag bag) {
        if (!doorUnlocked) {
            if (bag.containsItem("klic")) {
                System.out.println("Pouzil jsi klic a odemkl jsi radiovou mistnost.");
                doorUnlocked = true;
                radioUnlocked = true;

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

    /**
     * Zkontroluje, zda byla radiova mistnost odemcena.
     *
     * @return true, pokud byla radiova mistnost odemcena, jinak false
     */
    public boolean isRadioUnlocked() {
        return radioUnlocked;
    }
}
