package com.leksyit.task14vtb.service;

import com.leksyit.task14vtb.dto.ProductDto;
import com.leksyit.task14vtb.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ProductService {

    void deleteProduct(@PathVariable Long id);

    void updateProduct(ProductDto productDto);

    ProductDto getById(Long id);

    List<ProductDto> getAllProducts();

    List<ProductDto> getAllProductWithFilter(String word);

    Page<Product> getProductWithPagingAndFiltering(Specification<Product> specifications, Pageable pageable);

    void addProductById(Long id, ProductDto productDto);

    List<ProductDto> getListProductsFromPageableAndNullSpecification(Pageable pageable);

    List<ProductDto> getListProductsFromPageable(Specification<Product> productSpecification, Pageable pageable);

    Specification<Product> settingSpecification(String word, Integer minPrice, Integer maxPrice);

    void addNewVisited(Long id);

    List<Integer> preparePageInt(int current, int totalPages);
}
