package dependencies;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class CSVWriter {
	//todo: should include the columns names.
	private final String
			DEFAULT_COLUMNS_SEPARATOR = ",",
			DEFAULT_ROWS_SEPARATOR = "\n";
	private String
			columnsSeparator,
			rowsSeparator;
	private FileWriter fileWriter;
	private PrintWriter printWriter;

	//Method that writes the CSV file in the defined path, without the name of the columns.
	public void writeCSV(ResultSet rs) throws IOException, SQLException {
		this.printWriter.append(dataStringify(rs));
		this.close();
	}

	//Method that writes the CSV in the defined path, including the name of the columns,
	public void writeCompoundCSV(ResultSet rs) throws IOException, SQLException {
		String[] text = {metaStringify(rs), dataStringify(rs)};

		this.printWriter.append(String.join(rowsSeparator, text));
		this.close();
	}

	//Method that provides a String with the name of all the columns of the resultset.
	private String metaStringify(ResultSet rs) throws IOException, SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		String[] columns = new String[rsmd.getColumnCount()];

		rs.first();
		for (int i = 1; i <= columns.length; i++)
			columns[i - 1] = rsmd.getColumnName(i);

		return String.join(columnsSeparator, columns);
	}

	//Method that provides a String with the data attached to the resultset.
	private String dataStringify(ResultSet rs) throws IOException, SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		rs.last();

		int columnNumber = rsmd.getColumnCount(),
				rowsNumber = rs.getRow(),
				currentRow = 0;
		String[] rows = new String[rowsNumber];

		rs.first();

		//Move through the resultset...
		for (int rsRow = 1; rsRow <= rowsNumber; rsRow++) {
			rows[currentRow] = "";

			for (int i = 1; i <= columnNumber; i++) {
				rows[currentRow] += rs.getObject(i) + (columnNumber == i ? "" : columnsSeparator);
			}
			currentRow++;
			rs.next();
		}
		return String.join(rowsSeparator, rows);
	}

	//Method that flushes and closes the PrintWriter buffer and the FileWriter.
	private void close() throws IOException {
		this.printWriter.flush();
		this.printWriter.close();
		this.fileWriter.close();
	}

	//Utility method that improves legibility, instantiates the buffers and other things.
	private void setAll(String path, String cs, String rs) throws IOException {
		this.fileWriter = new FileWriter(path);
		this.printWriter = new PrintWriter(this.fileWriter);
		this.columnsSeparator = cs;
		this.rowsSeparator = rs;
	}

	//Constructor in case the separators are defined.
	public CSVWriter(String path, String columnsSeparator, String rowsSeparator) throws IOException {
		this.setAll(path, columnsSeparator, rowsSeparator);
	}

	//Constructor in case de separators are not defined.
	public CSVWriter(String path) throws IOException {
		this.setAll(path, DEFAULT_COLUMNS_SEPARATOR, DEFAULT_ROWS_SEPARATOR);
	}
}
