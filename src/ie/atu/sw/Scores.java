package ie.atu.sw;

/**
 * @author Kyrylo Kozlovskyi G00425385
 * @version 1.0
 * @since JavaSE-17
 */

/**
 * Custom datatype for storing and comparing Scores objects.
 */
public class Scores implements Comparable<Scores> {
	private String word;
	private double score;

	/**
	 * Scores Constructor.
	 * 
	 * @param word
	 * @param score
	 */
	public Scores(String word, double score) {
		this.word = word;
		this.score = score;
	}

	/**
	 * score getter.
	 * 
	 * @return score
	 */
	public double getScore() {
		return score;
	}

	/**
	 * score setter.
	 * 
	 * @param score
	 */
	public void setScore(double score) {
		this.score = score;
	}

	/**
	 * word getter.
	 * 
	 * @return word
	 */
	public String getWord() {
		return word;
	}

	/**
	 * word setter.
	 * 
	 * @param word
	 */
	public void setWord(String word) {
		this.word = word;
	}

	/**
	 * Overridden compareTo method to compare Scores objects by its score value.
	 * 
	 * @param o
	 */
	@Override
	public int compareTo(Scores o) {
		return Double.compare(this.score, o.score);
	}

}