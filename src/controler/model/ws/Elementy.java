package controler.model.ws;

import model.ElementyEntity;

/**
 * Created by Marek on 2015-06-01.
 *
 */
public class Elementy {

    private String atrybut1;
    private String atrybut2;
    private int id;
    private String nazwa;

    public Elementy() {}

    public Elementy(ElementyEntity typ) {
        atrybut1 = typ.getAtrybut1();
        atrybut2 = typ.getAtrybut2();
        id = typ.getId();
        nazwa = typ.getNazwa();
    }

    public ElementyEntity toEntity() {
        ElementyEntity elementyEntity = new ElementyEntity();
        elementyEntity.setAtrybut1(atrybut1);
        elementyEntity.setAtrybut2(atrybut2);
        elementyEntity.setId(id);
        elementyEntity.setNazwa(nazwa);
        return elementyEntity;
    }

    public String getAtrybut1() {
        return atrybut1;
    }

    public void setAtrybut1(String atrybut1) {
        this.atrybut1 = atrybut1;
    }

    public String getAtrybut2() {
        return atrybut2;
    }

    public void setAtrybut2(String atrybut2) {
        this.atrybut2 = atrybut2;
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
