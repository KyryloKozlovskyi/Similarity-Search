package ie.atu.sw;

public class Scores implements Comparable<Scores> {
	private String word;
	private double score;

	public Scores(String word, double score) {
		this.word = word;
		this.score = score;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	@Override
	public int compareTo(Scores o) {
		return Double.compare(this.score, o.score);
	}

}
