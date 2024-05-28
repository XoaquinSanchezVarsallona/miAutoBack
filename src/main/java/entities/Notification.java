package entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
public class Notification {



    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long notificationId;

    @Column(nullable = false)
    private long storeId;

    @Column(nullable = false)
    private long userId;

    @Column(nullable = false)
    private Timestamp creationDate = new Timestamp(System.currentTimeMillis());

    @Column(length = 500)
    private String description;

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // getters and setters
}