package command;

import World.WorldMap;
import java.util.*;

public class Console {
    private final Scanner scanner;
    private final WorldMap worldMap;
    private final Bag bag;
    private final Items items;
    private final Interact interact;
    private final UnlockRoom unlockRoom;
    private final Timer timer;
    private final Investigate investigate;
    private final Loader loader;
    private final Map<String, Integer> speakIndexes;

    public Console() {
        scanner = new Scanner(System.in);
        worldMap = new WorldMap();
        bag = new Bag();
        items = new Items();
        interact = new Interact(worldMap, bag);
        timer = new Timer();
        unlockRoom = new UnlockRoom(worldMap);
        investigate = new Investigate(worldMap, items);
        loader = new Loader();
        speakIndexes = new HashMap<>();
        loader.LoadCharacters();
    }

    public void start() {
        System.out.println(
                "Dostupne prikazy:\n" +
                        "1. jdi na [mistnost] - Presune te do jine mistnosti (napr. jdi na paluba).\n" +
                        "2. jdi na radiovamistnost - Presune te do radiove mistnosti, pokud je odemcena.\n" +
                        "3. seznam - Zobrazi seznam vsech mistnosti.\n" +
                        "4. otevri inventar - Vypise obsah inventare.\n" +
                        "5. predmety - Vypise predmety v aktualni mistnosti.\n" +
                        "6. prohledat - Prohledas mistnost a vypises predmety v ni.\n" +
                        "7. seber [predmet] - Sebere predmet z mistnosti.\n" +
                        "8. poloz [predmet] - Polozis predmet z inventare zpet do mistnosti.\n" +
                        "9. interaguj s dvermi - Pokusi se otevrit dvere do radiove mistnosti.\n" +
                        "10. interaguj s clunem - Pokusi se opravit a pouzit zachranny clun (potrebujes 5 prken).\n" +
                        "11. interaguj s [predmet] - Provede interakci se specifickym predmetem.\n" +
                        "12. mluv [radio / narrator] - Postava rekne dalsi repliku.\n" +
                        "13. stop - Konec hry."
        );


        while (true) {
            System.out.print(">> ");
            String command = scanner.nextLine().toLowerCase();

            if (command.startsWith("jdi na ")) {
                String destination = command.substring(7).trim();
                GoToCommand goToCommand = new GoToCommand(worldMap, destination);
                System.out.println(goToCommand.execute());
            } else if (command.equals("seznam")) {
                worldMap.printLocations();
            } else if (command.equals("otevri inventar")) {
                bag.openBag();
            } else if (command.equals("predmety")) {
                int roomId = worldMap.getCurrentRoomId();
                List<String> roomItems = items.getItemsInRoom(roomId);
                if (roomItems.isEmpty()) {
                    System.out.println("V mistnosti nejsou zadne predmety.");
                } else {
                    System.out.println("Predmety v mistnosti:");
                    for (String item : roomItems) {
                        System.out.println("- " + item);
                    }
                }
            } else if (command.startsWith("seber")) {
                String item = command.substring(6).trim();
                int roomId = worldMap.getCurrentRoomId();
                List<String> roomItems = items.getItemsInRoom(roomId);
                if (roomItems.contains(item)) {
                    items.pickUpItem(item, roomId, bag);
                    System.out.println("Sebral jsi: " + item);
                } else {
                    System.out.println("Tento predmet tu neni.");
                }
            } else if (command.startsWith("poloz")) {
                String item = command.substring(6).trim();
                if (bag.removeItem(item)) {
                    int roomId = worldMap.getCurrentRoomId();
                    items.putItemInRoom(item, roomId);
                    System.out.println("Polozil jsi: " + item);
                } else {
                    System.out.println("Tento predmet nemas v inventari.");
                }
            } else if (command.equals("interaguj s dvermi")) {
                unlockRoom.InteractWithDoor();
            } else if (command.equals("interaguj s clunem")) {
                interact.InteractWithBoat(bag);
            } else if (command.startsWith("interaguj s ")) {
                String itemName = command.substring(11).trim();
                interact.InteractWithItem(itemName);
            } else if (command.equals("prohledat")) {
                investigate.execute();
            } else if (command.startsWith("mluv ")) {
                String character = command.substring(5).trim();
                speakIndexes.putIfAbsent(character, 1);
                int currentId = speakIndexes.get(character);
                String line = loader.getSpeech(character, currentId);
                System.out.println(line);
                speakIndexes.put(character, currentId + 1);
            } else if (command.equals("stop")) {
                System.out.println("Konec hry.");
                break;
            } else {
                System.out.println("Neznamy prikaz.");
            }

            timer.increaseFlood();
            System.out.println("Floodtimer: " + timer.getFloodTimer());

            if (timer.getFloodTimer() >= 100) {
                System.out.println("Byl jsi zaplaven a zemrel jsi! Konec hry.");
                break;
            }
        }
        scanner.close();
    }
}
