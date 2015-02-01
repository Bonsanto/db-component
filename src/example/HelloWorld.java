package example;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

/**
 * Created by Bonsanto on 11/26/2014.
 */
@WebService()
public class HelloWorld {
	private int w = 0;

	@WebMethod
	public String sayHelloWorldFrom(String from) {
		String result = "Hello world, from " + from;
		System.out.println(result);
		w++;
		System.out.println(w);
		return result;
	}

	
	public static void main(String[] argv) {
		Object implementor = new HelloWorld();
		String address = "http://localhost:9000/HelloWorld";
		Endpoint.publish(address, implementor);
	}
}
