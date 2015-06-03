package pack;

import java.util.ArrayList;

/**
 * Created by Bonsanto on 5/28/2015.
 */
public class Client {
	public static void main(String[] argv) {
		Dispatcher d = new DispatcherService().getDispatcherPort();

		try {
			ArrayList<Object> parameters = new ArrayList<>();
			parameters.add("1");
			DataTable dt = d.queryDataTable("1", "3", new ArrayList<>());
			DataTable dx = d.queryDataTable("1", "1", new ArrayList<>());

//			for (int i = 0; i < dt.values.size(); i++) {
//				for (int j = 0; j < dt.columnNames.size(); j++) {
//					System.out.println(dt.columnNames.get(j) + ", " + dt.types.get(j) + " : " + dt.values.get(i).item.get(j));
//				}
//			}
//
//			for (int i = 0; i < dx.values.size(); i++) {
//				for (int j = 0; j < dx.columnNames.size(); j++) {
//					System.out.println(dx.columnNames.get(j) + ", " + dx.types.get(j) + " : " + dx.values.get(i).item.get(j));
//				}
//			}
			for (int i = 0; i < 10; i++) {
				d.writeJSON("1", "1", "favio.json", parameters);
				d.writeEntireCSV("1","1","favio.csv", new ArrayList<>());
				System.out.println(d.queryJSON("1", "1", new ArrayList<>()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
