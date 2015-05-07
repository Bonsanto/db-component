package pack;

import java.util.ArrayList;

/**
 * Created by Bonsanto on 5/6/2015.
 */
public class Client {
	public static void main(String[] argv) {
		DispatcherService service = new DispatcherService();
		Dispatcher dispatcher = service.getDispatcherPort();
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(1);
		System.out.println(dispatcher.queryJSON("0", "1", parameters));
		parameters.clear();
		parameters.add(90);
		parameters.add("Raichu");
		//todo: Resolve problems with inserts.
		//todo: probably adding a contains for the "insert, update, delete" word.
		System.out.println(dispatcher.queryJSON("0", "2", parameters));
	}
}
