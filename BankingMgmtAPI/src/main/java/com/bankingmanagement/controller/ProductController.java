package com.bankingmanagement.controller;


import com.bankingmanagement.entity.Product;
import com.bankingmanagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAllProducts();

    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable int id){
        Product product;
        try {
           product = productService.findProductById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return product;

    }

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String getProductById(@RequestBody Product product){
        Product newproduct;
            productService.addNewProduct(product);
      return "New Product Added";

    }


}
