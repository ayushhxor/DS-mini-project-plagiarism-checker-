package preprocessing;

import java.util.*;

public class TextProcessor {

    // Stopwords 
    private static final Set<String> STOPWORDS = new HashSet<>(Arrays.asList(
        "a", "an", "the", "is", "are", "was", "were",
        "in", "on", "at", "of", "to", "and", "or",
        "for", "with", "as", "by", "this", "that"
    ));

    // Normalize text 
    public static String normalize(String text) {
        if (text == null) return "";
        text = text.toLowerCase();
        text = text.replaceAll("[^a-z0-9\\s]", "");
        return text;
    }

    // Tokenize into words 
    public static List<String> tokenize(String text) {
        String normalized = normalize(text);
        String[] words = normalized.split("\\s+");

        List<String> tokens = new ArrayList<>();
        for (String word : words) {
            if (!word.isEmpty()) {
                tokens.add(word);
            }
        }
        return tokens;
    }

    // Remove stopwords
    public static List<String> removeStopwords(List<String> words) {
        List<String> filtered = new ArrayList<>();

        for (String word : words) {
            if (!STOPWORDS.contains(word)) {
                filtered.add(word);
            }
        }
        return filtered;
    }

    // Convert text  
    public static Set<String> getWordSet(String text) {
        List<String> tokens = tokenize(text);
        List<String> filtered = removeStopwords(tokens);
        return new HashSet<>(filtered);
    }

    // Convert text   
    public static Map<String, Integer> getWordFrequency(String text) {
        List<String> tokens = tokenize(text);
        List<String> filtered = removeStopwords(tokens);

        Map<String, Integer> freqMap = new HashMap<>();

        for (String word : filtered) {
            freqMap.put(word, freqMap.getOrDefault(word, 0) + 1);
        }

        return freqMap;
    }

    // Convert words 
    public static List<Integer> getWordHashes(String text) {
        List<String> tokens = tokenize(text);
        List<Integer> hashes = new ArrayList<>();

        for (String word : tokens) {
            hashes.add(word.hashCode());
        }

        return hashes;
    }
}