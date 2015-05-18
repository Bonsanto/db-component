package dependencies;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class JSON {
	private final String quote = "\"";
	private String json = "{";
	private boolean built = false;

	public JSON(JSON json) {
		this.json = json.json;
	}

	public JSON() {
	}

	public String getJson() {
		if (!built) this.build();
		return json;
	}


	public void addAttribute(String attribute, ResultSet rs) throws SQLException, ArrayIndexOutOfBoundsException {
		ResultSetMetaData rsmd = rs.getMetaData();
		rs.last();

		int columsNumber = rsmd.getColumnCount(),
				rowsNumber = rs.getRow(),
				currentRow = 0;
		rs.first();

		JSON[] rows = new JSON[rowsNumber];
		String[] columnNames = new String[columsNumber];

		for (int i = 1; i <= columnNames.length; i++)
			columnNames[i - 1] = rsmd.getColumnName(i);

		for (int rsRow = 1; rsRow <= rowsNumber; rsRow++) {
			rows[currentRow] = new JSON();

			for (int i = 1; i <= columsNumber; i++)
				rows[currentRow].addAttribute(columnNames[i - 1], rs.getObject(i));

			rows[currentRow].build();
			currentRow++;
			rs.next();
		}
		this.addAttribute(attribute, rows.length > 0 ? rows : rows[0]);
	}


	//TODO: FIX A BUG WHEN YOU USE THE JSON CONSTRUCTOR.
	public void addAttribute(String attribute, Object value) {
		StringBuilder stb = new StringBuilder();

		if (isArray(value)) {
			int length = Array.getLength(value);
			Object[] list = new Object[length];
			for (int i = 0; i < length; i++) list[i] = Array.get(value, i);
			addAttribute(attribute, list);
		} else {
			stb.append(json);
			stb.append(quote);
			stb.append(attribute);
			stb.append(quote);
			stb.append(": ");
			json = stb.toString();

			if (value instanceof JSON) {
				stb.append(((JSON) value).json);
				stb.append(", ");
				json = stb.toString();
			} else if (value instanceof String) {
				stb.append(quote);
				stb.append(value);
				stb.append(quote);
				stb.append(", ");
				json = stb.toString();
			} else {
				stb.append(value);
				stb.append(", ");
				json = stb.toString();
			}
		}
	}

	public void addAttribute(String attribute, Object[] value) {
		StringBuilder stb = new StringBuilder();
		stb.append(json);
		stb.append(quote);
		stb.append(attribute);
		stb.append(quote);
		stb.append(":[");
		json = stb.toString();

		for (Object aValue : value) {
			if (aValue instanceof JSON) {
				stb.append(((JSON) aValue).json);
				stb.append(", ");
				json = stb.toString();
			} else if (aValue instanceof String) {
				stb.append(quote);
				stb.append(aValue);
				stb.append(quote);
				stb.append(", ");
				json = stb.toString();
			} else {
				stb.append(aValue);
				stb.append(", ");
				json = stb.toString();
			}
		}
		if (value.length > 0) {
			stb.delete(0, stb.length());
			stb.append(json.substring(0, json.length() - 2));
			stb.append("], ");
			json = stb.toString();
		} else {
			stb.append("], ");
			json = stb.toString();
		}

	}

	private void build() {
		StringBuilder stb = new StringBuilder();
		stb.append(json.substring(0, json.length() - 2));
		stb.append("}");
		json = stb.toString();
		built = true;
	}

	//To solve a obsolete, prehistoric and primitive problem.
	private boolean isArray(Object obj) {
		return (obj != null && obj.getClass().isArray());
	}
}