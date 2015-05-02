package dependencies;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

public class JSONBuilder {
	String name;
	String properties = "{\n";
	Boolean built;

	public void setName(String s) {
		name = "var " + s + " =\n";
	}

	public void addProperty(String a, Integer b) {
		properties += "\t" + '"' + a + '"' + ": " + b.toString() + ",\n";
	}

	public void addProperty(String a, String b) {
		properties += "\t" + '"' + a + '"' + ": " + '"' + b + '"' + ",\n";
	}

	public void addProperty(String a, Double b) {
		properties += "\t" + '"' + a + '"' + ": " + b.toString() + ",\n";
	}

	public void addProperty(String a, BigDecimal b) {
		properties += "\t" + '"' + a + '"' + ": " + b.toString() + ",\n";
	}

	public void addProperty(String a, Float b) {
		properties += "\t" + '"' + a + '"' + ": " + b.toString() + ",\n";
	}

	public void addProperty(String a, Integer[] b) {
		properties += "\t" + '"' + a + '"' + ": \n" + "[";
		for (int i = 0; i < b.length; i++) {
			properties += "\t" + b[i].toString();
			if (i == b.length - 1)
				properties += "]\n";
			else
				properties += ",\n";
		}
		properties += ",\n";
	}

	public void addProperty(String a, String[] b) {
		properties += "\t" + '"' + a + '"' + ":\n " + "[";
		for (int i = 0; i < b.length; i++) {
			properties += "\t" + b[i];
			if (i == b.length - 1)
				properties += "]\n";
			else
				properties += ",\n";
		}
		properties += ",\n";
	}

	public void addProperty(String a, JSONBuilder b) {
		properties += "\t" + '"' + a + '"' + ": " + b.JSON() + ",\n";
	}

	public void addProperty(String a, JSONBuilder[] b) {
		properties += "\t" + '"' + a + '"' + ":\n " + "[";
		for (int i = 0; i < b.length; i++) {
			properties += "\t" + b[i].JSON();
			if (i == b.length - 1)
				properties += "]\n";
			else
				properties += ",\n";
		}
		properties += ",\n";
	}

	public void addProperty(ResultSet a, String queryname) {
		try {
			ResultSet rs = a;
			ResultSetMetaData rsmd = rs.getMetaData();
			int numcolumn;
			numcolumn = rsmd.getColumnCount();
			rs.last();
			JSONBuilder[] rows = new JSONBuilder[rs.getRow()];
			int xba = 0;
			rs.first();
			for (int fil = 1; fil <= rows.length; fil++) {
				rows[xba] = new JSONBuilder();
				for (int i = 1; i <= numcolumn; i++) {
					String nombre = rsmd.getColumnName(i);
					String tipo = rs.getObject(i).getClass().getSimpleName();
					if (tipo.equalsIgnoreCase("Integer")) {
						Integer si = (Integer) rs.getObject(i);
						rows[xba].addProperty(nombre, si);
					}
					if (tipo.equalsIgnoreCase("Double")) {
						Double sd = (Double) rs.getObject(i);
						rows[xba].addProperty(nombre, sd);
					}
					if (tipo.equalsIgnoreCase("String")) {
						String ss = (String) rs.getObject(i);
						rows[xba].addProperty(nombre, ss);
					}
					if (tipo.equalsIgnoreCase("BigDecimal")) {
						BigDecimal sbd = (BigDecimal) rs.getObject(i);
						rows[xba].addProperty(nombre, sbd);
					}
					if (tipo.equalsIgnoreCase("Numeric")) {
						Float sf = (Float) rs.getObject(i);
						rows[xba].addProperty(nombre, sf);
					}
					if (tipo.equalsIgnoreCase("Integer[]")) {
						Integer[] sai = (Integer[]) rs.getObject(i);
						rows[xba].addProperty(nombre, sai);
					}
					if (tipo.equalsIgnoreCase("String[]")) {
						String[] sas = (String[]) rs.getObject(i);
						rows[xba].addProperty(nombre, sas);
					}
					if (rsmd.getColumnType(i) == Types.DATE) {
						String sd = rs.getObject(i).toString();
						rows[xba].addProperty(nombre, sd);
					}
				}
				rows[xba].build();
				xba++;
				rs.next();
			}
			if (rows.length == 1) {
				this.addProperty(queryname, rows[0]);
			} else {
				this.addProperty(queryname, rows);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addProperty(String a, Map<Integer, Integer> mapa, String key, String value) {
		JSONBuilder[] keyvalue = new JSONBuilder[mapa.size()];
		int i = 0;
		for (Map.Entry<Integer, Integer> entry : mapa.entrySet()) {
			keyvalue[i] = new JSONBuilder();
			keyvalue[i].addProperty(key, entry.getKey());
			keyvalue[i].addProperty(value, entry.getValue());
			keyvalue[i].build();
			i++;
		}
		this.addProperty(a, keyvalue);
	}

	public void build() {
		this.properties = (this.properties.substring(0, this.properties.length() - 2));
		this.properties += "\n}";
		this.built = true;
	}

	public String JSON() {
		if (this.built == true)
			return this.properties;
		else
			return "not built";
	}
}