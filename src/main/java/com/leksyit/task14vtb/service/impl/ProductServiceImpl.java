package com.leksyit.task14vtb.service.impl;


import com.leksyit.task14vtb.dto.ProductDto;
import com.leksyit.task14vtb.entity.Product;
import com.leksyit.task14vtb.mapper.ProductMapper;
import com.leksyit.task14vtb.repository.ProductRepository;
import com.leksyit.task14vtb.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Override
    @Transactional(readOnly = true)
    public ProductDto getById(Long id) {
        return productMapper.productToProductDto(productRepository.findById(id).orElseThrow());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getAllProducts() {
        return productMapper.listOfProductsToListOfProductsDto(productRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getAllProductWithFilter(String word) {
        List<Product> fullList = productRepository.findAll();
        if (word == null) {
            return productMapper.listOfProductsToListOfProductsDto(fullList);
        }
        return productMapper.listOfProductsToListOfProductsDto(fullList.stream().filter(p -> p.getTitle().contains(word)).toList());
    }

    @Override
    @Transactional
    public void deleteProduct(@PathVariable Long id) {
        productRepository.delete(productRepository.findById(id).orElseThrow());
    }

    @Override
    @Transactional
    public void updateProduct(ProductDto productDto) {
        Product product = productMapper.productDtoToProduct(productDto);
        productRepository.save(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> getProductWithPagingAndFiltering(Specification<Product> specifications, Pageable pageable) {
        return productRepository.findAll(specifications, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> sortedMinToMax() {
        return productMapper.
                listOfProductsToListOfProductsDto(productRepository.
                        findAll().
                        stream().
                        sorted((i, j) -> j.getPrice() - i.getPrice()).
                        toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> sortedMaxToMin() {
        return productMapper.
                listOfProductsToListOfProductsDto(productRepository.
                        findAll().
                        stream().
                        sorted(Comparator.comparingInt(Product::getPrice)).
                        toList());
    }

    @Override
    @Transactional
    public void addProductById(Long id, ProductDto productDto) {
        Product existingProduct = productRepository.findById(id).orElseThrow();
        existingProduct.setTitle(productDto.getProductTitle());
        existingProduct.setPrice(productDto.getProductPrice());

        productRepository.save(existingProduct);
    }

}
