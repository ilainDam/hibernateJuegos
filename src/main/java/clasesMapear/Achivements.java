package clasesMapear;

import jakarta.persistence.*;
@Entity
@Table(name = "achievements")
public class Achivements {
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Games juego;

    public Achivements() {
    }

    public Achivements(Integer id, String name, String description, Games juego) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.juego = juego;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Games getJuego() {
        return juego;
    }

    public void setJuego(Games juego) {
        this.juego = juego;
    }
}
