package pack;

import java.util.ArrayList;

/**
 * Created by Bonsanto on 5/16/2015.
 */
public class Client {
	public static void main(String[] argv) {
		Dispatcher d = new DispatcherService().getDispatcherPort();
		System.out.println(d.writeEntireCSV("1", "4", "pepe.csv", new ArrayList<>()));
		long st = System.nanoTime();
		for (int i = 0; i < 100000; i++) {
			d.queryJSON("1", "4", new ArrayList<>());
		}
		System.out.println((System.nanoTime() - st) / 1000000000 + " seconds");
	}
}
