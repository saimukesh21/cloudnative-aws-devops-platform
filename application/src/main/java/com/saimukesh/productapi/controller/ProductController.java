
package com.saimukesh.productapi.controller;

import com.saimukesh.productapi.model.Product;
import com.saimukesh.productapi.repository.ProductRepository;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Product> getProducts() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return findProductById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@Valid @RequestBody Product product) {
        return repository.save(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody Product updatedProduct) {

        Product existingProduct = findProductById(id);

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setPrice(updatedProduct.getPrice());

        return repository.save(existingProduct);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {

        Product product = findProductById(id);
        repository.delete(product);
    }

    private Product findProductById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Product not found with ID: " + id
                ));
    }
}
