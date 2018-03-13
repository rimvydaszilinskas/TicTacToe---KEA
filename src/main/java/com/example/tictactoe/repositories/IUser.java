package com.example.tictactoe.repositories;

import com.example.tictactoe.models.User;

public interface IUser {
    void create(User user);
    void update(User user);
    User read(int id);
    User readAll(int id);
    int[] getResults(int id);
    int verifyUser(String username, String password);
}
