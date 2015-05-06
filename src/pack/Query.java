package pack;

import java.util.Objects;

/**
 * Created by Bonsanto on 5/4/2015.
 */
public class Query {
	private String sentence;

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
		cleanQuery();
	}

	//TODO: Move this to setSentence???
	//Cleans query to remove some special characters.
	private void cleanQuery() {
		this.sentence = this.sentence.replaceAll("\t+", " ").replaceAll("\n+", " ").replaceAll(" +", " ");
	}

	public Query(String sentence) {
		this.sentence = sentence;
	}

	public Query() {
	}
}
