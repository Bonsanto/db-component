package pack;

import java.util.ArrayList;

/**
 * Created by Bonsanto on 5/8/2015.
 */
public class Client {
	public static void main(String[] argv) {
		DispatcherService service = new DispatcherService();
		Dispatcher dispatcher = service.getDispatcherPort();

		//Query execution.
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(1);
		System.out.println(dispatcher.queryJSON("0", "1", parameters));
		parameters.clear();
		parameters.add(90);
		parameters.add("Raichu");
		//todo: Resolve problems with inserts.
		//todo: probably adding a contains for the "insert, update, delete" word.
		System.out.println(dispatcher.queryJSON("0", "2", parameters));

		//Transaction execution.
		ArrayList<String> queries = new ArrayList<>();
		ArrayList<Object> data = new ArrayList<>();
		int idPokemon = 23;
		String naProduct = "Papa",
				newNaProduct = "Queso";

		queries.add("1");
		queries.add("2");

		data.add(idPokemon);
		data.add(naProduct);
		data.add(newNaProduct);
		data.add(idPokemon);

		System.out.println(dispatcher.makeTransaction("2", queries, data));
	}
}
