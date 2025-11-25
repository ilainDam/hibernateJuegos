package clasesMapear;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "games")
public class Games {
    @Id
    @Column(name = "id")
    Integer id;
    @Column(name = "slug")
    String slug;
    @Column(name = "name")
    String name;
    @Column(name = "released_date")
    Date relesaded_date;
    @Column(name = "rating")
    Double rating;
    @ManyToMany(mappedBy = "games")
    private List<Genres> genresList=new ArrayList<>();
    @OneToMany(mappedBy = "games")
    private  List<Archivements> archivementsList = new ArrayList<>();

}
