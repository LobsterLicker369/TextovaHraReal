package command;

/**
 * Trida pro spravu casovace zaplavy.
 * Udrzuje casovac zaplavy a spousti udalosti, kdyz casovac dosahne urcite hodnoty.
 */
public class Timer {
    private int floodTimer = 0;
    private final Death death;

    /**
     * Konstruktor třídy Timer.
     * Inicializuje casovac zaplavy a vytvari objekt Death pro vypis smrti hrace.
     */
    public Timer() {
        death = new Death();
    }

    /**
     * Zvysi casovac zaplavy o 1.
     */
    public void increaseFlood() {
        floodTimer++;
    }

    /**
     * Vytiskne aktualni hodnotu casovace zaplavy.
     */
    public void printFloodStatus() {
        System.out.println("Flood timer: " + floodTimer + " / 100");
    }

    /**
     * Vraci aktualni hodnotu casovace zaplavy.
     *
     * @return aktualni hodnota casovace zaplavy
     */
    public int getFloodTimer() {
        return floodTimer;
    }

    /**
     * Vypise zpravu o smrti hrace a ukonci program.
     */
    public void playerdies() {
        System.out.println("Voda te zaplavila. Zemrel jsi.");
        System.exit(0);
    }

    /**
     * Zvysi casovac zaplavy o 1 a kontroluje, zda casovac nedosahl hodnoty 100.
     * Pokud ano, spustí udalost smrti hrace.
     */
    public void tick() {
        floodTimer++;
        System.out.println("Flood Timer: " + floodTimer);
        if (floodTimer >= 100) {
            death.PrintPlayerDeath();
        }
    }
}
