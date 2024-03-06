package com.bankingmanagement.service;

import com.bankingmanagement.entity.Product;
import com.bankingmanagement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public Product addNewProduct(Product product){
        Product product1 =  productRepository.save(product);
        return product1;
    }

    public List<Product> getAllProducts(){
      //  List<Product> products = productRepository.findAll();

        List<Product> products =IntStream.rangeClosed(1,100)
                .mapToObj(i ->{
                    Product product = new Product(i,"Name"+i,22d);
                    return product;

                }).collect(Collectors.toList());

        return products;
    }

    public Product findProductById(int id) throws Exception {
      Optional<Product> productOpt = productRepository.findById(id);
      if(productOpt.isPresent())
            return productOpt.get();
      else throw new Exception();
    }
}
