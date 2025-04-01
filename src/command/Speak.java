package command;

import java.io.*;
import java.util.*;

/**
 * Trida pro nacitani a spravu dialogu postav.
 * Umoznuje nacist dialogy z externich souboru a pristupovat k nim podle postavy.
 */
public class Speak {
    private Map<String, List<String>> dialogues;
    private Map<String, Integer> dialogueIndex;

    /**
     * Konstruktor tridy Speak.
     * Inicializuje mapy pro uchovavani dialogu a jejich indexu.
     * Nacist dialogy pro postavy 'narrator' a 'radio'.
     */
    public Speak() {
        dialogues = new HashMap<>();
        dialogueIndex = new HashMap<>();
        loadDialogue("narrator", "narratorspeak.txt");
        loadDialogue("radio", "radiospeak.txt");
    }

    /**
     * Nacte dialogy pro danou postavu z uvedeneho souboru.
     *
     * @param character nazev postavy, pro kterou se nacitaji dialogy
     * @param filename nazev souboru, ze ktereho se nacitaji dialogy
     */
    private void loadDialogue(String character, String filename) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
            dialogues.put(character, lines);
            dialogueIndex.put(character, 0);
        } catch (IOException e) {
            System.out.println("Chyba pri nacitani souboru " + filename + ": " + e.getMessage());
        }
    }
}
