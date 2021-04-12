package game.entity;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@IdClass(RatingId.class)
@NamedQuery( name = "Rating.getAVGRating",
        query = "SELECT AVG(s.rating) FROM Rating s WHERE s.game=:game")
@NamedQuery( name = "Rating.getRating",
        query = "SELECT rating FROM Rating WHERE game =: game AND player=: player order by rating")
public class Rating implements Serializable {
    @Id
    private String player;

    @Id
    private String game;

    private int rating;
    private Date ratedOn;

    public Rating(){}

    public Rating(String game, String player, int rating, Date ratedOn) {
        this.game = game;
        this.player = player;
        this.rating = rating;
        this.ratedOn = ratedOn;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getPlayer() {
        return player;
    }


    public int getRating() {
        return rating;
    }

    public Date getRatedOn() {
        return ratedOn;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "game='" + game + '\'' +
                ", player='" + player + '\'' +
                ", rating=" + rating +
                ", ratedOn=" + ratedOn +
                '}';
    }
}