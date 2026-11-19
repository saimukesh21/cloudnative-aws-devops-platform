
package com.saimukesh.productapi;

import com.saimukesh.productapi.controller.ProductController;
import com.saimukesh.productapi.model.Product;
import com.saimukesh.productapi.repository.ProductRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductRepository repository;

    @Test
    void createProductReturnsCreated() throws Exception {

        when(repository.save(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Laptop\",\"price\":55000}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.price").value(55000.0));
    }

    @Test
    void getAllProductsReturnsProducts() throws Exception {

        when(repository.findAll())
                .thenReturn(List.of(new Product("Laptop", 55000.0)));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Laptop"));
    }

    @Test
    void getProductByIdReturnsProduct() throws Exception {

        when(repository.findById(1L))
                .thenReturn(Optional.of(new Product("Laptop", 55000.0)));

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"));
    }

    @Test
    void updateProductReturnsUpdatedProduct() throws Exception {

        Product existing = new Product("Laptop", 55000.0);

        when(repository.findById(1L))
                .thenReturn(Optional.of(existing));

        when(repository.save(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(put("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Gaming Laptop\",\"price\":75000}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gaming Laptop"))
                .andExpect(jsonPath("$.price").value(75000.0));
    }

    @Test
    void deleteProductReturnsNoContent() throws Exception {

        Product existing = new Product("Laptop", 55000.0);

        when(repository.findById(1L))
                .thenReturn(Optional.of(existing));

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());

        verify(repository).delete(existing);
    }

    @Test
    void missingProductReturnsNotFound() throws Exception {

        when(repository.findById(999L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/products/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void negativePriceReturnsBadRequest() throws Exception {

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Invalid Product\",\"price\":-100}"))
                .andExpect(status().isBadRequest());

        verify(repository, never()).save(any(Product.class));
    }
}
