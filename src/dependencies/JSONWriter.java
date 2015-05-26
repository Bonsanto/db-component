package dependencies;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JSONWriter {
	private FileWriter fileWriter;
	private PrintWriter printWriter;

	//Method that writes a JSON in a file.
	public void writeJSON(ResultSet rs) throws SQLException, IOException {
		JSON json = new JSON();
		json.addAttribute("response", rs);
		this.printWriter.append(json.getJson());
		this.close();
	}

	//Method that closes all the connections.
	private void close() throws IOException {
		this.printWriter.flush();
		this.fileWriter.close();
		this.printWriter.close();
	}

	//Method that open all the buffers to write in the disk.
	private void setAll(String path) throws IOException {
		this.fileWriter = new FileWriter(path);
		this.printWriter = new PrintWriter(this.fileWriter);
	}

	public JSONWriter(String path) throws IOException {
		this.setAll(path);
	}
}
