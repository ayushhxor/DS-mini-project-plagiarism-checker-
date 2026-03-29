import comparison.SimilarityCalculator;
import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Plagiarism Detector Started");

        System.out.println("Enter the database text: ");
        String databaseText = sc.nextLine();

        System.out.println("Enter the input text: ");
        String inputText = sc.nextLine();

        double result = SimilarityCalculator.calculateSimilarity(databaseText, inputText);

        System.out.println("Similarity: " + result + "%");
    }
}