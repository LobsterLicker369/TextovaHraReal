package World;

import java.util.Arrays;

public class Location {
    private String name;
    private int id;
    private int[] locations;

    public Location(String name, int id, int[] locations) {
        this.name = name;
        this.id = id;
        this.locations = locations;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int[] getLocations() {
        return locations;
    }

    @Override
    public String toString() {
        return "Mistnost: " + name + ", ID: " + id + ", propojeno: " + Arrays.toString(locations);
    }
}
