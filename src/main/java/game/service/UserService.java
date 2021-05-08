package game.service;

import game.entity.User;

import java.util.List;

public interface UserService {
    void addLoginData(User user) throws UserException;
    boolean correctPassword(String login, String password) throws UserException;
}
