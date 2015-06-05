package model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Objects;

/**
 * Created by Marek on 2015-05-30.
 *
 */
@NamedQueries({
        @NamedQuery(
                name = "CAN HAS ELEMENT?",
                query = "from ElementEntity where id = :IZ_ID"
        )
})
@Entity
@Table(name = "element", schema = "public", catalog = "index")
public class ElementEntity {
    private int id;
    private ElementyEntity typ;
    private int parametr1;
    private String parametr2;
    private Integer kategoria;
    private KategoriaEntity kategoriaEntity;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "typ", nullable = false)
    public ElementyEntity getTyp() {
        return typ;
    }

    public void setTyp(ElementyEntity typ) {
        this.typ = typ;
    }

    @Basic
    @Column(name = "parametr1")
    public int getParametr1() {
        return parametr1;
    }

    public void setParametr1(int parametr1) {
        this.parametr1 = parametr1;
    }

    @Basic
    @Column(name = "parametr2")
    public String getParametr2() {
        return parametr2;
    }

    public void setParametr2(String parametr2) {
        this.parametr2 = parametr2;
    }

    //    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false, targetEntity = KategoriaEntity.class)
//    cannot have KategoriaEntity property because of XML marschalling
    @JoinColumn(name = "kategoria", nullable = false)
    public Integer getKategoria() {
        return kategoria;
    }

    public void setKategoria(Integer kategoria) {
        this.kategoria = kategoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ElementEntity that = (ElementEntity) o;

        if (id != that.id) return false;
        if (typ != that.typ) return false;
        if (parametr1 != that.parametr1) return false;
        if (!Objects.equals(kategoria, that.kategoria)) return false;
        if (parametr2 != null ? !parametr2.equals(that.parametr2) : that.parametr2 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + typ.hashCode();
        result = 31 * result + parametr1;
        result = 31 * result + (parametr2 != null ? parametr2.hashCode() : 0);
        result = 31 * result + kategoria;
        return result;
    }
}
