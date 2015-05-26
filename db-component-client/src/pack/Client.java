package pack;

import java.util.ArrayList;

/**
 * Created by Bonsanto on 5/26/2015.
 */
public class Client {
	public static void main(String[] argv) {
		Dispatcher d = new DispatcherService().getDispatcherPort();
		long t = System.nanoTime();
		ArrayList<String> queries = new ArrayList<>();
		ArrayList<Object> parameters = new ArrayList<>();
		queries.add("1");
		queries.add("2");
		queries.add("3");
		parameters.add(2);

//    for (int i = 0; i < 100000; i++) {
//      System.out.println(d.makeTransaction("1", queries, parameters));
//    }
		String parameter = "cuchi3.jpg";
		ArrayList<Object> params = new ArrayList<>();
		params.add(parameter);
		String json = null;
		try {
			System.out.println(d.queryJSON("2", "2", params));
			json = d.queryJSON("2", "1", new ArrayList<>());
			System.out.println(json);
			System.out.println(d.writeEntireCSV("2", "1", "many pics.csv", new ArrayList<>()));
			System.out.println(d.writeEntireCSV("2", "2", "one pic.csv", params));
			System.out.println((System.nanoTime() - t) / 1000000000);
			System.out.println(d.queryJSON("1", "3", new ArrayList<>()));
			System.out.println(d.writeJSON("1", "3", "json.json", new ArrayList<>()));
		} catch (IOException_Exception e) {
			e.printStackTrace();
		}
	}
}
