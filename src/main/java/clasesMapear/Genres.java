package clasesMapear;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "genres")
public class Genres {
    @Id
    @Column(name = "id")
    Integer id;
    @Column(name = "name")
    String name;
}
