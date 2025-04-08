package command;

import World.WorldMap;
import java.util.*;

/**
 * Trida pro spravovani hlavni konzole hry.
 * Obsahuje logiku pro zpracovani prikazu od hrace a interakci s hernim svetem.
 */
public class Console {
    private final Scanner scanner;
    private final Map<String, Command> commandMap;
    private final WorldMap worldMap;
    private final Bag bag;
    private final Items items;
    private final Interact interact;
    private final UnlockRoom unlockRoom;
    private final Timer timer;
    private final Investigate investigate;
    private final Loader loader;
    private final Map<String, Integer> speakIndexes;

    /**
     * Konstruktor, ktery inicializuje vsechny potrebne objekty a nastavi prikazy.
     */
    public Console() {
        scanner = new Scanner(System.in);
        worldMap = new WorldMap();
        bag = new Bag();
        items = new Items();

        items.resetItems();
        bag.resetInventory();

        interact = new Interact(worldMap, bag);
        unlockRoom = new UnlockRoom(worldMap);
        investigate = new Investigate(worldMap, items);
        timer = new Timer();
        loader = new Loader();
        speakIndexes = new HashMap<>();
        loader.LoadCharacters();

        commandMap = new HashMap<>();
        initializeCommands();
    }

    /**
     * Inicializuje seznam prikazu pro hru.
     */
    private void initializeCommands() {
        // Staticke prikazy
        commandMap.put("seznam", worldMap::printLocations);
        commandMap.put("otevri inventar", bag::openBag);
        commandMap.put("prohledat", investigate::execute);
        commandMap.put("stop", () -> {
            System.out.println("Konec hry.");
            System.exit(0);
        });

        // Interakce
        commandMap.put("otevri dvere", () -> unlockRoom.InteractWithDoor(bag));
        commandMap.put("pouzij clun", this::useBoat);
        commandMap.put("opravit svetlici", this::repairFlare);
    }

    /**
     * Spusti hru a ceka na vstup od uzivatele pro prikazy.
     */
    public void start() {
        System.out.println(
                "Dostupne prikazy:\n" +
                        "- seznam                -> Zobrazi seznam vsech mistnosti.\n" +
                        "- otevri inventar       -> Zobrazi obsah inventare.\n" +
                        "- prohledat             -> Prohleda mistnost a vypise predmety v ni.\n" +
                        "- seber [predmet]       -> Sebere predmet z mistnosti.\n" +
                        "- poloz [predmet]       -> Polozi predmet z inventare zpet do mistnosti.\n" +
                        "- otevri dvere          -> Pokusi se otevrit dvere do radiove mistnosti.\n" +
                        "- pouzij clun           -> Pokusi se opravit a pouzit zachranny clun (potrebuje 5 prken a opravenou svetlici).\n" +
                        "- jdi na [mistnost]     -> Prezune te do jine mistnosti (napr. jdi na paluba).\n" +
                        "- mluv [postava]        -> Schizofrenie (nebo radio po otevreni dveri do mistnosti) promluvi.\n" +
                        "- opravit svetlici      -> Opravi svetlici (pouze ve strojovne, potrebujes 3 soucastky).\n" +
                        "- stop                  -> Konec hry."
        );

        while (true) {
            System.out.print(">> ");
            String input = scanner.nextLine().trim().toLowerCase();

            // Rozdeleni na prikaz a argumenty
            String[] parts = input.split(" ", 2);
            String commandKey = parts[0];
            String argument = (parts.length > 1) ? parts[1] : null;

            // Nejprve zkontrolujeme presnou shodu
            if (commandMap.containsKey(input)) {
                commandMap.get(input).execute();
            }
            // Dynamicke prikazy s argumenty
            else if (commandKey.equals("jdi") && argument != null && argument.startsWith("na ")) {
                executeGoToCommand(argument.substring(3).trim());
            } else if (commandKey.equals("seber") && argument != null) {
                executePickUpCommand(argument);
            } else if (commandKey.equals("poloz") && argument != null) {
                executeDropCommand(argument);
            } else if (commandKey.equals("mluv") && argument != null) {
                executeSpeakCommand(argument);
            } else if (commandKey.equals("interaguj") && argument != null && argument.startsWith("s ")) {
                executeInteractCommand(argument.substring(2).trim());
            }
            // Neznamy prikaz
            else {
                System.out.println("Neznamy prikaz.");
            }

            timer.increaseFlood();
            System.out.println("Sekundy vyuzity: " + timer.getFloodTimer());

            if (timer.getFloodTimer() >= 100) {
                System.out.println("Byl jsi zaplaven a zemrel jsi! Konec hry.");
                break;
            }
        }
        scanner.close();
    }

    /**
     * Spusti prikaz pro presun na jinou mistnost.
     * @param destination Cielova mistnost.
     */
    private void executeGoToCommand(String destination) {
        System.out.println(new GoToCommand(worldMap, destination).execute());
    }

    /**
     * Spusti prikaz pro komunikaci s postavou.
     * @param character Jmeno postavy.
     */
    private void executeSpeakCommand(String character) {
        if (character.equals("radio") && !unlockRoom.isRadioUnlocked()) {
            System.out.println("Radio nefunguje. Musis nejdriv odemknout radiovou mistnost!");
            return;
        }

        speakIndexes.putIfAbsent(character, 1);
        int currentId = speakIndexes.get(character);
        String line = loader.getSpeech(character, currentId);
        System.out.println(line);
        speakIndexes.put(character, currentId + 1);
    }

    /**
     * Spusti prikaz pro sebrani predmetu.
     * @param item Nazev predmetu.
     */
    private void executePickUpCommand(String item) {
        int roomId = worldMap.getCurrentRoomId();
        List<String> roomItems = items.getItemsInRoom(roomId);

        if (roomItems.contains(item)) {
            if (items.pickUpItem(item, roomId, bag)) {  // Predmet se uspesne sebral
                System.out.println("Sebral jsi: " + item);
            } else {
                System.out.println("Predmet se nepodarilo sebrat.");
            }
        } else {
            System.out.println("Tento predmet tu neni.");
        }
    }

    /**
     * Spusti prikaz pro polozit predmet.
     * @param item Nazev predmetu.
     */
    private void executeDropCommand(String item) {
        if (bag.removeItem(item)) {
            int roomId = worldMap.getCurrentRoomId();
            items.putItemInRoom(item, roomId);
            System.out.println("Polozil jsi: " + item);
        } else {
            System.out.println("Tento predmet nemas v inventari.");
        }
    }

    /**
     * Spusti interakci s predmetem.
     * @param item Nazev predmetu.
     */
    private void executeInteractCommand(String item) {
        interact.InteractWithItem(item);
    }

    /**
     * Spusti opravu clunu.
     */
    private void useBoat() {
        interact.InteractWithBoat(bag);
    }

    /**
     * Spusti opravu svetlice.
     */
    private void repairFlare() {
        int currentRoomId = worldMap.getCurrentRoomId();

        // Oprava svetlice je mozna jen ve strojovne (roomId = 4)
        if (currentRoomId != 4) {
            System.out.println("Svetlici muzes opravit jen ve strojovne!");
            return;
        }

        if (bag.canAssembleFlare()) {
            // Odstranime vsechny soucastky z inventare
            for (int i = 0; i < 2; i++) {
                bag.removeItem("soucastky svetlice");
            }
            // Pridame opravenou svetlici
            bag.addItem("opravena svetlice");

            System.out.println("Sestavil jsi opravenou svetlici! Ted ji muzes pouzit k uniku.");
        } else {
            System.out.println("Nemas vsechny soucastky svetlice.");
        }
    }
}
