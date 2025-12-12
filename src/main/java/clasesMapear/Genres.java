package clasesMapear;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "genres")
public class Genres {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @ManyToMany()
    @JoinTable(
            name = "gamegenres",
            joinColumns = @JoinColumn(name = "genre_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    private List<Games> juegos;

    public Genres() {
    }

    public Genres(Integer id, String name, List<Games> juegos) {
        this.id = id;
        this.name = name;
        this.juegos = juegos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Games> getJuegos() {
        return juegos;
    }

    public void setJuegos(List<Games> juegos) {
        this.juegos = juegos;
    }
}
