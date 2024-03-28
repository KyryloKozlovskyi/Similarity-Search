package ie.atu.sw;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Dictionary {
	private Scanner input = new Scanner(System.in); // Instance of Scanner
	private final int NUMBER_OF_FEATURES = 50; //

	// Parses the file and builds a new map
	public Map<String, double[]> parse(String path) throws Exception {
		String key; // Stores word as a key for a map
		Map<String, double[]> embeddings = new HashMap<>(); // Map Word/Embedding
		String tmp[]; // Temporary stores an array of strings
		try (var br = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
			String next;
			System.out.print(ConsoleColour.YELLOW);
			System.out.println("An embeddings file at " + path + " is being used");
			System.out.println("Building a map...");
			while ((next = br.readLine()) != null) { // Loop through each line in the embedding file

				double[] value = new double[NUMBER_OF_FEATURES]; // Stores vector(array of doubles) as a value for a map
				tmp = next.split(",");
				key = tmp[0];
				// Loop through the array starting at index 1 to avoid the "word" and populate
				// the
				// values array
				for (int i = 1; i < tmp.length; i++) {
					value[i - 1] = Double.parseDouble(tmp[i]);
				}

				embeddings.put(key, value);
			}
		} catch (Exception e) {
			System.out.print(ConsoleColour.RED);
			throw new Exception("[ERROR] Encountered a problem parsing the file: " + e.getMessage());
		}
		System.out.print(ConsoleColour.GREEN);
		System.out.println("A map the size of " + embeddings.keySet().size() + " embeddings created");

		return embeddings;
	}

	// Returns a validated array of user words
	public String[] getUserInput(Map<String, double[]> embeddings) {
		String[] userWords;
		String userInput;
		boolean mapContainsFlag;

		do {
			mapContainsFlag = true;
			System.out.print("Enter a Word or Text>");
			userInput = input.nextLine();
			userWords = userInput.toLowerCase().split(" ");

			// Make sure map contains all user words
			for (int i = 0; i < userWords.length; i++) {
				if (!embeddings.containsKey(userWords[i])) {
					mapContainsFlag = false;
					System.out.println("There is no \"" + userWords[i] + "\" in the map. Try a different word");
				}
			}
		} while (!mapContainsFlag);

		return userWords;

	}

	// Calculates average in case multiple words entered
	public double[] getAvgUserEmbeddings(Map<String, double[]> embeddings, String[] userWords) {
		double[] userWordEmbeddings = new double[NUMBER_OF_FEATURES];
		double[] tmp;

		for (String word : userWords) {
			tmp = embeddings.get(word);
			for (int i = 0; i < userWordEmbeddings.length; i++) {
				userWordEmbeddings[i] = userWordEmbeddings[i] + tmp[i];
			}
		}
		for (int i = 0; i < userWordEmbeddings.length; i++) {
			userWordEmbeddings[i] = userWordEmbeddings[i] / userWords.length;
		}
		return userWordEmbeddings;
	}

	// ED
	public double calculateEuclideanDistance(double[] point1, double[] point2) {
		double sumOfSquaredDiffs = 0.0;
		for (int i = 0; i < point1.length; i++) {
			double diff = point2[i] - point1[i];
			sumOfSquaredDiffs += diff * diff;
		}

		double distance = Math.sqrt(sumOfSquaredDiffs);
		return distance;
	}

	// Cosine Distance
	public double calculateCosineDistance(double[] point1, double[] point2) {
		double dotProduct = dotProduct(point1, point2);
		double magnitude1 = vectorNorm(point1);
		double magnitude2 = vectorNorm(point2);

		double cosineSimilarity = dotProduct / (magnitude1 * magnitude2);

		// Cosine distance is 1 - cosine similarity
		double cosineDistance = 1 - cosineSimilarity;
		return cosineDistance;
	}

	// Dot Product
	public double dotProduct(double[] point1, double[] point2) {

		double result = 0;
		for (int i = 0; i < point1.length; i++) {
			result += point1[i] * point2[i];
		}
		return result;
	}

	// Cosine
	public double vectorNorm(double[] point) {
		double result = 0;
		for (double aV : point) {
			result += aV * aV;
		}
		result = (double) Math.sqrt(result);
		return result;
	}

	// Takes user input return. ScoresDataType list
	public List<Scores> getWordScores(String[] userWords, Map<String, double[]> embeddings, int comparisonSelection) {
		List<Scores> userScores = new ArrayList<>(); // Stores user scores
		double[] userEmbeddingsAvg = getAvgUserEmbeddings(embeddings, userWords); // avg vectors
		// calculateEuclideanDistance
		if (comparisonSelection == 1) {
			for (String word : embeddings.keySet()) {
				double currentWord[] = embeddings.get(word);
				userScores.add(new Scores(word, calculateEuclideanDistance(currentWord, userEmbeddingsAvg)));
			}
			// calculateCosineDistance
		} else if (comparisonSelection == 2) {
			for (String word : embeddings.keySet()) {
				double currentWord[] = embeddings.get(word);
				userScores.add(new Scores(word, calculateCosineDistance(currentWord, userEmbeddingsAvg)));
			}
		}
		return userScores;

	}

	// Sort list of Scores (words - scores)
	public List<Scores> sortWordScores(List<Scores> userScores) {
		userScores.sort((o1, o2) -> o1.compareTo(o2));
		return userScores;
	}

	// Must take a sorted list
	public void printProcessedWords(List<Scores> userScores, int n) {
		System.out.print(ConsoleColour.WHITE_BOLD_BRIGHT);
		for (int i = 1; i < n + 1; i++) {
			var tmp = userScores.get(i);
			System.out.println("Word: " + tmp.getWord() + " - Score: " + tmp.getScore());
		}
	}

	// Actual runner MENU OPTION 3
	public void processWords(Map<String, double[]> embeddings, int n, String path, int comparisonSelection) throws IOException {
		String[] userInput = getUserInput(embeddings);
		List<Scores> userScores = new ArrayList<>();
		userScores = getWordScores(userInput, embeddings, comparisonSelection);
		userScores = sortWordScores(userScores);
		printProcessedWords(userScores, n);

		writeToFile(path, buildString(userScores, userInput, n));

	}

	public static String buildString(List<Scores> scoresList, String[] userInput, int n) {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("Input: \n");

		for (String word : userInput) {
			stringBuilder.append(word).append(" "); // Add a space between words
		}

		stringBuilder.append("\nOutput: \n");
		// Append each word to the StringBuilder

		for (int i = 1; i < n + 1; i++) {
			stringBuilder.append(scoresList.get(i).getWord()).append(" : ").append(scoresList.get(i).getScore())
					.append("\n"); // Add
			// a
			// space
			// between
			// words

		}

		return stringBuilder.toString();
	}

	public void writeToFile(String fileName, String words) throws IOException{
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			// Write data to the file
			writer.write(words);
			System.out.print(ConsoleColour.GREEN);
			System.out.println("Data has been written to the file successfully");
			System.out.print(ConsoleColour.BLACK_BOLD_BRIGHT);
			System.out.print("\nPress Enter to continue>");
			input.nextLine();
		} catch (IOException e) {
			System.out.print(ConsoleColour.RED);
			System.err.println("[ERROR] writing to the file: " + e.getMessage());
		}
	}

}