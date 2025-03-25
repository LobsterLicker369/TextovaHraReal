package command;

import World.WorldMap;

import java.util.List;

public class Investigate{
    private final WorldMap worldMap;
    private final Items items;

    public Investigate(WorldMap worldMap, Items items) {
        this.worldMap = worldMap;
        this.items = items;
    }

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
