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
	}

	public String getQuery(String... parameters) throws Exception {
		//todo: probably a log here incase the query didn't load correctly.
		if (Objects.equals(sentence, "")) throw new Exception("The query doesn't have any content");

		String[] fields = sentence.split("\\$");

		//If the number of arguments doesn't match with the number of the "values" then throw an Exception.
		//todo: probably add a log here.
		if (numberNeeded(fields) != parameters.length)
			throw new Exception("The number of parameters doesn't match with the number required by this query");

		// If there are more parameters in the sentence.
		if (fields.length > 1) {
			int index = 0;

			for (int i = 0; i < fields.length; i++) {
				//If there is a value keyword it will be replaced with the new string.
				if (Objects.equals(fields[i], "value")) {
					fields[i] = parameters[index];
					index++;
				}
			}
		}
		return concat(fields);
	}

	private int numberNeeded(String... word) {
		int params = 0;

		for (String w : word) if (w.contains("value")) params++;

		return params;
	}

	private String concat(String... words) {
		String completeWord = "";

		for (String w : words) completeWord += w;

		//Tidy up the string and returns it.
		return completeWord.replaceAll("\t+", " ").replaceAll("\n+", " ").replaceAll(" +", " ");
	}

	public Query(String sentence) {
		this.sentence = sentence;
	}

	public Query() {
	}
}
