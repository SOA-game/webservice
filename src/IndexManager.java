
import model.ElementEntity;
import model.ElementyEntity;
import model.KategoriaEntity;
import model.KategorieEntity;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import javax.annotation.PostConstruct;
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

  private SessionFactory sessionFactory;

  @WebMethod
  public String sayHelloWorldFrom(String from) {
    Session session = sessionFactory.openSession();
    Transaction transaction = session.beginTransaction();
    Query query = session.createQuery("from KategorieEntity");

    StringBuilder sb = new StringBuilder();
    for (Object o : query.list()) {
      KategorieEntity entity = (KategorieEntity) o;
      sb.append(entity.getAtrybut()).append(entity.getNazwa());
    }

    transaction.commit();
    session.close();

    String result = "Hello!, world, from " + from + "\n" + sb.toString();
    System.out.println(result);
    return result;
  }

  private interface Action {
    void run(Session session);
  }

  public class Saver implements Action {
    private Object object;

    public Saver(Object object) {
      this.object = object;
    }

    @Override
    public void run(Session session) {
      session.save(object);
    }
  }

  private void perform(Action a) {
    Transaction transaction = null;
    Session session = sessionFactory.openSession();
    try {
      transaction = session.beginTransaction();

      a.run(session);

      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
      if (transaction != null) {
        transaction.rollback();
      }
    } finally {
      session.close();
    }
  }

  @WebMethod
  public void addCategoryType(final KategorieEntity typ) {
    perform(new Saver(typ));
  }

  @WebMethod
  public void addCategory(final KategoriaEntity typ) {
    perform(new Saver(typ));
  }

  @WebMethod
  public void addElementType(final ElementyEntity typ) {
    perform(new Saver(typ));
  }

  @WebMethod
  public void addElement(final ElementEntity typ) {
    perform(new Saver(typ));
  }



  @PostConstruct
  void prepare() {
    Configuration configuration = new Configuration();
    configuration.configure();

    ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
  }

  public static void main(String[] argv) {
    Object implementor = new IndexManager ();
    String address = "http://localhost:9000/IndexManager";
    Endpoint.publish(address, implementor);
  }
}
