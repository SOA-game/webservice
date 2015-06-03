package controlers.model;

import model.ElementEntity;
import model.ElementyEntity;
import model.KategoriaEntity;
import model.KategorieEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.util.List;

/**
 * Created by Marek on 2015-05-28.
 *
 */
@Singleton
@WebService(targetNamespace = "http://test")
public class IndexManager {

  private SessionFactory sessionFactory;

  private <T extends Action> T perform(T a) {
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

    return a;
  }

  @WebMethod
  public void addCategory(final KategoriaEntity typ) {
    perform(new Saver(typ));
  }

  @WebMethod
  public void addElement(final ElementEntity typ) {
    perform(new Saver(typ));
  }

  @SuppressWarnings("unchecked")
  @WebMethod
  public List<KategorieEntity> getCategoryTypes() {
    Getter entity = new Getter("KategorieEntity");
    perform(entity);
    return entity.getList();
  }

  @SuppressWarnings("unchecked")
  @WebMethod
  public List<ElementyEntity> getElementTypes() {
    Getter entity = new Getter("ElementyEntity");
    perform(entity);

    return entity.getList();
  }

  @SuppressWarnings("unchecked")
  @WebMethod
  public List<KategoriaEntity> getCategories() {
    Getter entity = new Getter("KategoriaEntity");
    perform(entity);
    System.out.println(entity.getList());

    return entity.getList();
  }

  public KategoriaEntity getCategoryEntity(final int id) {
    return perform(new Action() {
      KategoriaEntity kategoria;

      @Override
      public void run(Session session) {
        kategoria = (KategoriaEntity) session.createQuery("from KategoriaEntity ke where ke.id = " + id).uniqueResult();
      }
    }).kategoria;
  }

  public void removeCategory(final KategoriaEntity entity) {
    perform(new Action() {
      @Override
      public void run(Session session) {
        int result = session.createQuery("delete from KategoriaEntity where id = :id")
                .setParameter("id", entity.getId())
                .executeUpdate();
        System.out.println(result);
      }
    });
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

interface Action {
  void run(Session session);
}

class Saver implements Action {
  private Object object;

  public Saver(Object object) {
    this.object = object;
  }

  @Override
  public void run(Session session) {
    session.save(object);
  }
}

class Getter implements Action {
  private List list;

  public Getter(String entity) {
    this.entity = entity;
  }

  private String entity;

  @Override
  public void run(Session session) {
    list = session.createQuery("from " + entity).list();
  }

  public List getList() {
    return list;
  }
}