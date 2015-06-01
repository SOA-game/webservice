package controlers.model;

import controler.model.ws.Element;
import controler.model.ws.Elementy;
import controler.model.ws.Kategoria;
import controler.model.ws.Kategorie;
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
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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
  public void addCategory(final Kategoria typ) {
    perform(new Saver(typ.toEntity()));
  }

  @WebMethod
  public void addElement(final Element typ) {
    perform(new Saver(typ.toEntity()));
  }

  @WebMethod
  public List<Kategorie> getCategoryTypes() {
    Getter<Kategorie, KategorieEntity> entity = new Getter<>("KategorieEntity", Kategorie.class, KategorieEntity.class);
    perform(entity);

    return entity.getList();
  }

  @WebMethod
  public List<Elementy> getElementTypes() {
    Getter<Elementy, ElementyEntity> entity = new Getter<>("ElementyEntity", Elementy.class, ElementyEntity.class);
    perform(entity);

    return entity.getList();
  }

  @WebMethod
  public List<Kategoria> getCategories() {
    Getter<Kategoria, KategoriaEntity> entity = new Getter<>("KategoriaEntity", Kategoria.class, KategoriaEntity.class);
    perform(entity);

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

class Getter<Ret, Entity> implements Action {
  private List<Ret> list;

  public Getter(String entity, Class<Ret> retClass, Class<Entity> entityClass) {
    this.entity = entity;
    this.retClass = retClass;
    this.entityClass = entityClass;
  }

  private String entity;
  private Class<Ret> retClass;
  private Class<Entity> entityClass;

  @Override
  public void run(Session session) {
    List ret = session.createQuery("from " + entity).list();

    list = new ArrayList<>();
    for (Object o : ret) {
      try {
        list.add(retClass.getConstructor(entityClass).newInstance(o));
      } catch (InstantiationException e) {
        e.printStackTrace();
        System.out.println("ie");
      } catch (IllegalAccessException e) {
        System.out.println("iae");
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        System.out.println("ite");
        e.printStackTrace();
      } catch (NoSuchMethodException e) {
        System.out.println("nsme");
        e.printStackTrace();
      }
    }
  }

  public List<Ret> getList() {
    return list;
  }
}