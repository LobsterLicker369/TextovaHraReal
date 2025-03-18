package command;

import World.Location;
import World.WorldMap;

public class GoToCommand {
    private final WorldMap worldMap;
    private final String destinationName;

    public GoToCommand(WorldMap worldMap, String destinationName) {
        this.worldMap = worldMap;
        this.destinationName = destinationName;
    }

    public String execute() {
        Location currentLocation = worldMap.getCurrentLocation();
        Location targetLocation = null;

        for (Location location : worldMap.getLocations().values()) {
            if (location.getName().equalsIgnoreCase(destinationName)) {
                targetLocation = location;
                break;
            }
        }

        if (targetLocation == null) {
            return "Tato mistnost neexistuje.";
        }

        if (currentLocation.getId() == targetLocation.getId()) {
            return "Uz se nachazis v teto mistnosti.";
        }

        if (worldMap.canMoveTo(targetLocation.getId())) {
            worldMap.moveTo(targetLocation);
            return "Presunuto na mistnost: " + targetLocation.getName();
        } else {
            return "Tato mistnost neni primo dostupna.";
        }
    }
}
