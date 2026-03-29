package comparison;

import java.util.List;

import datastructures.MyHashSet;
import preprocessing.TextProcessor;

public class SimilarityCalculator {

    public static double calculateSimilarity(String text1, String text2) {

        MyHashSet set1 = new MyHashSet();
        MyHashSet set2 = new MyHashSet();

    List<String> words1 = TextProcessor.removeStopwords(TextProcessor.tokenize(text1));
    List<String> words2 = TextProcessor.removeStopwords(TextProcessor.tokenize(text2));

        // Fill sets
        for (String word : words1) {
            if (!word.isEmpty())
                set1.add(word);
        }

        for (String word : words2) {
            if (!word.isEmpty())
                set2.add(word);
        }

        int common = 0;
        int total = 0;

        // Count common + total
        for (String word : words1) {
            if (!word.isEmpty()) {
                if (set2.contains(word)) {
                    common++;
                }
                total++;
            }
        }

        for (String word : words2) {
            if (!word.isEmpty() && !set1.contains(word)) {
                total++;
            }
        }

        return (common * 100.0) / total;
    }
}