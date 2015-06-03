package dependencies;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class DataTable {
	private Object[][] values;
	private String[] columnNames;
	private int[] types;

	public int[] getTypes() {
		return types;
	}

	public void setTypes(int[] types) {
		this.types = types;
	}

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
		this.types = new int[cn];

		for (int i = 0; i < cn; i++) {
			this.columnNames[i] = rsmd.getColumnName(i + 1);
			this.types[i] = rsmd.getColumnType(i + 1);
		}

		for (int i = 0; i < rn; i++) {
			for (int j = 0; j < cn; j++) {
				this.values[i][j] = rs.getObject(this.columnNames[j]);
			}
			rs.next();
		}
	}

	public void addRows(ResultSet rs) throws SQLException {
		DataTable dt = new DataTable();
		dt.build(rs);

		int rows = this.getValues().length + dt.getValues().length;
		int columns = this.getColumnNames().length;

		Object[][] values = new Object[rows][columns];
		String[] columnNames = new String[columns];
		int[] types = new int[columns];

		for (int i = 0; i < this.columnNames.length; i++) {
			types[i] = this.getTypes()[i];
			columnNames[i] = this.getColumnNames()[i];
		}

		for (int i = 0; i < this.values.length; i++) {
			for (int j = 0; j < this.columnNames.length; j++) {
				values[i][j] = this.values[i][j];
			}
		}

		for (int i = 0, x = this.values.length; i < dt.getValues().length; i++) {
			for (int j = 0; j < this.columnNames.length; j++) {
				values[x + i][j] = dt.getValues()[i][j];
			}
		}

		this.columnNames = columnNames;
		this.types = types;
		this.values = values;
	}
}
