package clasesMapear;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "platforms")
public class Platforms {
    @Id
    @Column(name = "id")
    Integer id;
    @Column(name = "name")
    String name;
}
