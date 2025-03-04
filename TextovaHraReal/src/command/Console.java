package command;

import World.WorldMap;
import java.util.Scanner;

public class Console {
    private WorldMap worldMap;

    public Console() {
        this.worldMap = new WorldMap(); // Inicializace mapy sveta
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        String command;

        // Na zacatku hry vypiseme seznam mistnosti a aktualni mistnost
        worldMap.printLocations(); // Vytiskne seznam vsech mistnosti
        worldMap.printCurrentLocation(); // Vytiskne aktualni mistnost

        System.out.println("Dostupne prikazy:");
        System.out.println("1. jdi na [mistnost] - Presune te do jine mistnosti.");
        System.out.println("2. seznam - Zobrazi seznam vsech mistnosti.");
        System.out.println("3. stop - Konec hry.");

        while (true) {
            System.out.print(">> ");
            command = scanner.nextLine();

            if (command.startsWith("jdi na")) {
                String direction = command.substring(7).trim();
                Command gotoCommand = new GoToCommand(worldMap, direction);
                System.out.println(gotoCommand.execute()); // Vykona prikaz a vypise vysledek
            } else if (command.equals("seznam")) {
                worldMap.printLocations(); // Zobrazi vsechny mistnosti
            } else if (command.equals("stop")) {
                System.out.println("Konec hry.");
                break; // Ukonci hru
            } else {
                System.out.println("Neznamy prikaz.");
            }
        }

        scanner.close();
    }
}
