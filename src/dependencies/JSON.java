package dependencies;

import sun.misc.BASE64Encoder;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;

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

		int columnsNumber = rsmd.getColumnCount();
		int rowsNumber = rs.getRow();
		JSON[] rows = new JSON[rowsNumber];
		String[] columnNames = new String[columnsNumber];

		rs.first();

		for (int i = 0; i < columnsNumber; i++)
			columnNames[i] = rsmd.getColumnName(i + 1);

		for (int row = 0; row < rowsNumber; row++) {
			rows[row] = new JSON();

			for (String columnName : columnNames)
				rows[row].addAttribute(columnName, rs.getObject(columnName));

			rows[row].build();
			rs.next();
		}
		this.addAttribute(attribute, rows.length > 0 ? rows : rows[0]);
	}


	//TODO: FIX A BUG WHEN YOU USE THE JSON CONSTRUCTOR.
	public void addAttribute(String attribute, Object value) {
		StringBuilder stb = new StringBuilder();

		if (isArray(value)) {
			if (value instanceof byte[]) {
				stb.append(json);
				stb.append(quote);
				stb.append(attribute);
				stb.append(quote);
				stb.append(":");
				stb.append(quote);
				json = stb.toString();
				stb.append((new BASE64Encoder().encode((byte[]) value)).replaceAll("\n|\r", ""));
				stb.append(quote);
				stb.append(",");
				json = stb.toString();
			} else {
				int length = Array.getLength(value);
				Object[] list = new Object[length];
				for (int i = 0; i < length; i++) list[i] = Array.get(value, i);
				addAttribute(attribute, list);
			}
		} else {
			stb.append(json);
			stb.append(quote);
			stb.append(attribute);
			stb.append(quote);
			stb.append(":");
			json = stb.toString();

			if (value instanceof JSON) {
				stb.append(((JSON) value).json);
			} else if (value instanceof String || value instanceof Date) {
				stb.append(quote);
				stb.append(value);
				stb.append(quote);
			} else {
				stb.append(value);
			}
			stb.append(",");
			json = stb.toString();
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
				stb.append(",");
				json = stb.toString();
			} else if (aValue instanceof String) {
				stb.append(quote);
				stb.append(aValue);
				stb.append(quote);
				stb.append(",");
				json = stb.toString();
			} else {
				stb.append(aValue);
				stb.append(",");
				json = stb.toString();
			}
		}
		if (value.length > 0) {
			stb.delete(0, stb.length());
			stb.append(json.substring(0, json.length() - 1));
			stb.append("],");
			json = stb.toString();
		} else {
			stb.append("],");
			json = stb.toString();
		}

	}

	private void build() {
		StringBuilder stb = new StringBuilder();
		stb.append(json.substring(0, json.length() - 1));
		stb.append("}");
		json = stb.toString();
		built = true;
	}

	//To solve a obsolete, prehistoric and primitive problem.
	private boolean isArray(Object obj) {
		return (obj != null && obj.getClass().isArray());
	}
}