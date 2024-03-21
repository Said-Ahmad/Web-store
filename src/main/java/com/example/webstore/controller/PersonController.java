package com.example.webstore.controller;

import com.example.webstore.models.Login;
import com.example.webstore.models.User;
import com.example.webstore.sevices.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;

@Controller
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    @GetMapping("/reg")
    public String newPerson(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }
    @GetMapping("/login")
    public String login(@ModelAttribute("user") User user) {
        return "login";
    }


    @PostMapping("/new-user")
    public String create(@ModelAttribute("user") User user) {
        if (user.getName().equals("")||user.getSurname().equals("")||user.getEmail().equals("")||user.getPassword().equals("")){
            System.out.println("Поля пользователя не заполнены!");
            return "registration";
        }
        Login login = new Login();
        login.setEmail(user.getEmail());
        login.setPassword(user.getPassword());
        User verifiedUser = personService.checkLogin(login);
        if (Objects.isNull(verifiedUser)){
            personService.save(user);

            System.out.println("Пользователь успешно сохранён!");
            return "login";
        }else{
            System.out.println("Пользователь уже существует!");
            return "registration";
        }
    }

    @GetMapping("/check-login")
    public String login(@ModelAttribute("user") User user,Model model){
        Login login = new Login();
        login.setEmail(user.getEmail());
        login.setPassword(user.getPassword());
        user = personService.checkLogin(login);
            if ( Objects.isNull(user)){
                System.out.println("Не верный логин!");
                return "login";
            }else{
                model.addAttribute("user",user);
                System.out.println("Пользователь успешно верифицирован!");
                return "home";
            }
    }



        @GetMapping("/user-profile/{id}")
            public String showProfile (@PathVariable(value = "id") int id, Model model){
            User user = personService.getPersonById(id);
            model.addAttribute("user", user);
            return "profile";
        }
    }
