import comparison.SimilarityCalculator;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String text1, text2;
        System.out.println("Enter the database text: ");
        text1 = sc.nextLine();
        System.out.println("Enter the input text: ");
        text2 = sc.nextLine();

        double result = SimilarityCalculator.calculateSimilarity(text1, text2);

        System.out.println("Similarity: " + result + "%");
        System.out.println("Plagiarism Detector Started");

        // TODO: Read files
        // TODO: Process text
        // TODO: Compare documents
        // TODO: Display result
    }
}