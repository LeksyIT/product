package com.leksyit.task14vtb.service.impl;


import com.leksyit.task14vtb.entity.Product;
import com.leksyit.task14vtb.repository.ProductRepository;
import com.leksyit.task14vtb.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getById(Long id) {
        return productRepository.getById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void add(Product product) {
        productRepository.save(product);
    }

    public List<Product> getAllProductWithFilter(String word) {
        List<Product> fullList = productRepository.findAll();
        if (word == null) {
            return fullList;
        }
        return fullList.stream().filter(p -> p.getTitle().contains(word)).toList();
    }

    @Override
    public void deleteProduct(@PathVariable Long id) {
        productRepository.delete(productRepository.findById(id).orElseThrow());
    }
}
