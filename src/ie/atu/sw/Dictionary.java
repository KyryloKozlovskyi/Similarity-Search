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

/**
 * @author Kyrylo Kozlovskyi G00425385 https://github.com/KyryloKozlovskyi/Similarity-Search
 * @version 1.0
 * @since JavaSE-17
 */

/**
 * The Dictionary class contains functionality to parse word embeddings from a
 * file to a map, calculate vector distance, process user input to find similar
 * words, and create an output file containing N number of words and their
 * similarity scores.
 */
public class Dictionary {
	private Scanner input = new Scanner(System.in); // Instance of Scanner
	private final int NUMBER_OF_FEATURES = 50; // Number of elements in the vector (double array)

	/**
	 * Parses the provided file and builds a map. O(n) running time for reading the
	 * file. O(n) for splitting lines and parsing doubles. O(1) for populating the
	 * map. Therefore, the overall running time for the parse() method is O(n).
	 * 
	 * @param path A user specified word embeddings file.
	 * @return embeddings - A HashMap with key(word):vector(double[]) mapping.
	 * @throws Exception
	 */
	public Map<String, double[]> parse(String path) throws Exception {
		String key; // Stores word as a key for a map
		Map<String, double[]> embeddings = new HashMap<>(); // Map Word:Embedding
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
				// the values array
				for (int i = 1; i < tmp.length; i++) {
					value[i - 1] = Double.parseDouble(tmp[i]);
				}
				embeddings.put(key, value); // Populate the map
			}
			br.close();
		} catch (Exception e) {
			System.out.print(ConsoleColour.RED);
			throw new Exception("[ERROR] Encountered a problem parsing the file: " + e.getMessage());
		}
		System.out.print(ConsoleColour.GREEN);
		System.out.println("A map the size of " + embeddings.keySet().size() + " embeddings created");
		return embeddings;
	}

	/**
	 * Method to prompt the user to enter one or more words and validate the input
	 * (make sure the words provided are in the map). Looping over the userWords
	 * array takes O(n). Checking if the map contains the user word takes O(1).
	 * Therefore, the overall running time is O(n).
	 * 
	 * @param embeddings A map.
	 * @return userWords - A validated array of user words (all words entered by
	 *         user are in the map structure).
	 */
	public String[] getUserInput(Map<String, double[]> embeddings) {
		String[] userWords;
		String userInput;
		boolean mapContainsFlag;
		do {
			System.out.print(ConsoleColour.BLACK_BOLD_BRIGHT);
			mapContainsFlag = true;
			System.out.print("Enter a Word or Text>");
			userInput = input.nextLine();
			userWords = userInput.toLowerCase().split(" ");
			// Make sure map contains all user words
			for (int i = 0; i < userWords.length; i++) {
				if (!embeddings.containsKey(userWords[i])) {
					mapContainsFlag = false;
					System.out.print(ConsoleColour.RED);
					System.out.println("There is no \"" + userWords[i] + "\" in the map. Try a different word");
				}
			}
		} while (!mapContainsFlag);
		return userWords;
	}

	/**
	 * This method calculates the average embedding vector for a set of user input
	 * words. O(n) running time.
	 * 
	 * @param embeddings A map.
	 * @param userWords  A validated array of user words.
	 * @return userWordEmbeddings - An average of userWords vectors.
	 */
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

	/**
	 * This method calculates the Euclidean distance of 2 vectors. O(n) running
	 * time.
	 * 
	 * @param point1 Vector 1.
	 * @param point2 Vector 2.
	 * @return distance - Euclidean distance.
	 */
	public double calculateEuclideanDistance(double[] point1, double[] point2) {
		double sumOfSquaredDiffs = 0.0;
		for (int i = 0; i < point1.length; i++) {
			double diff = point2[i] - point1[i];
			sumOfSquaredDiffs += diff * diff;
		}
		double distance = Math.sqrt(sumOfSquaredDiffs);
		return distance;
	}

	/**
	 * This method calculates the Cosine distance of 2 vectors.
	 * 
	 * @param point1 Vector 1.
	 * @param point2 Vector 2.
	 * @return cosineDistance - Cosine Distance.
	 */
	public double calculateCosineDistance(double[] point1, double[] point2) {
		double dotProduct = dotProduct(point1, point2);
		double magnitude1 = vectorNorm(point1);
		double magnitude2 = vectorNorm(point2);
		double cosineSimilarity = dotProduct / (magnitude1 * magnitude2);
		// Cosine distance (1 - cosine similarity)
		double cosineDistance = 1 - cosineSimilarity;
		return cosineDistance;
	}

	/**
	 * This method calculates the dot product of 2 vectors. O(n) running time.
	 * 
	 * @param point1 Vector 1.
	 * @param point2 Vector 2.
	 * @return result - Dot product.
	 */
	public double dotProduct(double[] point1, double[] point2) {
		double result = 0;
		for (int i = 0; i < point1.length; i++) {
			result += point1[i] * point2[i];
		}
		return result;
	}

	/**
	 * This method calculates the vector norm (part of the Cosine distance
	 * calculation). O(n) running time.
	 * 
	 * @param point Vector.
	 * @return result - Vector norm.
	 */
	public double vectorNorm(double[] point) {
		double result = 0;
		for (double p : point) {
			result += p * p;
		}
		result = (double) Math.sqrt(result);
		return result;
	}

	/**
	 * This method calculates the similarity scores between the user input words and
	 * all words in the embeddings map.
	 * 
	 * @param userWords           Array of user words.
	 * @param embeddings          A map.
	 * @param comparisonSelection Algorithm selector.
	 * @return userScores - A list of similarity scores for each user word.
	 */
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

	/**
	 * This methods sorts the userScores list (Asc). Tim Sort O(n log n) running
	 * time.
	 * 
	 * @param userScores Unsorted list of similarity scores.
	 * @return userScores - Sorted.
	 */
	public List<Scores> sortWordScores(List<Scores> userScores) {
		userScores.sort((o1, o2) -> o1.compareTo(o2));
		return userScores;
	}

	/**
	 * Method to print processed words (results) to the console. O(n) running time.
	 * 
	 * @param userScores Sorted list of user scores.
	 * @param n          Number of words to print.
	 */
	public void printProcessedWords(List<Scores> userScores, int n) {
		System.out.print(ConsoleColour.WHITE_BOLD_BRIGHT);
		for (int i = 1; i < n + 1; i++) {
			var tmp = userScores.get(i);
			System.out.println("Word: " + tmp.getWord() + " - Score: " + tmp.getScore());
		}
	}

	/**
	 * This method processes a list of user input words by calculating their
	 * similarity scores with words from the embeddings map and writes the results
	 * to a file.
	 * 
	 * @param embeddings          A Map.
	 * @param n                   Top N.
	 * @param path                Embeddings file path.
	 * @param comparisonSelection Algorithm selector.
	 * @throws IOException
	 */
	public void processWords(Map<String, double[]> embeddings, int n, String path, int comparisonSelection)
			throws IOException {
		String[] userInput = getUserInput(embeddings);
		List<Scores> userScores = new ArrayList<>();
		userScores = getWordScores(userInput, embeddings, comparisonSelection);
		userScores = sortWordScores(userScores);
		printProcessedWords(userScores, n);
		writeToFile(path, buildString(userScores, n));
	}

	/**
	 * This method builds an output string.
	 * 
	 * @param scoresList Sorted list of similarity scores.
	 * @param n          Top N.
	 * @return stringBuilder - String for writing to a file.
	 */
	public static String buildString(List<Scores> scoresList, int n) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 1; i < n + 1; i++) {
			stringBuilder.append(scoresList.get(i).getWord()).append(", ").append(scoresList.get(i).getScore())
					.append("\n");
		}
		return stringBuilder.toString();
	}

	/**
	 * This method writes the results of program execution to a file.
	 * 
	 * @param fileName Output file name.
	 * @param words    Output string.
	 * @throws IOException
	 */
	public void writeToFile(String fileName, String words) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			// Write data to the file
			writer.write(words);
			System.out.print(ConsoleColour.GREEN);
			System.out.println("Data has been written to the file successfully");
			writer.close();
		} catch (IOException e) {
			System.out.print(ConsoleColour.RED);
			System.err.println("[ERROR] writing to the file: " + e.getMessage());
		}
	}
}