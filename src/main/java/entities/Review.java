package entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long reviewID;

    @Column(nullable = false)
    private long userID;

    @Column(nullable = false)
    private long storeID;

    @Column(nullable = false)
    private int rating;

    @Column(length = 500)
    private String comment;

    @Column(nullable = false)
    private Timestamp creationDate = new Timestamp(System.currentTimeMillis());

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public void setStoreID(long storeID) {
        this.storeID = storeID;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    public int getRating () { return this.rating; }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
