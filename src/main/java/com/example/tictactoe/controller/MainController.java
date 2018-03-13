package com.example.tictactoe.controller;

import com.example.tictactoe.models.User;
import com.example.tictactoe.repositories.IUser;
import com.example.tictactoe.repositories.IUserDB;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    boolean userLogged = false;
    IUser userDB = new IUserDB();
    User user;

    @GetMapping("/")
    public String index(){
        if(userLogged)
            return "userIndex";
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
        if(userLogged)
            return "userIndex";
        return "register";
    }

    @PostMapping("/register")
    public String verifyRegister(){
        if(userLogged)
            return "userIndex";
        return "register";
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

    @ResponseBody
    @PostMapping("/something")
    public String returnAjax(){
        // response for ajax request
        user = userDB.readAll(1);
        return user.getFirstname();
    }
}
