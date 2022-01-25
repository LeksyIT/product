package com.leksyit.task14vtb.service.impl;


import com.leksyit.task14vtb.dto.ProductDto;
import com.leksyit.task14vtb.entity.Product;
import com.leksyit.task14vtb.mapper.ProductMapper;
import com.leksyit.task14vtb.repository.ProductRepository;
import com.leksyit.task14vtb.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    public Product getById(Long id) {
         return productRepository.getById(id);
    }

    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Product> getAllProductWithFilter(String word) {
        List<Product> fullList = productRepository.findAll();
        if (word == null) {
            return fullList;
        }
        return fullList.stream().filter(p -> p.getTitle().contains(word)).toList();
    }

    @Override
    @Transactional
    public void deleteProduct(@PathVariable Long id) {
        productRepository.delete(productRepository.findById(id).orElseThrow());
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public void updateProduct(ProductDto productDto) {
        Product product = productMapper.productDtoToProduct(productDto);
        productRepository.save(product);
    }
}
