package com.github.kolizey72.onlineshop.service;

import com.github.kolizey72.onlineshop.entity.Product;
import com.github.kolizey72.onlineshop.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(long id) {
        return productRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public void create(Product product) {
        productRepository.save(product);
    }

    public void update(long id, Product updatedProduct) {
        productRepository.findById(id).ifPresent(productToUpdate -> {
            productToUpdate.setName(updatedProduct.getName());
            productToUpdate.setPrice(updatedProduct.getPrice());
            productToUpdate.setUserClass(updatedProduct.getUserClass());
            productToUpdate.setUserProfession(updatedProduct.getUserProfession());
        });
    }

    public void delete(long id) {
        productRepository.deleteById(id);
    }
}
