package com.example.webstore.sevices;

import com.example.webstore.models.Login;
import com.example.webstore.models.Product;
import com.example.webstore.models.User;
import com.example.webstore.repository.PersonRepository;
import com.example.webstore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    private final ProductRepository productRepository;
    private final PersonRepository personRepository;
    @Autowired
    public PersonService(ProductRepository productRepository, PersonRepository personRepository) {
        this.productRepository = productRepository;
        this.personRepository = personRepository;
    }

    public User checkLogin(Login login) {
        return personRepository.checkLogin(login);
    }
    public void save(User user){
        personRepository.savePerson(user);
    }

    public User getPersonById(int id) {
        return personRepository.getPersonById(id);
    }

    public List<Product> getProducts() {
        return productRepository.getProducts();
    }
}
