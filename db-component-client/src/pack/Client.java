package pack;

import java.util.ArrayList;

/**
 * Created by Bonsanto on 5/16/2015.
 */
public class Client {
	public static void main(String[] argv) {
		Dispatcher d = new DispatcherService().getDispatcherPort();
		System.out.println(d.writeEntireCSV("1", "4", "pepe.csv", new ArrayList<>()));
	}
}
