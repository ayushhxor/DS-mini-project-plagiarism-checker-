package comparison;

import datastructures.MyHashSet;

public class SimilarityCalculator {

    public static double calculateSimilarity(String text1, String text2) {

        MyHashSet set1 = new MyHashSet();
        MyHashSet set2 = new MyHashSet();

        String[] words1 = text1.toLowerCase().split("\\W+");
        String[] words2 = text2.toLowerCase().split("\\W+");

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