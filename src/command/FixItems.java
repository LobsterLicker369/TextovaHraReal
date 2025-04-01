package command;

import java.util.List;
import java.util.Collections;
import World.WorldMap;

/**
 * Trida pro opravu predmetu, jako je clun, svetlice a klice, ve hre.
 */
public class FixItems {

    private final WorldMap worldMap;

    /**
     * Konstruktor pro vytvoreni tridy FixItems.
     *
     * @param worldMap mapa sveta
     */
    public FixItems(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    /**
     * Opravi clun, pokud uzivatel ma dostatek prken.
     *
     * @param bag seznam predmetu v batohu
     */
    public void FixBoat(List<String> bag) {
        int count = Collections.frequency(bag, "prkno");
        if (count >= 5) {
            for (int i = 0; i < 5; i++) {
                bag.remove("prkno");
            }
            System.out.println("Opravil jsi clun! Muzeš nyni uprchnout z lodi.");
            // tady muzes treba nastavit flag nebo zavolat escape metodu
        } else {
            System.out.println("Nemas dostatek prken (5) pro opravu clunu.");
        }
    }

    /**
     * Opravi svetlici, pokud uzivatel ma soucastky a klic.
     *
     * @param bag seznam predmetu v batohu
     */
    public void FixFlare(List<String> bag) {
        if (bag.contains("soucastky svetlice") && bag.contains("klic")) {
            bag.remove("soucastky svetlice");
            bag.remove("klic");
            System.out.println("Opravil jsi svetlici! Muzeš ji nyni odpalit.");
            // Tady by slo pridat opravenou svetlici do inventare
        } else {
            System.out.println("Chybi ti soucastky svetlice a/nebo klic pro opravu.");
        }
    }

    /**
     * Opravi klice, pokud uzivatel ma dostatek soucastek klice.
     *
     * @param bag seznam predmetu v batohu
     */
    public void FixKeys(List<String> bag) {
        int count = Collections.frequency(bag, "soucastky klice");
        if (count >= 2) {
            bag.remove("soucastky klice");
            bag.remove("soucastky klice");
            System.out.println("Opravil jsi klice! Muzeš je nyni pouzit.");
        } else {
            System.out.println("Nemas dostatek soucastek klice (2) pro opravu.");
        }
    }
}
