import utils.FileHandler;
import comparison.SimilarityCalculator;

public class Main {

    public static void main(String[] args) {

        System.out.println("Plagiarism Detector Started");

        FileHandler fileHandler = new FileHandler();

        // Read from files
        String text1 = fileHandler.readFile("test/doc1.txt");
        String text2 = fileHandler.readFile("test/doc2.txt");

        // Calculate similarity
        double result = SimilarityCalculator.calculateSimilarity(text1, text2);

        System.out.println("Similarity: " + result + "%");
    }
}

/*import comparison.SimilarityCalculator;
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
}*/