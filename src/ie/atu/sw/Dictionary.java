package ie.atu.sw;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Dictionary {
	private final static int NUMBER_OF_FEATURES = 50; // 
	private String key; // Stores word as a key for a map
	private Double[] value = new Double[NUMBER_OF_FEATURES]; // Stores vector(array of doubles) as a value for a map

	// Parses the file and builds a new map
	public Map<String, Double[]> parse(String path) throws Exception {
		Map<String, Double[]> embeddings = new HashMap<>(); // Map Word/Embedding
		String tmp[]; // Temporary stores an array of strings
		try (var br = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
			String next;
			while ((next = br.readLine()) != null) { // Loop through each line in the embedding file
				tmp = next.split(",");
				key = tmp[0];
				// Loop through the array starting at index 1 to avoid the "word" and populate
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
}
