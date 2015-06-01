package controler.model.ws;

import controlers.model.IndexManager;
import model.ElementEntity;

/**
 * Created by Marek on 2015-06-01.
 *
 */
public class Element implements Wrapper {

    private int id;
    private int parametr1;
    private String parametr2;
    private int kategoriaId;
    private Elementy typ;

    public Element() {}

    public Element(ElementEntity elementEntity) {
        id = elementEntity.getId();
        kategoriaId = elementEntity.getKategoria().getId();
        parametr1 = elementEntity.getParametr1();
        parametr2 = elementEntity.getParametr2();
        typ = new Elementy(elementEntity.getTyp());
    }

    @Override
    public ElementEntity toEntity() {
        ElementEntity elementEntity = new ElementEntity();
        elementEntity.setId(id);
        elementEntity.setKategoria(new IndexManager().getCategoryEntity(kategoriaId));
        elementEntity.setParametr1(parametr1);
        elementEntity.setParametr2(parametr2);
        elementEntity.setTyp(typ.toEntity());
        return elementEntity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParametr1() {
        return parametr1;
    }

    public void setParametr1(int parametr1) {
        this.parametr1 = parametr1;
    }

    public String getParametr2() {
        return parametr2;
    }

    public void setParametr2(String parametr2) {
        this.parametr2 = parametr2;
    }

    public Kategoria getKategoriaId() {
        return kategoriaId;
    }

    public void setKategoriaId(Kategoria kategoriaId) {
        this.kategoriaId = kategoriaId;
    }

    public Elementy getTyp() {
        return typ;
    }

    public void setTyp(Elementy typ) {
        this.typ = typ;
    }
}
