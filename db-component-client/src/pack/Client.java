package pack;

import java.util.ArrayList;

/**
 * Created by Bonsanto on 5/5/2015.
 */
public class Client {
	public static void main(String[] argv) {
		DispatcherService service = new DispatcherService();
		Dispatcher dispatcher = service.getDispatcherPort();
		ArrayList<String> parameters = new ArrayList<>();
		parameters.add("1");
		System.out.println(dispatcher.queryJSON("0", "1", parameters));
	}
}

