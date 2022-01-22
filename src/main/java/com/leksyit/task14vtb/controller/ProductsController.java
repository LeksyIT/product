package com.leksyit.task14vtb.controller;


import com.leksyit.task14vtb.entity.Product;
import com.leksyit.task14vtb.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductsController {

    private final ProductServiceImpl productsService;

    private static final String PRODUCT = "product";
    private static final String PRODUCTS = "products";



    @GetMapping
    public String showProductsList(Model model) {
        Product product = new Product();
        model.addAttribute(PRODUCTS, productsService.getAllProducts());
        model.addAttribute(PRODUCT, product);
        return PRODUCTS;
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute(value = PRODUCT) Product product) {
        productsService.add(product);
        return "redirect:/products";
    }

    @GetMapping("/show/{id}")
    public String showOneProduct(Model model, @PathVariable(value = "id") Long id) {
        Product product = productsService.getById(id);
        model.addAttribute(PRODUCT, product);
        return "product-page";
    }

    @GetMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {
        productsService.deleteProduct(id);
        return "redirect:/products";
    }

    @GetMapping("/filter")
    public String filterProduct(Model model, @RequestParam(value = "word", required = false) String word) {
        Product product = new Product();
        model.addAttribute(PRODUCTS, productsService.getAllProductWithFilter(word));
        model.addAttribute(PRODUCT, product);
        model.addAttribute("word", word);
        return PRODUCTS;
    }

    @PostMapping("/edit/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute(value = PRODUCT) Product product, Model model) {
        model.addAttribute(PRODUCTS, productsService.getAllProducts());

        //get product from database by id
        Product existingProduct = productsService.getProductById(id);
        existingProduct.setId(id);
        existingProduct.setTitle(product.getTitle());
        existingProduct.setPrice(product.getPrice());

        //save updated product object
        productsService.updateProduct(existingProduct);
        return PRODUCTS;
    }

    @GetMapping("/edit/{id}")
    public String openUpdateStudentPage(@PathVariable Long id, @ModelAttribute("product") Product product) {
        return "edit-product";
    }

    @GetMapping("/max-to-min")
    public String sortedMinToMax(Model model, @ModelAttribute("product") Product product){
        List<Product> productSortedList = productsService.getAllProducts().stream().sorted((i,j)->j.getPrice()-i.getPrice()).toList();
        model.addAttribute(PRODUCTS,productSortedList );
        model.addAttribute(PRODUCT, product);
        return PRODUCTS;
    }

    @GetMapping("/min-to-max")
    public String sortedMaxToMin(Model model, @ModelAttribute("product") Product product){
        List<Product> productSortedList = productsService.getAllProducts().stream().sorted(Comparator.comparingInt(Product::getPrice)).toList();
        model.addAttribute(PRODUCTS,productSortedList );
        model.addAttribute(PRODUCT, product);
        return PRODUCTS;
    }
}
