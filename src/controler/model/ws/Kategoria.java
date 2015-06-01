package controler.model.ws;

import controlers.model.IndexManager;
import model.ElementEntity;
import model.KategoriaEntity;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Marek on 2015-06-01.
 *
 */
public class Kategoria implements Wrapper {
    private int wartosc;
    private Kategorie typ;
    private Collection<Element> elementy = new ArrayList<>();
    private int id;

    public Kategoria() {}

    public Kategoria(KategoriaEntity entity) {
        for (ElementEntity elementEntity : entity.getElementy()) {
            elementy.add(new Element(elementEntity));
        }
        id = entity.getId();
        typ = new Kategorie(entity.getTyp());
        wartosc = entity.getWartosc();
    }

    @Override
    public KategoriaEntity toEntity() {
        KategoriaEntity entity = new KategoriaEntity();
        ArrayList<ElementEntity> kategorieEntities = new ArrayList<>();
        for (Element kategorie : elementy) {
            kategorieEntities.add(kategorie.toEntity());
        }
        entity.setElementy(kategorieEntities);
        entity.setId(id);
        entity.setTyp(typ.toEntity());
        entity.setWartosc(wartosc);
        return entity;
    }

    public int getWartosc() {
        return wartosc;
    }

    public void setWartosc(int wartosc) {
        this.wartosc = wartosc;
    }

    public Kategorie getTyp() {
        return typ;
    }

    public void setTyp(Kategorie typ) {
        this.typ = typ;
    }

    public Collection<Element> getElementy() {
        return elementy;
    }

    public void setElementy(Collection<Element> elementy) {
        this.elementy = elementy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
