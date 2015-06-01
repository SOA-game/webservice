package model;

import javax.persistence.*;

/**
 * Created by Marek on 2015-05-30.
 *
 */
@Entity
@Table(name = "elementy", schema = "public", catalog = "index")
public class ElementyEntity {
    private int id;
    private String nazwa;
    private String atrybut1;
    private String atrybut2;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "nazwa")
    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    @Basic
    @Column(name = "atrybut1")
    public String getAtrybut1() {
        return atrybut1;
    }

    public void setAtrybut1(String atrybut1) {
        this.atrybut1 = atrybut1;
    }

    @Basic
    @Column(name = "atrybut2")
    public String getAtrybut2() {
        return atrybut2;
    }

    public void setAtrybut2(String atrybut2) {
        this.atrybut2 = atrybut2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ElementyEntity that = (ElementyEntity) o;

        if (id != that.id) return false;
        if (nazwa != null ? !nazwa.equals(that.nazwa) : that.nazwa != null) return false;
        if (atrybut1 != null ? !atrybut1.equals(that.atrybut1) : that.atrybut1 != null) return false;
        if (atrybut2 != null ? !atrybut2.equals(that.atrybut2) : that.atrybut2 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nazwa != null ? nazwa.hashCode() : 0);
        result = 31 * result + (atrybut1 != null ? atrybut1.hashCode() : 0);
        result = 31 * result + (atrybut2 != null ? atrybut2.hashCode() : 0);
        return result;
    }
}
