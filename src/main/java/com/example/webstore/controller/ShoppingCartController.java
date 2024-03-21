package com.example.webstore.controller;

import com.example.webstore.models.Product;
import com.example.webstore.models.User;
import com.example.webstore.sevices.PersonService;
import com.example.webstore.sevices.ShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final PersonService personService;

    public ShoppingCartController(ShoppingCartService shoppingCartService, PersonService personService) {
        this.shoppingCartService = shoppingCartService;
        this.personService = personService;
    }
    @PostMapping("/add-to-cart/{id}/{id2}")
    public String addToCart(@PathVariable(value = "id") int person_id, @PathVariable(value = "id2") int product_id, Model model) {

        //Сохранение
        shoppingCartService.addProductIdForPerson(person_id, product_id);


        //Прод
        List<Product> products = personService.getProducts();
        //User
        User user = personService.getPersonById(person_id);
        model.addAttribute("user", user);
        model.addAttribute("listOfProducts", products);
        return "products";
    }

    @GetMapping("/show-shopping-cart/{id}")
    public String showShoppingCart(@PathVariable(value = "id") int person_id, Model model) {
        List<Product> products = shoppingCartService.getProductsFromShoppingCart(person_id);
        System.out.println("From shopping cart:!!!" + products);
        User user = personService.getPersonById(person_id);
        model.addAttribute("listOfProducts", products);
        model.addAttribute("user", user);
        return "shopping-cart";
    }

    @DeleteMapping("/delete-product/{person_id}/{product_id}")
    public String deleteProduct(@PathVariable(value = "person_id") int person_id, @PathVariable(value = "product_id") int product_id, Model model) {
        shoppingCartService.deleteProduct(product_id, person_id);
        List<Product> products = shoppingCartService.getProductsFromShoppingCart(person_id);
        model.addAttribute("listOfProducts", products);
        User user = personService.getPersonById(person_id);
        model.addAttribute("user", user);
        return "shopping-cart";
    }

    @GetMapping("/show-products/{id}")
    public String getProducts(@PathVariable(value = "id") int id, Model model) {
        System.out.println("!!!!");
        List<Product> products = personService.getProducts();
        model.addAttribute("listOfProducts", products);
        User user = personService.getPersonById(id);
        model.addAttribute("user", user);
        return "products";

    }

}









