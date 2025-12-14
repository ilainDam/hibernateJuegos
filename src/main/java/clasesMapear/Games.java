package clasesMapear;

import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "games")
public class Games {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "slug")
    private String slug;
    @Column(name = "name")
    private String name;
    @Column(name = "released_date")
    private String releasedDate;
    @Column(name = "rating")
    private Double rating;
    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY, mappedBy = "juego")
    private List<Achievements> achievements;
    @ManyToMany(mappedBy = "juegos")
    private List<Platforms> plataformas;
    @ManyToMany(mappedBy = "juegos")
    private List<Genres> generos;

    public Games() {
    }

    public Games(Integer id, String slug, String name, String releasedDate, Double rating, List<Achievements> archivements, List<Platforms> plataformas, List<Genres> generos) {
        this.id = id;
        this.slug = slug;
        this.name = name;
        this.releasedDate = releasedDate;
        this.rating = rating;
        this.achievements = archivements;
        this.plataformas = plataformas;
        this.generos = generos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(String releasedDate) {
        this.releasedDate = releasedDate;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public List<Achievements> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<Achievements> achievements) {
        this.achievements = achievements;
    }

    public List<Platforms> getPlataformas() {
        return plataformas;
    }

    public void setPlataformas(List<Platforms> plataformas) {
        this.plataformas = plataformas;
    }

    public List<Genres> getGeneros() {
        return generos;
    }

    public void setGeneros(List<Genres> generos) {
        this.generos = generos;
    }
}
