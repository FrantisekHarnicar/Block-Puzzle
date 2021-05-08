package game.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "player")
public class User {

    @Id
    private String login;

    private String password;

    private Date createdOn;
    public User() {
    }

    public User(String login, String password, Date createdOn) {
        this.login = login;
        this.password = password;
        this.createdOn = createdOn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "player{" +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", createdOn=" + createdOn +
                '}';
    }

}
