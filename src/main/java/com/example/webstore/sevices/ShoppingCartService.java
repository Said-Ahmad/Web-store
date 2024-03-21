package com.example.webstore.sevices;

import com.example.webstore.models.Product;
import com.example.webstore.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartService {
    private final ProductRepository productRepository;

    public ShoppingCartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProductIdForPerson(int person_id, int product_id) {
        productRepository.addProductIdForPerson(person_id,product_id);
    }

    public List<Product> getProductsFromShoppingCart(int person_id) {
        return productRepository.getProductsFromShoppingCart(person_id);
    }

    public void deleteProduct(int product_id, int person_id) {
        productRepository.deleteProduct(product_id,person_id);
    }
}
