package command;

import java.util.List;
import java.util.Collections;
import World.WorldMap;

public class FixItems {

    private final WorldMap worldMap;

    public FixItems(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

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
