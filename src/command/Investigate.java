package command;

import World.WorldMap;
import java.util.List;

/**
 * Trida pro zkoumani mistnosti a ziskavani seznamu predmetu v ni.
 */
public class Investigate {
    private final WorldMap worldMap;
    private final Items items;

    /**
     * Konstruktor tridy Investigate.
     *
     * @param worldMap svet, kde se hra odehrava
     * @param items spravce predmetu ve hre
     */
    public Investigate(WorldMap worldMap, Items items) {
        this.worldMap = worldMap;
        this.items = items;
    }

    /**
     * Provede zkoumani aktualni mistnosti a vypise seznam predmetu,
     * ktere se v ni nachazi.
     */
    public void execute() {
        int currentRoomId = worldMap.getCurrentRoomId();
        List<String> roomItems = items.getItemsInRoom(currentRoomId);

        if (roomItems.isEmpty()) {
            System.out.println("V teto mistnosti nejsou zadne predmety.");
        } else {
            System.out.println("V mistnosti vidis nasledujici predmety:");
            for (String item : roomItems) {
                System.out.println("- " + item);
            }
        }
    }
}
