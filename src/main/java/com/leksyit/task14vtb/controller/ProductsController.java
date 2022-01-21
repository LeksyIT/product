package com.leksyit.task14vtb.controller;


import com.leksyit.task14vtb.entity.Product;
import com.leksyit.task14vtb.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductsController {
    private ProductServiceImpl productsService;

    private static final String PRODUCT = "product";
    private static final String PRODUCTS = "products";

    @Autowired
    public void setProductsService(ProductServiceImpl productsService) {
        this.productsService = productsService;
    }

    @GetMapping
    public String showProductsList(Model model) {
        Product product = new Product();
        model.addAttribute(PRODUCTS, productsService.getAllProducts());
        model.addAttribute(PRODUCT, product);
        return "products";
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
    public String filterProduct(Model model, @RequestParam(value = "word", required = false)String word) {
        Product product = new Product();
        model.addAttribute(PRODUCTS,productsService.getAllProductWithFilter(word));
        model.addAttribute(PRODUCT,product);
        model.addAttribute("word",word);
        return PRODUCTS;
    }

    @PostMapping("/edit/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute(value = PRODUCT) Product product, Model model) {

        //get product from database by id
        Product existingProduct = productsService.getProductById(id);
        System.out.println(existingProduct.getId() + " " + existingProduct.getTitle() + " " + existingProduct.getPrice());
        existingProduct.setId(id);
        existingProduct.setTitle(product.getTitle());
        existingProduct.setPrice(product.getPrice());
        System.out.println(existingProduct.getId() + " " + existingProduct.getTitle() + " " + existingProduct.getPrice());
        //save updated product object
        productsService.updateProduct(existingProduct);
        model.addAttribute(PRODUCTS,productsService.getAllProducts());
        return PRODUCTS;
    }
    @GetMapping("/edit/{id}")
    public String openUpdateStudentPage(@PathVariable Long id, @ModelAttribute("product") Product product) {
        return "edit-product";
    }
}
