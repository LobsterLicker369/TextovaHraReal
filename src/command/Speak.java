package command;

import java.io.*;
import java.util.*;

public class Speak {
    private Map<String, List<String>> dialogues;
    private Map<String, Integer> dialogueIndex;

    public Speak() {
        dialogues = new HashMap<>();
        dialogueIndex = new HashMap<>();
        loadDialogue("narrator", "narratorspeak.txt");
        loadDialogue("radio", "radiospeak.txt");
    }

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
