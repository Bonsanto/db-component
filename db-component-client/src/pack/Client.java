package pack;

import java.util.ArrayList;

/**
 * Created by Bonsanto on 5/14/2015.
 */
public class Client {
	public static void main(String[] argv) {
		DispatcherService dispatcherService = new DispatcherService();
		Dispatcher dispatcher = dispatcherService.getDispatcherPort();

		System.out.println(dispatcher.queryCSV("0", "0", "file.csv", new ArrayList<Object>()));
	}
}