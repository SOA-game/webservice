package controlers.model;

import javafx.util.Pair;
import model.ElementEntity;
import model.ElementyEntity;
import model.KategoriaEntity;
import model.KategorieEntity;
import org.hibernate.Query;
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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marek on 2015-05-28.
 *
 */
@Singleton
@WebService(targetNamespace = "http://test")
public class IndexManager implements Serializable {


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
    return perform(
            new HAI("CAN HAS CATEGORY TYPES?")
                    .I_HAS_A_LIST())
            .KTHXBYE();
  }

  @SuppressWarnings("unchecked")
  @WebMethod
  public List<ElementyEntity> getElementTypes() {
    return perform(
            new HAI("CAN HAS ELEMENT TYPES?")
                    .I_HAS_A_LIST())
            .KTHXBYE();
  }

  @SuppressWarnings("unchecked")
  @WebMethod
  public List<KategoriaEntity> getCategories() {
    return perform(
            new HAI("CAN HAS CATEGORIES?")
                    .I_HAS_A_LIST())
            .KTHXBYE();
  }

  @WebMethod
  public KategoriaEntity getCategoryEntity(final int id) {
    return (KategoriaEntity) perform(
            new HAI("CAN HAS CATEGORY?")
                    .I_HAS_A_UNIQUE_RESULT()
                    .IM_IN_YR_DECLARATION()
                    .AND("IZ_ID", Integer.toString(id))
                    .IM_OUTTA_YR_DECLARATION())
            .KTHXBYE();
//    return (KategoriaEntity) perform(new EntityGetter(id, "KategoriaEntity")).KTHXBYE();
  }

  @WebMethod
  public ElementEntity getElementEntity(final int id) {
    return (ElementEntity) perform(
            new HAI("CAN HAS ELEMENT?")
                    .I_HAS_A_UNIQUE_RESULT()
                    .IM_IN_YR_DECLARATION()
                    .AND("IZ_ID", String.valueOf(id))
                    .IM_OUTTA_YR_DECLARATION())
            .KTHXBYE();
  }

  @WebMethod
  public void removeCategory(final int id) {
    perform(new Action() {
      @Override
      public void run(Session session) {
        session.createQuery("delete from ElementEntity where kategoria = :id")
                .setParameter("id", id)
                .executeUpdate();
        session.createQuery("delete from KategoriaEntity where id = :id")
                .setParameter("id", id)
                .executeUpdate();
      }
    });
  }

  @WebMethod
  public void removeElement(final int id) {
    perform(new Action() {
      @Override
      public void run(Session session) {
        session.createQuery("delete from ElementEntity where id = :id")
                .setParameter("id", id)
                .executeUpdate();
      }
    });
  }

  @WebMethod
  public void editElement(final ElementEntity entity) {
    perform(new Updater(entity));
  }

  @WebMethod
  public void editCategory(final KategoriaEntity entity) {
    perform(new Updater(entity));
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

class EntityGetter implements Action {

  private int id;
  private Object result;
  private String entity;

  EntityGetter(int id, String entity) {
    this.id = id;
    this.entity = entity;
  }

  @Override
  public void run(Session session) {
    result = session.createQuery("from " + entity + " where id=:id")
            .setParameter("id", id)
            .uniqueResult();
  }

  public Object getResult() {
    return result;
  }
}

class HAI {

  private final String query;

  HAI(String query) {
    this.query = query;
  }

  public UNIQUE_RESULT I_HAS_A_UNIQUE_RESULT() {
    return new UNIQUE_RESULT(query);
  }

  public LIST_RESULT I_HAS_A_LIST() {
    return new LIST_RESULT(query);
  }
}

abstract class RESULT implements Action {
  protected String query;
  protected Object result;

  public RESULT(String query) {
    this.query = query;
  }

  public Object KTHXBYE() {
    return result;
  }
}

class UNIQUE_RESULT extends RESULT {
  protected ArrayList<Pair<String, String>> parameters = new ArrayList<>();

  public UNIQUE_RESULT(String query) {
    super(query);
  }

  @Override
  public void run(Session session) {
    Query query = session.getNamedQuery(this.query);
    for (Pair<String, String> parameter : parameters) {
      query.setString(parameter.getKey(), parameter.getValue());
    }
    result = query.uniqueResult();
  }
  public VARZ IM_IN_YR_DECLARATION() {
    return new VARZ();
  }

  public class VARZ {
    public VARZ AND(String parameter, String value) {
      parameters.add(new Pair<>(parameter, value));
      return this;
    }

    public UNIQUE_RESULT IM_OUTTA_YR_DECLARATION() {
      return UNIQUE_RESULT.this;
    }
  }
}

class LIST_RESULT extends RESULT {
  List result;

  public LIST_RESULT(String query) {
    super(query);
  }

  @Override
  public void run(Session session) {
    result = session.getNamedQuery(query).list();
  }

  @Override
  public List KTHXBYE() {
    return result;
  }
}

class Updater implements Action {

  private Object entity;

  Updater(Object entity) {
    this.entity = entity;
  }

  @Override
  public void run(Session session) {
    session.update(entity);
  }
}