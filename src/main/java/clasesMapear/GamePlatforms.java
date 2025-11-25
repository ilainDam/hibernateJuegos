package clasesMapear;

import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Table(name = "gameplatforms")
public class GamePlatforms {
    @Column(name = "game_id")
    Integer game_id;
    @Column(name = "platform_id")
    Integer platform_id;
}
