package command;

import World.Location;
import World.WorldMap;

public class GoToCommand implements Command {
    private WorldMap worldMap;
    private String destinationName;

    public GoToCommand(WorldMap worldMap, String destinationName) {
        this.worldMap = worldMap;
        this.destinationName = destinationName.toLowerCase(); // Prevedeme na mala pismena
    }

    @Override
    public String execute() {
        // Prohledame mistnosti a najdeme odpovidajici jmeno
        for (Location location : worldMap.getLocations().values()) {
            if (location.getName().toLowerCase().equals(destinationName)) {
                // Zkontrolujeme, zda je propojeno v nekterem smeru
                int direction = worldMap.getDirectionToLocation(location);
                if (direction != -1 && worldMap.canMoveTo(direction)) {
                    worldMap.moveTo(location);
                    return "Presunuto na mistnost: " + location.getName();
                } else {
                    return "Nelze se presunout v tomto smeru.";
                }
            }
        }
        return "Mistnost " + destinationName + " nebyla nalezena.";
    }

    @Override
    public boolean exit() {
        return false;
    }
}
