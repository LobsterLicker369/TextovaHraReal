package command;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Loader {

    private final HashMap<String, HashMap<Integer, String>> characterSpeeches = new HashMap<>();

    public void LoadCharacters() {
        try (BufferedReader reader = new BufferedReader(new FileReader("characters.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("#")) continue; // ignoruj komentare a prazdne radky
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String character = parts[0].trim();
                    String file = parts[1].trim();
                    loadSpeechesForCharacter(character, file);
                }
            }
            System.out.println("Postavy uspesne nacteny.");
        } catch (IOException e) {
            System.out.println("Chyba pri nacitani characters.txt: " + e.getMessage());
        }
    }

    private void loadSpeechesForCharacter(String character, String file) {
        HashMap<Integer, String> speeches = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("#")) continue;
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    int id = Integer.parseInt(parts[0].trim());
                    String text = parts[1].trim();
                    speeches.put(id, text);
                }
            }
            characterSpeeches.put(character, speeches);
            System.out.println("Repliky pro " + character + " nacteny ze souboru: " + file);
        } catch (IOException e) {
            System.out.println("Chyba pri nacitani " + file + ": " + e.getMessage());
        }
    }


    public String getSpeech(String character, int id) {
        if (characterSpeeches.containsKey(character)) {
            HashMap<Integer, String> speeches = characterSpeeches.get(character);
            return speeches.getOrDefault(id, "Replika nenalezena.");
        } else {
            return "Postava nenalezena.";
        }
    }
}
