package game.server.controller;

import game.entity.User;
import game.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserController {
    private String loggedUser;
    private String userPassword;
    private Date date = new Date();
    private boolean registration;
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }
    @RequestMapping("/regist")
    public String regist() {
        registration = !registration;
        return "index";
    }

    public boolean isRegistration() {
        return registration;
    }

    @RequestMapping("/login")
    public String login(String login, String password) {
        if(userService.correctPassword(login,password)){
            loggedUser = login;
            return "redirect:/blockpuzzle";
        }else{
            return "redirect:/";
        }
    }

    @RequestMapping("/registration")
    public String registration(String login, String password) {
        if(login != null && password != null){
            userService.addLoginData(new User(login, password,date));
            loggedUser = login;
            registration = !registration;
            return "redirect:/blockpuzzle";
        }else{
            return "redirect:/";
        }
    }


    @RequestMapping("/logout")
    public String logout() {
        loggedUser = null;
        return "redirect:/";
    }

    public String getLoggedUser() {
        return loggedUser;
    }

    public boolean isLogged() {
        return loggedUser != null;
    }
}

