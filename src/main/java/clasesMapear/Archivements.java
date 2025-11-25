package clasesMapear;

import jakarta.persistence.*;

@Table(name = "archivements")
public class Archivements {
    @Id
    @Column(name = "id")
    Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "games_id")
    private Games game;
    @Column(name = "name")
    String name;
    @Column(name = "descripcion")
    String description;

}
