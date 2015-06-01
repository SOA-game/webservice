package model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Created by Marek on 2015-05-30.
 *
 */
@Entity
@Table(name = "element", schema = "public", catalog = "index")
public class ElementEntity {
    private int id;
    private ElementyEntity typ;
    private int parametr1;
    private String parametr2;
    private KategoriaEntity kategoria;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false, targetEntity = KategoriaEntity.class)
    @JoinColumn(name = "kategoria", nullable = false)
    public KategoriaEntity getKategoria() {
        return kategoria;
    }

    public void setKategoria(KategoriaEntity kategoria) {
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
        if (kategoria != that.kategoria) return false;
        if (parametr2 != null ? !parametr2.equals(that.parametr2) : that.parametr2 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + typ.hashCode();
        result = 31 * result + parametr1;
        result = 31 * result + (parametr2 != null ? parametr2.hashCode() : 0);
        result = 31 * result + kategoria.hashCode();
        return result;
    }
}
