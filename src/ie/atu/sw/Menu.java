package ie.atu.sw;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu {
	private Scanner input = new Scanner(System.in); // Instance of Scanner
	private String userInput; // Stores user input
	private String embeddingsFilePath; // Stores file path
	private Dictionary dict = new Dictionary(); // Instance of Dictionary to access the parse() method
	private Map<String, Double[]> embeddings = new HashMap<>(); // A map to store parse() return
	
	// runMenu() 
	public void runMenu() throws Exception {
		System.out.println(ConsoleColour.WHITE);
		System.out.println("************************************************************");
		System.out.println("*     ATU - Dept. of Computer Science & Applied Physics    *");
		System.out.println("*                                                          *");
		System.out.println("*          Similarity Search with Word Embeddings          *");
		System.out.println("*                                                          *");
		System.out.println("************************************************************");
		do {

			System.out.println("(1) Specify Embeddings File");
			System.out.println("(2) Specify an Output File (default: ./out.txt)");
			System.out.println("(3) Enter a Word or Text");
			System.out.println("(4) Configure Options");
			System.out.println("(-1) Quit");
			System.out.print(ConsoleColour.BLACK_BOLD_BRIGHT);
			System.out.print("Select Option [1-?]>");
			userInput = input.next();
			
			// Option selection
			switch (userInput) {
			case "1":
				System.out.print("Specify a path and name for the Word Embeddings file>");
				userInput = input.next();
				setEmbeddingFilePath(userInput);
				System.out.println("A new Embeddings file at " + userInput + " is being used");
				System.out.println("Building a map...");
				embeddings = dict.parse(getEmbeddingFilePath());
				System.out.println("A Map the size of " + embeddings.keySet().size() + " embeddings created");
				break;
			}
		} while (true);
	}

	public String getEmbeddingFilePath() {
		return embeddingsFilePath;
	}

	public void setEmbeddingFilePath(String embeddingsFilePath) {
		this.embeddingsFilePath = embeddingsFilePath;
	}
}
