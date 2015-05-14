package dependencies;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class JSON {
	private static String quote = "\"";
	protected String json = "{";

	public JSON(JSON json) {
		this.json = json.json;
	}

	public JSON() {
		this.json = "{";
	}

	public String getJson() {
		return json;
	}


	public void addAttribute(String attribute, ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		rs.last();

		int columsNumber = rsmd.getColumnCount(),
				rowsNumber = rs.getRow(),
				currentRow = 0;
		rs.first();

		JSON[] rows = new JSON[rowsNumber];

		for (int rsRow = 1; rsRow <= rowsNumber; rsRow++) {
			rows[currentRow] = new JSON();

			for (int i = 1; i <= columsNumber; i++) {
				String name = rsmd.getColumnName(i);
				rows[currentRow].addAttribute(name, rs.getObject(i));
			}
			rows[currentRow].build();
			currentRow++;
			rs.next();
		}
		if (rows.length > 0)
			this.addAttribute(attribute, rows);
		else
			this.addAttribute(attribute, rows[0]);
	}


	//TODO: FIX A BUG WHEN YOU USE THE JSON CONSTRUCTOR.
	public void addAttribute(String attribute, Object value) {
		StringBuilder concat = new StringBuilder();

		if (isArray(value)) {
			int length = Array.getLength(value);
			Object[] list = new Object[length];
			for (int i = 0; i < length; i++) list[i] = Array.get(value, i);
			addAttribute(attribute, list);
		} else {
			concat.append(json);
			concat.append(quote);
			concat.append(attribute);
			concat.append(quote);
			concat.append(": ");
			json = concat.toString();

			if (value instanceof JSON) {
				concat.append(((JSON) value).json);
				concat.append(", ");
				json = concat.toString();
			} else if (value instanceof String) {
				concat.append(quote);
				concat.append(value);
				concat.append(quote);
				concat.append(", ");
				json = concat.toString();
			} else {
				concat.append(value);
				concat.append(", ");
				json = concat.toString();
			}
		}
	}

	public void addAttribute(String attribute, Object[] value) {
		StringBuilder concat = new StringBuilder();
		concat.append(json);
		concat.append(quote);
		concat.append(attribute);
		concat.append(quote);
		concat.append(":[");
		json = concat.toString();

		for (Object aValue : value) {
			if (aValue instanceof JSON) {
				concat.append(((JSON) aValue).json);
				concat.append(", ");
				json = concat.toString();
			} else if (aValue instanceof String) {
				concat.append(quote);
				concat.append(aValue);
				concat.append(quote);
				concat.append(", ");
				json = concat.toString();
			} else {
				concat.append(aValue);
				concat.append(", ");
				json = concat.toString();
			}
		}
		if (value.length > 0) {
			concat.delete(0, concat.length());
			concat.append(json.substring(0, json.length() - 2));
			concat.append("], ");
			json = concat.toString();
		} else {
			concat.append("], ");
			json = concat.toString();
		}

	}

	public void build() {
		StringBuilder concat = new StringBuilder();
		concat.append(json.substring(0, json.length() - 2));
		concat.append("}");
		json = concat.toString();
	}

	//To solve a obsolete, prehistoric and primitive problem.
	private boolean isArray(Object obj) {
		return (obj != null && obj.getClass().isArray());
	}
}