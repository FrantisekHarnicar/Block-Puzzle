/*package game.service;

import game.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class UserServiceRestClient implements UserService {

    @Value("${remote.server.api}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public void addLoginData(User user) throws UserException {
        restTemplate.postForEntity(url + "/player", user, User.class);
    }

    @Override
    public List<String> correctPassword(String login) throws UserException {
        return Arrays.asList(restTemplate.getForEntity(url + "/player/"+login, String[].class).getBody());
    }
}*/
