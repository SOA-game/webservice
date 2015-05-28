package model;

import javax.persistence.*;

/**
 * Created by Marek on 2015-05-28.
 */
@Entity
@Table(name = "kategorie", schema = "public", catalog = "index")
public class KategorieEntity {
    private Integer id;
    private String nazwa;
    private String atrybut;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique=true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
    @Column(name = "atrybut")
    public String getAtrybut() {
        return atrybut;
    }

    public void setAtrybut(String atrybut) {
        this.atrybut = atrybut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KategorieEntity that = (KategorieEntity) o;

        if (id != that.id) return false;
        if (nazwa != null ? !nazwa.equals(that.nazwa) : that.nazwa != null) return false;
        if (atrybut != null ? !atrybut.equals(that.atrybut) : that.atrybut != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        Integer result = id;
        result = 31 * result + (nazwa != null ? nazwa.hashCode() : 0);
        result = 31 * result + (atrybut != null ? atrybut.hashCode() : 0);
        return result;
    }
}
