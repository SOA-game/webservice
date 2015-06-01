package controler.model.ws;

import model.KategorieEntity;

/**
 * Created by Marek on 2015-06-01.
 *
 */
public class Kategorie implements Wrapper {

    private String atrybut;
    private int id;
    private String nazwa;

    public Kategorie() {}

    public Kategorie(KategorieEntity typ) {
        atrybut = typ.getAtrybut();
        id = typ.getId();
        nazwa = typ.getNazwa();
    }

    @Override
    public KategorieEntity toEntity() {
        KategorieEntity entity = new KategorieEntity();
        entity.setAtrybut(atrybut);
        entity.setId(id);
        entity.setNazwa(nazwa);
        return entity;
    }

    public String getAtrybut() {
        return atrybut;
    }

    public void setAtrybut(String atrybut) {
        this.atrybut = atrybut;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
}
