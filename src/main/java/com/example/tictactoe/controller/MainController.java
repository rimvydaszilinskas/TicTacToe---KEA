package com.example.tictactoe.controller;

import com.example.tictactoe.models.User;
import com.example.tictactoe.repositories.IUser;
import com.example.tictactoe.repositories.IUserDB;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    private boolean userLogged = false;
    private IUser userDB = new IUserDB();
    private User user;

    @GetMapping("/")
    public String index(Model model){
        if(userLogged) {
            user = userDB.readAll(user.getId());
            model.addAttribute("player", user);
            return "userIndex";
        }
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        if(userLogged)
            return "userIndex";
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        if(userLogged) {
            return "userIndex";
        }
        return "register";
    }

    @GetMapping("/logout")
    public String logout(){
        userLogged = false;
        user = null;
        return "index";
    }

    @ResponseBody
    @PostMapping("/register")
    public String verifyRegister(@RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname,
                                 @RequestParam("username") String username, @RequestParam("password") String password){
        if(!userDB.userExists(username)){
            userDB.create(new User(0, firstname, lastname, username, password));
            return "OK";
        }
        return "EXISTS";
    }

    @ResponseBody
    @PostMapping("/login")
    public String verifyLogin(@RequestParam("username") String username, @RequestParam("password") String password){
        int id = userDB.verifyUser(username, password);
        if(id != 0){
            user = userDB.readAll(id);
            userLogged = true;
            return "OK";
        }
        return "404";
    }

}
