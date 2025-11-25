package clasesMapear;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "genres")
public class Genres {
    @Id
    @Column(name = "id")
    Integer id;
    @Column(name = "name")
    String name;
    @ManyToMany(mappedBy = "genres")
    @JoinTable(name = "gamesgenres",
            joinColumns = @JoinColumn(name = "genres_id"),
            inverseJoinColumns = @JoinColumn(name = "raza_id")
    )
    private List<Games> gamesList = new ArrayList<>();
}
