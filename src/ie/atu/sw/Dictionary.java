package ie.atu.sw;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Dictionary {
	private Scanner input = new Scanner(System.in); // Instance of Scanner
	private final static int NUMBER_OF_FEATURES = 50; //

	// Parses the file and builds a new map
	public Map<String, double[]> parse(String path) throws Exception {
		String key; // Stores word as a key for a map
		Map<String, double[]> embeddings = new HashMap<>(); // Map Word/Embedding
		String tmp[]; // Temporary stores an array of strings
		try (var br = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
			String next;
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
			throw new Exception("[ERROR] Encountered a problem parsing the file. " + e.getMessage());
		}
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
					System.out.println("There is no \"" + userWords[i] + "\" in the map. Try a different word!");
				}
			}
		} while (!mapContainsFlag);

		return userWords;

	}

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

	public double calculateEuclideanDistance(double[] point1, double[] point2) {
		double sumOfSquaredDiffs = 0.0;
		for (int i = 0; i < point1.length; i++) {
			double diff = point2[i] - point1[i];
			sumOfSquaredDiffs += diff * diff;
		}

		double distance = Math.sqrt(sumOfSquaredDiffs);
		return distance;
	}
}
