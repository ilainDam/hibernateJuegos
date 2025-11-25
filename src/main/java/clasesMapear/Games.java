package clasesMapear;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

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
    @OneToMany(mappedBy = "games")
    private  List<Archivements> archivementsList = new ArrayList<>() {
    };

}
