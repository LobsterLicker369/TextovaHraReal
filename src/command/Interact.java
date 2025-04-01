package command;

import World.WorldMap;

/**
 * Trida pro interakci s predmety a objekty ve hre.
 */
public class Interact {
    private final WorldMap worldMap;
    private final Bag bag;

    /**
     * Konstruktor tridy Interact.
     *
     * @param worldMap svet, kde se hra odehrava
     * @param bag taska, ve ktere jsou predmety, se kterymi lze interagovat
     */
    public Interact(WorldMap worldMap, Bag bag) {
        this.worldMap = worldMap;
        this.bag = bag;
    }

    /**
     * Interakce s clunem. Pro opravu clunu jsou potreba 4 prkna
     * a opravena svetlice. Po oprave clunu a pouziti svetlice
     * je hrac presunut na zachrannou cestu a hra konci.
     *
     * @param bag taska hrace obsahujici predmety
     */
    public void InteractWithBoat(Bag bag) {
        int plankCount = bag.getItemCount("prkno");

        if (plankCount >= 4 && bag.containsItem("opravena svetlice")) {
            for (int i = 0; i < 4; i++) {
                bag.removeItem("prkno");
            }
            bag.removeItem("opravena svetlice");

            System.out.println("Opravil jsi clun a mas opravenou svetlici!");
            System.out.println("Uspesne jsi uprchl z lodi pomoci clunu a privolal pomoc svetlici!");
            System.exit(0);
        } else if (plankCount < 4) {
            System.out.println("Nemas dostatek prken (4) pro opravu clunu.");
        } else {
            System.out.println("Nemuzes uprchnout bez opravene svetlice! Oprav ji nejdrive.");
        }
    }

    /**
     * Interakce s obecnym predmetem. Zatim pouze vypise informaci,
     * ze s predmetem nelze interagovat.
     *
     * @param itemName nazev predmetu
     */
    public void InteractWithItem(String itemName) {
        System.out.println("Zatim nelze interagovat s: " + itemName);
    }
}
