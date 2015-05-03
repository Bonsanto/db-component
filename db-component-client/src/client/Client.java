package client;


/**
 * Created by Bonsanto on 5/2/2015.
 */
public class Client {
	public static void main(String[] argv) {
		DispatcherService service = new DispatcherService();
		Dispatcher dispatcher = service.getDispatcherPort();
		System.out.println(dispatcher.queryJSON(""));
	}
}
