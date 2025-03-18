package command;

import World.WorldMap;

public class Interact {
    private final WorldMap worldMap;
    private final Bag bag;

    public Interact(WorldMap worldMap, Bag bag) {
        this.worldMap = worldMap;
        this.bag = bag;
    }

    public void InteractWithBoat(Bag bag) {
        int plankCount = bag.getItemCount("prkno");

        if (plankCount >= 5) {
            if (bag.containsItem("opravena svetlice")) {
                for (int i = 0; i < 5; i++) {
                    bag.removeItem("prkno");
                }
                System.out.println("Opravil jsi clun a mas opravenou svetlici!");
                System.out.println("Uspesne jsi uprchl z lodi pomocí clunu a privolal pomoc svetlici!");
                System.exit(0);
            } else {
                System.out.println("Nemuzes uprchnout bez opravene svetlice! Oprav ji nejdrive.");
            }
        } else {
            System.out.println("Nemáš dostatek prken (5) pro opravu clunu.");
        }
    }

    public void InteractWithItem(String itemName) {
        System.out.println("Zatim nelze interagovat s: " + itemName);
    }
}
