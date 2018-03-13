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
            preparedStatement = conn.prepareStatement("UPDATE games WHERE id='?' SET wins='?', ties='?', loses='?'");
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, user.getWins());
            preparedStatement.setInt(3, user.getTies());
            preparedStatement.setInt(4, user.getLoses());

            boolean result = preparedStatement.execute();

            if(result)
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
        int id = 0, wins, loses, ties;
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
            }

            int[] results = getResults(uid);

            if(results != null){
                wins = results[0];
                ties = results[1];
                loses = results[2];
                return new User(id, firstname, lastname, username, password, wins, ties, loses);
            }
            return new User(id, firstname, lastname, username, password);

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
