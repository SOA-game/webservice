package model;

import javax.persistence.*;

/**
 * Created by Marek on 2015-05-28.
 */
@Entity
@Table(name = "kategoria", schema = "public", catalog = "index")
public class KategoriaEntity {
    private Integer id;
    private Integer wartosc;

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
    @Column(name = "wartosc")
    public Integer getWartosc() {
        return wartosc;
    }

    public void setWartosc(Integer wartosc) {
        this.wartosc = wartosc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KategoriaEntity that = (KategoriaEntity) o;

        if (id != that.id) return false;
        if (wartosc != that.wartosc) return false;

        return true;
    }

    @Override
    public int hashCode() {
        Integer result = id;
        result = 31 * result + wartosc;
        return result;
    }
}
