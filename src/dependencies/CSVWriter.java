package dependencies;

import com.sun.deploy.util.ArrayUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class CSVWriter {
	//todo: should include the columns names.
	private final String DEFAULT_COLUMNS_SEPARATOR = ",",
			DEFAULT_ROWS_SEPARATOR = "\\n";
	private String columnsSeparator,
			rowsSeparator;

	//Method that writes the CSV file in the defined path, without writing the column's names.
	public void writeSimpleCSV(String path, ResultSet rs) throws IOException, SQLException {
		FileWriter fileWriter = new FileWriter(path);
		PrintWriter printWriter = new PrintWriter(fileWriter);

		//Move through the resultset...
		ResultSetMetaData rsmd = rs.getMetaData();
		rs.last();

		int columnNumber = rsmd.getColumnCount(),
				rowsNumber = rs.getRow(),
				currentRow = 0;
		String[] rows = new String[rowsNumber];

		rs.first();
		for (int rsRow = 1; rsRow <= rowsNumber; rsRow++) {
			rows[currentRow] = "";

			for (int i = 1; i <= columnNumber; i++) {
				rows[currentRow] += rs.getObject(i) + (columnNumber == i ? "" : columnsSeparator);
			}
			printWriter.println(rows[currentRow]);
			currentRow++;
			rs.next();
		}
		printWriter.flush();
		printWriter.close();
		fileWriter.close();
	}

	//Constructor in case the separators are defined.
	public CSVWriter(String columnsSeparator, String rowsSeparator) {
		this.columnsSeparator = columnsSeparator;
		this.rowsSeparator = rowsSeparator;
	}

	//Constructor in case de separators are not defined.
	public CSVWriter() {
		this.columnsSeparator = DEFAULT_COLUMNS_SEPARATOR;
		this.rowsSeparator = DEFAULT_ROWS_SEPARATOR;
	}
}
