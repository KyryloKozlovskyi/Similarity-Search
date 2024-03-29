package ie.atu.sw;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Menu class is used to Display console interface and process user input.
 * Contains methods (setters and getters) that allow users to specify paths and
 * environmental variables.
 */
public class Menu {
	// User input
	private Scanner input = new Scanner(System.in);
	private String userInput;
	// Environmental variables
	private String embeddingsFilePath; // Stores Embeddings file path
	private String outputFilePath; // Stores Output file path
	private int topN; // Stores N value
	private int comparisonSelection; // Stores algorithm option
	// Data processing
	private Dictionary dict = new Dictionary(); // Instance of Dictionary to access the parse() method
	private Map<String, double[]> embeddings = new HashMap<>(); // A map to store parse() return

	/**
	 * Displays console interface. The runMenu() is used to process user input.
	 * 
	 * @throws Exception
	 */
	public void runMenu() throws Exception {
		System.out.println(ConsoleColour.RESET);
		System.out.println(ConsoleColour.WHITE);
		System.out.println("************************************************************");
		System.out.println("*     ATU - Dept. of Computer Science & Applied Physics    *");
		System.out.println("*                                                          *");
		System.out.println("*          Similarity Search with Word Embeddings          *");
		System.out.println("*                                                          *");
		System.out.println("************************************************************");
		do {
			System.out.println(ConsoleColour.WHITE);
			System.out.println("************************************************************");
			System.out.println("* (1) Specify Embeddings File Path and Populate a Map      *");
			System.out.println("* (2) Specify an Output File Path                          *");
			System.out.println("* (3) Specify N - Number of Top Matches                    *");
			System.out.println("* (4) Specify Similarity Comparison Algorithm              *");
			System.out.println("* (5) Print Current Settings                               *");
			System.out.println("* (6) Enter a Word (or Text) to Process                    *");
			System.out.println("* (-1) Quit                                                *");
			System.out.println("************************************************************\n");
			System.out.print(ConsoleColour.BLACK_BOLD_BRIGHT);
			System.out.print("Select Option [1-6]>");
			userInput = input.nextLine();

			// Option selection
			switch (userInput) {
			case "1": // Embeddings File path
				setEmbeddingFilePath();
				embeddings = dict.parse(getEmbeddingFilePath());
				break;
			case "2": // Output File path
				setOutputFilePath();
				break;
			case "3": // N
				setTopN();
				break;
			case "4": // Algorithm
				setComparisonSelection();
				break;
			case "5": // Current Settings
				printCurrentSettings();
				break;
			case "6": // Process user input
				dict.processWords(embeddings, topN, getOutputFilePath(), comparisonSelection);
				System.out.print(ConsoleColour.BLACK_BOLD_BRIGHT);
				System.out.print("\nPress Enter to continue>");
				input.nextLine();
				break;
			case "-1": // Quit
				System.out.print(ConsoleColour.RED);
				System.out.println("Shutting down....");
				System.out.println(ConsoleColour.RESET);
				System.exit(0);
				break;
			}
		} while (true);
	}

	/**
	 * Prints current user settings.
	 */
	public void printCurrentSettings() {
		System.out.println("Current Settings: ");
		System.out.print(ConsoleColour.YELLOW);
		System.out.println("Embeddings File: " + getEmbeddingFilePath());
		System.out.println("Output File: " + getOutputFilePath());
		System.out.println("Top N: " + getTopN());
		System.out.println("Similarity Comparison Algorithm: " + printComparisonSelection(getComparisonSelection()));
		System.out.print(ConsoleColour.BLACK_BOLD_BRIGHT);
		System.out.print("\nPress Enter to continue>");
		input.nextLine();
	}

	// Embeddings File
	/**
	 * embeddingsFilePath getter.
	 * 
	 * @return embeddingsFilePath
	 */
	public String getEmbeddingFilePath() {
		return embeddingsFilePath;
	}

	/**
	 * embeddingsFilePath setter. Allows user to specify Embeddings file path.
	 */
	public void setEmbeddingFilePath() {
		System.out.print("Enter the file path (or press Enter to use the default path: ./word-embeddings.txt)>");
		userInput = input.nextLine();
		if (!userInput.isEmpty()) {
			this.embeddingsFilePath = userInput;
		} else {
			this.embeddingsFilePath = "./word-embeddings.txt";
		}
	}

	// Output file
	/**
	 * outputFilePath getter.
	 * 
	 * @return outputFilePath
	 */
	public String getOutputFilePath() {
		return outputFilePath;
	}

	/**
	 * outputFilePath setter. Allows user to specify Output file path.
	 */
	public void setOutputFilePath() {
		System.out.print("Enter the file path (or press Enter to use the default path: ./out.txt)>");
		userInput = input.nextLine();
		if (!userInput.isEmpty()) {
			this.outputFilePath = userInput;
		} else {
			this.outputFilePath = "./out.txt";
		}
		System.out.print(ConsoleColour.YELLOW);
		System.out.println("An output file at " + getOutputFilePath() + " is being used");
	}

	// Top N
	/**
	 * topN getter.
	 * 
	 * @return topN
	 */
	public int getTopN() {
		return topN;
	}

	/**
	 * topN setter. Allows user to specify a number of top matches (N).
	 */
	public void setTopN() {
		System.out.print("Enter a number of top matches (N) to save (or press Enter to use the default value: 3)>");
		userInput = input.nextLine();
		if (!userInput.isEmpty()) {
			this.topN = Integer.parseInt(userInput);
		} else {
			this.topN = 3;
		}
		System.out.print(ConsoleColour.YELLOW);
		System.out.println("The N value of " + getTopN() + " is being used");
	}

	// Comparison
	/**
	 * comparisonSelection getter.
	 * 
	 * @return comparisonSelection
	 */
	public int getComparisonSelection() {
		return comparisonSelection;
	}

	/**
	 * comparisonSelection setter. Allows user to select a Vector Comparison
	 * Algorithm.
	 */
	public void setComparisonSelection() {
		System.out.print(
				"Enter a number (or press Enter to use the default algortihm: Euclidean Distance)\n1 - Euclidean Distance\n2 - Cosine Distance\nSelect Option [1-2]>");
		userInput = input.nextLine();
		if (!userInput.isEmpty()) {
			this.comparisonSelection = Integer.parseInt(userInput);
		} else {
			this.comparisonSelection = 1;
		}
		System.out.print(ConsoleColour.YELLOW);
		System.out.println(printComparisonSelection(getComparisonSelection()) + " is being used");
	}

	/**
	 * Prints Comparison Algorithm options.
	 */
	public String printComparisonSelection(int n) {
		String returnString = "";
		if (this.comparisonSelection == 1) {
			returnString = "Euclidean Distance";
		} else if (this.comparisonSelection == 2) {
			returnString = "Cosine Distance";
		}
		return returnString;
	}

}