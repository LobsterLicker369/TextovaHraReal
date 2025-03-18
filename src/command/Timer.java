package command;

public class Timer {
    private int floodTimer = 0;
    private final Death death;

    public Timer() {
        death = new Death();
    }

    public void increaseFlood() {
        floodTimer++;
    }


    public void printFloodStatus() {
        System.out.println("Flood timer: " + floodTimer + " / 100");
    }

    public int getFloodTimer() {
        return floodTimer;
    }

    public void playerdies() {
        System.out.println("Voda te zaplavila. Zemrel jsi.");
        System.exit(0);
    }

    public void tick() {
        floodTimer++;
        System.out.println("Flood Timer: " + floodTimer);
        if (floodTimer >= 100) {
            death.PrintPlayerDeath();
        }
    }
}
