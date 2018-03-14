package com.example.tictactoe.repositories;

import com.example.tictactoe.models.User;
import com.example.tictactoe.repositories.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IUserDB implements IUser {

    private Connection conn;
    private PreparedStatement preparedStatement;
    private ResultSet result;

    public IUserDB(){
        try{
            this.conn = Database.getConnection();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void create(User user) {
        try{
            preparedStatement = conn.prepareStatement("INSERT INTO users(firstname, lastname, username, password) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, user.getFirstname());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getPassword());
            boolean result = preparedStatement.execute();

            if(result)
                System.out.println("New user created");
            else
                System.out.println("Error creating users");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        try{
            preparedStatement = conn.prepareStatement("UPDATE users SET wins='" + user.getWins() + "', ties='" + user.getTies() +
                    "', loses='" + user.getLoses() + "' WHERE id='" + user.getId() + "';");

            boolean result = preparedStatement.execute();

            if(!result)
                System.out.println("User update");
            else
                System.out.println("User update error");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public User read(int id) {
        try{
            preparedStatement = conn.prepareStatement("SELECT * FROM users WHERE id='"+ id + "' LIMIT 1");
            result = preparedStatement.executeQuery();

            if(result.next()){
                return new User(result.getInt("id"),
                        result.getString("firstname"),
                        result.getString("lastname"),
                        result.getString("username"),
                        result.getString("password"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User readAll(int uid){
        int id = 0, wins = 0, loses = 0, ties = 0;
        String firstname = "", lastname = "", username = "", password = "";
        try{
            preparedStatement = conn.prepareStatement("SELECT * FROM users WHERE id='"+ uid + "' LIMIT 1");
            result = preparedStatement.executeQuery();

            if(result.next()){
                id = result.getInt("id");
                firstname = result.getString("firstname");
                lastname = result.getString("lastname");
                username = result.getString("username");
                password = result.getString("password");
                wins = result.getInt("wins");
                ties = result.getInt("ties");
                loses = result.getInt("loses");
            }

            return new User(id, firstname, lastname, username, password, wins, ties, loses);

        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public int[] getResults(int id){
        try{
            int[] results = new int[3];
            preparedStatement = conn.prepareStatement("SELECT * FROM games WHERE userid='" + id +"' LIMIT 1");
            result = preparedStatement.executeQuery();

            if(result.next()){
                results[0] = result.getInt("wins");
                results[1] = result.getInt("ties");
                results[2] = result.getInt("loses");
            }

            return results;
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int verifyUser(String username, String password){
        try{
            preparedStatement = conn.prepareStatement("SELECT * FROM users WHERE username='" + username + "' AND password='" + password + "' LIMIT 1");
            result = preparedStatement.executeQuery();

            if(result.next()){
                return result.getInt("id");
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean userExists(String username){
        try{
            preparedStatement = conn.prepareStatement("SELECT id FROM users WHERE username='" + username + "' LIMIT 1");
            result = preparedStatement.executeQuery();

            if(result.next())
                return true;
        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
