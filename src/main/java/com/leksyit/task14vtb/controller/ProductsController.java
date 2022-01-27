package com.leksyit.task14vtb.controller;

import com.leksyit.task14vtb.dto.ProductDto;
import com.leksyit.task14vtb.entity.Product;
import com.leksyit.task14vtb.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductsController {

    private final ProductServiceImpl productsService;

    private static final String PRODUCT = "product";
    private static final String PRODUCTS = "products";
    private static final String REDIRECT_PRODUCTS = "redirect:/products";
    private static final String ID = "id";

    // FIXME: 27.01.2022 Добавить переключение меду страницами
    @GetMapping
    public String showProductsList(Model model,
                                   @RequestParam(value = "word", required = false) String word,
                                   @RequestParam(value = "minPrice", required = false) Integer minPrice,
                                   @RequestParam(value = "maxPrice", required = false) Integer maxPrice,
                                   ProductDto productDto,
                                   Pageable pageable) {

        Specification<Product> specification = productsService.settingSpecification(word, minPrice, maxPrice);
        List<ProductDto> productList = productsService.getListProductsFromPageable(specification, pageable);

        model.addAttribute(PRODUCTS, productList);
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
    public String showOneProduct(Model model, @PathVariable(value = ID) Long id) {
        model.addAttribute(PRODUCT, productsService.getById(id));
        return "product-page";
    }

    @GetMapping("/confidential/{id}")
    public String deleteStudent(@PathVariable(value = ID) Long id) {
        productsService.deleteProduct(id);
        return REDIRECT_PRODUCTS;
    }

    @PostMapping("/edit/{id}")
    public String updateStudent(@PathVariable(value = ID) Long id,
                                @ModelAttribute(value = PRODUCT) ProductDto productDto,
                                Model model) {
        productsService.addProductById(id, productDto);

        model.addAttribute(PRODUCTS, productsService.getAllProducts());
        return REDIRECT_PRODUCTS;
    }

    @GetMapping("/edit/{id}")
    public String openUpdateStudentPage(@PathVariable(value = ID) Long id,
                                        Model model) {

        model.addAttribute(PRODUCT, productsService.getById(id));
        return "edit-product";
    }

    @GetMapping("/sorted")
    public String sortedMinToMax(Model model,
                                 @ModelAttribute(value = PRODUCT) ProductDto productDto,
                                 Pageable pageable) {

        model.addAttribute(PRODUCTS, productsService.getListProductsFromPageableAndNullSpecification(pageable));
        return PRODUCTS;
    }
}
