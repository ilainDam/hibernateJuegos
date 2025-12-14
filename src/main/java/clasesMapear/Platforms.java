package clasesMapear;

import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "platforms")
public class Platforms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "gameplatforms",
            joinColumns=@JoinColumn(name = "platform_id"),
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
