
import javax.ejb.Singleton;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

/**
 * Created by Marek on 2015-05-28.
 *
 */
@Singleton
@WebService(targetNamespace = "http://test")
public class IndexManager {
  @WebMethod
  public String sayHelloWorldFrom(String from) {
    String result = "Hello, world, from " + from;
    System.out.println(result);
    return result;
  }
  public static void main(String[] argv) {
    Object implementor = new IndexManager ();
    String address = "http://localhost:9000/IndexManager";
    Endpoint.publish(address, implementor);
  }
}
