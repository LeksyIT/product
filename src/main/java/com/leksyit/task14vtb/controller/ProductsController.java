package com.leksyit.task14vtb.controller;

import com.leksyit.task14vtb.dto.ProductDto;
import com.leksyit.task14vtb.entity.Product;
import com.leksyit.task14vtb.service.UserService;
import com.leksyit.task14vtb.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductsController {

    private final ProductServiceImpl productsService;
    private final UserService userService;

    private static final String PRODUCT = "product";
    private static final String PRODUCTS = "products";
    private static final String REDIRECT_PRODUCTS = "redirect:/products/?size=5&word=&minPrice=&maxPrice=";
    private static final String ID = "id";

    @GetMapping
    public String showProductsList(Model model,
                                   @RequestParam(value = "word", required = false) String word,
                                   @RequestParam(value = "minPrice", required = false) Integer minPrice,
                                   @RequestParam(value = "maxPrice", required = false) Integer maxPrice,
                                   ProductDto productDto,
                                   Pageable pageable) {

        Specification<Product> specification = productsService.settingSpecification(word, minPrice, maxPrice);
        Page<Product> modelsPages = productsService.getProductWithPagingAndFiltering(specification, pageable);
        List<ProductDto> productList = productsService.getListProductsFromPageable(specification, pageable);

        model.addAttribute(PRODUCTS, productList);
        model.addAttribute(PRODUCT, productDto);
        model.addAttribute("word", word);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("currentPage", pageable.getPageNumber());
        model.addAttribute("pageNumbers", preparePageInt(pageable.getPageNumber(), modelsPages.getTotalPages()));
        model.addAttribute("login", userService.getUserName());

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
        productsService.addNewVisited(id);
        model.addAttribute(PRODUCT, productsService.getById(id));
        return "product-page";
    }

    @Secured(value="ROLE_ADMIN")
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable(value = ID) Long id) {
        productsService.deleteProduct(id);
        return REDIRECT_PRODUCTS;
    }

    @Secured(value="ROLE_ADMIN")
    @PostMapping("/edit/{id}")
    public String updateStudent(@PathVariable(value = ID) Long id,
                                @ModelAttribute(value = PRODUCT) ProductDto productDto,
                                Model model) {
        productsService.addProductById(id, productDto);

        model.addAttribute(PRODUCTS, productsService.getAllProducts());
        return REDIRECT_PRODUCTS;
    }

    @Secured(value="ROLE_ADMIN")
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

        Specification<Product> specification = Specification.where(null);

        Page<Product> modelsPages = productsService.getProductWithPagingAndFiltering(specification, pageable);
        model.addAttribute("currentPage", pageable.getPageNumber());
        model.addAttribute("pageNumbers", preparePageInt(pageable.getPageNumber(), modelsPages.getTotalPages()));

        model.addAttribute(PRODUCTS, productsService.getListProductsFromPageableAndNullSpecification(pageable));
        return PRODUCTS;
    }

    private List<Integer> preparePageInt(int current, int totalPages) {

        List<Integer> pageNumbers = new ArrayList<>();
        int start = Math.max(current - 5, 0);
        int end = Math.min(totalPages,start+11);
        for (int i = start;i<end;i++){
            pageNumbers.add(i);
        }
        return pageNumbers;
    }
}
