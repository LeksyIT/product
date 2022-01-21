package com.leksyit.task14vtb.service;

import com.leksyit.task14vtb.entity.Product;
import org.springframework.web.bind.annotation.PathVariable;

public interface ProductService{

    void deleteProduct(@PathVariable Long id);

    Product getProductById(Long id);

    Product updateProduct(Product student);
}
