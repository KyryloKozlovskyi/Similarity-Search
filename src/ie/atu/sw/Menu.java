package ie.atu.sw;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu {
	private Scanner input = new Scanner(System.in); // Instance of Scanner
	private String userInput; // Stores user input
	private String embeddingsFilePath = "./word-embeddings.txt"; // Stores file path
	private String outputFilePath = "./out.txt"; // Stores output file path
	private int topN = 3; // Default 3
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
			System.out.println("(2) Specify an Output File (Default: ./out.txt)");
			System.out.println("(3) Specify Top N - Number of Top Matches (Default: 3)");
			System.out.println("(4) Specify Similarity Comparison Algorithm (Default: 1 - Euclidean Distance)");
			System.out.println("(5) Print Current Settings");
			System.out.println("(6) Enter a Word (or Text) to Process");
			System.out.println("(-1) Quit");
			//System.out.print(ConsoleColour.BLACK_BOLD_BRIGHT);
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
				// Top N
				break;
			case "4":
				// Algorithm
				break;
			case "5":
				printCurrentSettings();
				break;
			case "6":
				dict.processWords(embeddings, topN);
				break;
			case "-1":
				System.out.println("Shutting down....");
				System.exit(0);
				break;
			}
		} while (true);
	}
	
	public void printCurrentSettings()
	{
		System.out.println("Your Current Settings");
		System.out.println("Embeddings File: " + getEmbeddingFilePath() );
		System.out.println("Output File: " + getOutputFilePath());
		System.out.println("Top N: " + getTopN());
	}
	// EMB FILE
	public String getEmbeddingFilePath() {
		return embeddingsFilePath;
	}

	public void setEmbeddingFilePath() {
		System.out.print("Specify the file path (or press Enter for Default: ./word-embeddings.txt)>");
		userInput = input.nextLine();
		if(!userInput.isEmpty())
		{
			this.embeddingsFilePath = userInput;
		}
	}

	// OUTPUT FILE
	public String getOutputFilePath() {
		return outputFilePath;
	}

	public void setOutputFilePath() {
		System.out.print("Specify an output file path and name (press Enter for Default)>");
		userInput = input.nextLine();
		if(!userInput.isEmpty())
		{
			this.outputFilePath = userInput;
		}
	}

	// TOP N
	public int getTopN() {
		return topN;
	}

	public void setTopN(int topN) {
		this.topN = topN;
	}
}
