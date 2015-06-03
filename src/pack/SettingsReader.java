package pack;

import dependencies.LogHandler;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;

public class SettingsReader {
	private final String[] SETTING_NAMES = {"ip", "name", "port", "log"};
	private final Object[] DEFAULT_SETTINGS = {"0.0.0.0", "Dispatcher", "8080", Level.SEVERE};

	public HashMap<String, Object> readSettings(String path, LogHandler log) throws Exception {
		HashMap<String, Object> stngs = new HashMap<>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new File(path));
		doc.getDocumentElement().normalize();

		if (Objects.equals(path, "")) {
			log.write(Level.SEVERE, "The file's path wasn't provided");
			throw new Exception("The file's path wasn't provided");
		}

		for (int i = 0; i < SETTING_NAMES.length; i++) {
			NodeList pro = doc.getElementsByTagName(SETTING_NAMES[i]);
			if (pro.item(0) == null)
				stngs.put(SETTING_NAMES[i], DEFAULT_SETTINGS[i]);
			else
				stngs.put(SETTING_NAMES[i], pro.item(0).getFirstChild().getNodeValue());
		}

		log.setMaxLevel((String) stngs.get("log"));
		return stngs;
	}
}
