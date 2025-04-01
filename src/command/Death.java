package command;

/**
 * Trida pro zpracovani smrti hrace ve hre.
 */
public class Death {

    /**
     * Vytiskne zpravu o smrti hrace a ukonci program.
     */
    public void PrintPlayerDeath() {
        System.out.println("Voda te zaplavila. Zemrel jsi.");
        System.exit(0);
    }
}
