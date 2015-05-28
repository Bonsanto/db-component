package dependencies;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class DataTable {
	private String[] columnNames;

	private Object[][] values;

	public String[] getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}

	public Object[][] getValues() {
		return values;
	}

	public void setValues(Object[][] values) {
		this.values = values;
	}

	public void print() {
		if (this.values.length > 0) {
			for (Object[] value : values) {
				for (int j = 0; j < columnNames.length; j++) {
					System.out.println(this.columnNames[j] + ": " + value[j]);
				}
			}
		}
	}

	public void build(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int cn, rn;

		rs.last();
		cn = rsmd.getColumnCount();
		rn = rs.getRow();
		rs.first();

		this.columnNames = new String[cn];
		this.values = new Object[rn][cn];

		for (int i = 0; i < cn; i++)
			this.columnNames[i] = rsmd.getColumnName(i + 1);


		for (int i = 0; i < rn; i++) {
			for (int j = 0; j < cn; j++) {
				this.values[i][j] = rs.getObject(this.columnNames[j]);
			}
			rs.next();
		}

		rs.close();
	}
}
