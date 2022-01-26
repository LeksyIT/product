package com.leksyit.task14vtb.controller;

import com.leksyit.task14vtb.dto.ProductDto;
import com.leksyit.task14vtb.entity.Product;
import com.leksyit.task14vtb.mapper.ProductMapper;
import com.leksyit.task14vtb.service.impl.ProductServiceImpl;
import com.leksyit.task14vtb.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductsController {

    private final ProductServiceImpl productsService;

    private final ProductMapper productMapper;

    private static final String PRODUCT = "product";
    private static final String PRODUCTS = "products";
    private static final String REDIRECT_PRODUCTS = "redirect:/products";

    @GetMapping
    public String showProductsList(Model model,
                                   @RequestParam(value = "word", required = false) String word,
                                   @RequestParam(value = "minPrice", required = false) Integer minPrice,
                                   @RequestParam(value = "maxPrice", required = false) Integer maxPrice) {
        Specification<Product> specification = Specification.where(null);
        if (word != null) {
            specification = specification.and(ProductSpecification.titleContains(word));
        }
        if (minPrice != null) {
            specification = specification.and(ProductSpecification.priceGreaterThanOrEqual(minPrice));
        }
        if (maxPrice != null) {
            specification = specification.and(ProductSpecification.priceLesserThanOrEqual(maxPrice));
        }

        ProductDto productDto = new ProductDto();
        model.addAttribute(PRODUCTS,productMapper.listOfProductsToListOfProductsDto(productsService.
                getProductWithPagingAndFiltering(specification, PageRequest.of(0, 1000)).getContent()));
        model.addAttribute(PRODUCT, productDto);
        model.addAttribute("word", word);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        return PRODUCTS;
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute(value = PRODUCT) ProductDto productDto, Model model) {
        productsService.updateProduct(productDto);

        model.addAttribute(PRODUCTS, productsService.getAllProducts());
        return REDIRECT_PRODUCTS;
    }

    @GetMapping("/show/{id}")
    public String showOneProduct(Model model, @PathVariable(value = "id") Long id) {
        model.addAttribute(PRODUCT, productsService.getById(id));
        return "product-page";
    }

    @GetMapping("/confidential/{id}")
    public String deleteStudent(@PathVariable(value = "id") Long id) {
        productsService.deleteProduct(id);
        return REDIRECT_PRODUCTS;
    }

    @GetMapping("/filter")
    public String filterProduct(Model model, @RequestParam(value = "word", required = false) String word) {
        ProductDto productDto = new ProductDto();
        model.addAttribute(PRODUCTS, productsService.getAllProductWithFilter(word));
        model.addAttribute(PRODUCT, productDto);
        model.addAttribute("word", word);
        return PRODUCTS;
    }

    @PostMapping("/edit/{id}")
    public String updateStudent(@PathVariable(value = "id") Long id, @ModelAttribute(value = PRODUCT) ProductDto productDto, Model model) {
        productsService.addProductById(id, productDto);
        model.addAttribute(PRODUCTS, productsService.getAllProducts());
        return REDIRECT_PRODUCTS;
    }

    @GetMapping("/edit/{id}")
    public String openUpdateStudentPage(@PathVariable(value = "id") Long id, @ModelAttribute("product") ProductDto productDto, Model model) {
        model.addAttribute(PRODUCT, productsService.getById(id));
        return "edit-product";
    }
//// FIXME: 26.01.2022 Сделать одним методом который принимает параметр
    @GetMapping("/max-to-min")
    public String sortedMinToMax(Model model, @ModelAttribute("product") ProductDto productDto) {
        model.addAttribute(PRODUCTS, productsService.sortedMinToMax());
        model.addAttribute(PRODUCT, productDto);
        return PRODUCTS;
    }

    @GetMapping("/min-to-max")
    public String sortedMaxToMin(Model model, @ModelAttribute("product") ProductDto productDto) {
        model.addAttribute(PRODUCTS, productsService.sortedMaxToMin());
        model.addAttribute(PRODUCT, productDto);
        return PRODUCTS;
    }
}
