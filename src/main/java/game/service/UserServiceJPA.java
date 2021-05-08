package game.service;

import game.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class UserServiceJPA implements UserService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addLoginData(User user) throws UserException {
        entityManager.persist(user);
    }

    @Override
    public boolean correctPassword(String login, String password) throws UserException {
        if(((Number) entityManager.createQuery("SELECT count(login) from User WHERE login =: login and password =: password")
                .setParameter("login", login)
                .setParameter("password", password)
                .getSingleResult()).intValue() > 0){
            return true;
        }
        return false;
    }
}
