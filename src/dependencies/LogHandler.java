package dependencies;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Level;
import java.util.Calendar;

public class LogHandler {
	private final String
			DEFAULT_COLUMNS_SEPARATOR = ",",
			DEFAULT_ROWS_SEPARATOR = "\n",
			FILE_SEPARATOR = "-";
	private String
			folder,
			path,
			columnsSeparator,
			rowsSeparator;
	private Calendar cal;
	private FileWriter fileWriter;
	private PrintWriter printWriter;
	private Level maxLevel;

	public void close() throws IOException {
		this.printWriter.flush();
		this.printWriter.close();
		this.fileWriter.close();
	}

	public void write(Level lvl, String message) throws IOException {
		if (this.maxLevel.intValue() <= lvl.intValue()) {
			Calendar cal = Calendar.getInstance();
			String log = this.makeMessage(new Date().toString(), lvl.toString(), message);

			if (!this.cal.equals(cal)) {
				this.close();
				this.cal = cal;
				this.concatenateName();
				this.fileWriter = new FileWriter(this.path, true);
				this.printWriter = new PrintWriter(this.fileWriter);
			}

			this.printWriter.append(log);
		}
		this.close();
	}

	//Method concatenates the array of strings and returns it.
	private String makeMessage(String... strings) {
		return String.join(this.columnsSeparator, strings) + this.rowsSeparator;
	}

	private void setBuffers(String folder) throws IOException {
		this.cal = Calendar.getInstance();
		this.folder = folder;
		this.concatenateName();
		this.fileWriter = new FileWriter(this.path, true);
		this.printWriter = new PrintWriter(this.fileWriter);
	}

	private void setSeparators(String cs, String rs) {
		this.columnsSeparator = cs;
		this.rowsSeparator = rs;
	}

	private void concatenateName() {
		this.path = new File("").getAbsolutePath() + File.separator + this.folder +
				File.separator + cal.get(Calendar.YEAR) + FILE_SEPARATOR + cal.get(Calendar.MONTH) + 1 +
				FILE_SEPARATOR + cal.get(Calendar.DATE) + "Log.csv";
	}

	public LogHandler(String folder, String columnsSeparator, String rowsSeparator) throws IOException {
		this.setBuffers(folder);
		this.setSeparators(columnsSeparator, rowsSeparator);
	}

	public Level getMaxLevel() {
		return maxLevel;
	}

	public void setMaxLevel(String maxLevel) {
		this.maxLevel = Level.parse(maxLevel.toUpperCase());
	}

	public LogHandler() throws IOException {
		this.setBuffers("");
		this.setSeparators(DEFAULT_COLUMNS_SEPARATOR, DEFAULT_ROWS_SEPARATOR);
	}
}