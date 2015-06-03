package model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Marek on 2015-05-30.
 *
 */
@Entity
@Table(name = "kategoria", schema = "public", catalog = "index")
public class KategoriaEntity {
    private int id;
    private KategorieEntity typ;
    private int wartosc;

    private Collection<ElementEntity> elementy;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "kategoria", orphanRemoval = false)
    public Collection<ElementEntity> getElementy() {
        return elementy;
    }

    public void setElementy(Collection<ElementEntity> elementy) {
        this.elementy = elementy;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "typ", nullable = false)
    public KategorieEntity getTyp() {
        return typ;
    }

    public void setTyp(KategorieEntity typ) {
        this.typ = typ;
    }

    @Basic
    @Column(name = "wartosc")
    public int getWartosc() {
        return wartosc;
    }

    public void setWartosc(int wartosc) {
        this.wartosc = wartosc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KategoriaEntity that = (KategoriaEntity) o;

        if (id != that.id) return false;
        if (typ != that.typ) return false;
        if (wartosc != that.wartosc) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + typ.hashCode();
        result = 31 * result + wartosc;
        return result;
    }
}
