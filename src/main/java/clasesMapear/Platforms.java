package clasesMapear;

import jakarta.persistence.*;
import jdk.jfr.Name;

import java.util.List;
@Entity
@Table(name = "platforms")
public class Platforms {
    @Id
    @Column(name = "id")
    Integer id;
    @Column(name = "name")
    String name;
    @ManyToMany()
    @JoinTable(
            name = "gameplatforms",
            joinColumns=@JoinColumn(name = "plaform_id"),
            inverseJoinColumns =@JoinColumn(name = "game_id")
    )
    private List<Games> juegos;

    public Platforms() {
    }

    public Platforms(Integer id, String name, List<Games> juegos) {
        this.id = id;
        this.name = name;
        this.juegos = juegos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Games> getJuegos() {
        return juegos;
    }

    public void setJuegos(List<Games> juegos) {
        this.juegos = juegos;
    }
}
