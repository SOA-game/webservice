package model;

import javax.persistence.*;

/**
 * Created by Marek on 2015-05-28.
 *
 */
@Entity
@Table(name = "element", schema = "public", catalog = "index")
public class ElementEntity {
    private Integer id;
    private Integer parametr1;
    private Integer parametr2;

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
    @Column(name = "parametr1")
    public Integer getParametr1() {
        return parametr1;
    }

    public void setParametr1(Integer parametr1) {
        this.parametr1 = parametr1;
    }

    @Basic
    @Column(name = "parametr2")
    public Integer getParametr2() {
        return parametr2;
    }

    public void setParametr2(Integer parametr2) {
        this.parametr2 = parametr2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ElementEntity that = (ElementEntity) o;

        if (id != that.id) return false;
        if (parametr1 != that.parametr1) return false;
        if (parametr2 != that.parametr2) return false;

        return true;
    }

    @Override
    public int hashCode() {
        Integer result = id;
        result = 31 * result + parametr1;
        result = 31 * result + parametr2;
        return result;
    }
}
