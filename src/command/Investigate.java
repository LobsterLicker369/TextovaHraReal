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
            System.out.println("V této místnosti nejsou žádné předměty.");
        } else {
            System.out.println("V místnosti vidíš následující předměty:");
            for (String item : roomItems) {
                System.out.println("- " + item);
            }
        }
    }
}
