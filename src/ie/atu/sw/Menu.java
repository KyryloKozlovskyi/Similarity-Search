package ie.atu.sw;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu {
	// User input
	private Scanner input = new Scanner(System.in); // Instance of Scanner
	private String userInput; // Stores user input
	// Env variables (default)
	private String embeddingsFilePath = "./word-embeddings.txt"; // Stores file path
	private String outputFilePath = "./out.txt"; // Stores output file path
	private int topN = 3; // Default 3
	private int comparisonSelection = 1;
	// Data
	private Dictionary dict = new Dictionary(); // Instance of Dictionary to access the parse() method
	private Map<String, double[]> embeddings = new HashMap<>(); // A map to store parse() return
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

			System.out.println("(1) Specify Embeddings File Path and Populate a Map");
			System.out.println("(2) Specify an Output File");
			System.out.println("(3) Specify N - Number of Top Matches");
			System.out.println("(4) Specify Similarity Comparison Algorithm");
			System.out.println("(5) Print Current Settings");
			System.out.println("(6) Enter a Word (or Text) to Process");
			System.out.println("(-1) Quit");
			// System.out.print(ConsoleColour.BLACK_BOLD_BRIGHT);
			System.out.print("Select Option [1-?]>");
			userInput = input.nextLine();

			// Option selection
			switch (userInput) {
			case "1":
				setEmbeddingFilePath();
				embeddings = dict.parse(getEmbeddingFilePath());
				break;
			case "2":
				setOutputFilePath();
				break;
			case "3":
				setTopN();
				break;
			case "4":
				setComparisonSelection();
				break;
			case "5":
				printCurrentSettings();
				break;
			case "6":
				dict.processWords(embeddings, topN, getOutputFilePath(), comparisonSelection);
				break;
			case "-1":
				System.out.println("Shutting down....");
				System.exit(0);
				break;
			}
		} while (true);
	}

	public void printCurrentSettings() {
		System.out.println("Current Settings: ");
		System.out.println("Embeddings File: " + getEmbeddingFilePath());
		System.out.println("Output File: " + getOutputFilePath());
		System.out.println("Top N: " + getTopN());
		System.out.println(printComparisonSelection(getComparisonSelection()));
	}

	// EMB FILE
	public String getEmbeddingFilePath() {
		return embeddingsFilePath;
	}

	public void setEmbeddingFilePath() {
		System.out.print("Enter the file path (or press Enter to use the default path: ./word-embeddings.txt)>");
		userInput = input.nextLine();
		if (!userInput.isEmpty()) {
			this.embeddingsFilePath = userInput;
		}
	}

	// OUTPUT FILE
	public String getOutputFilePath() {
		return outputFilePath;
	}

	public void setOutputFilePath() {
		System.out.print("Enter the file path (or press Enter to use the default path: ./out.txt)>");
		userInput = input.nextLine();
		if (!userInput.isEmpty()) {
			this.outputFilePath = userInput;
		}
		System.out.println("An output file at " + getOutputFilePath() + " is being used");
	}

	// TOP N
	public int getTopN() {
		return topN;
	}

	public void setTopN() {
		System.out.print("Enter a number of top matches (N) to save (or press Enter to use the default value: 3)>");
		userInput = input.nextLine();
		if (!userInput.isEmpty()) {
			this.topN = Integer.parseInt(userInput);
		}
		System.out.println("The N value of " + getTopN() + " is being used");
	}

	// Comp
	public int getComparisonSelection() {
		return comparisonSelection;
	}

	public void setComparisonSelection() {
		System.out.print(
				"Enter a number (or press Enter to use the default algortihm: Euclidean Distance)\n1 - Euclidean Distance\n2 - Cosine Distance\n3 - Dot Product\nYour Choice>");
		userInput = input.nextLine();
		if (!userInput.isEmpty()) {
			this.comparisonSelection = Integer.parseInt(userInput);
		}
		System.out.println(printComparisonSelection(getComparisonSelection()) + " is being used");
	}

	public String printComparisonSelection(int n) {
		String returnString = "";
		if (this.comparisonSelection == 1) {
			returnString = "Euclidean Distance";
		} else if (this.comparisonSelection == 2) {
			returnString = "Cosine Distance";
		} else if (this.comparisonSelection == 3) {
			returnString = "Dot Product";
		}
		return returnString;
	}
}
