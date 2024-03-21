package com.example.webstore.repository;

import com.example.webstore.models.Login;
import com.example.webstore.models.User;
import com.example.webstore.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.DigestUtils;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import java.sql.SQLException;

@Repository
public class PersonRepository {
    private final Util utils;

    @Autowired
    public PersonRepository(Util utils) {
        this.utils = utils;
    }





    public User checkLogin(Login login) {
        Connection connection = utils.getConnection();
        try (PreparedStatement statement = connection
                .prepareStatement("select * from public.person where email = ? and password = ?")) {
            String password = DigestUtils.md5DigestAsHex(login.getPassword().getBytes());
            statement.setString(1, login.getEmail());
            statement.setString(

                    2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                User person = new User();
                System.out.println(person);
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setSurname(resultSet.getString("surname"));
                return person;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }








    public void savePerson(User user) {
        try (Connection connection = utils.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO public.person (email,password,name,surname) values (?, ?, ?, ?)")) {
            String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());

            System.out.println(password);
            statement.setString(1, user.getEmail());
            statement.setString(2, password);
            statement.setString(3, user.getName());
            statement.setString(4, user.getSurname());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getPersonById(int id) {
        Connection connection = utils.getConnection();
        try (PreparedStatement statement = connection
                .prepareStatement("select * from public.person where id = ?")) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User person = new User();
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setSurname(resultSet.getString("surname"));
                person.setEmail(resultSet.getString("email"));
                System.out.println(person);
                return person;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }


}
