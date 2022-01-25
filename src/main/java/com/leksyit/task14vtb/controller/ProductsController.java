package com.leksyit.task14vtb.controller;

import com.leksyit.task14vtb.dto.ProductDto;
import com.leksyit.task14vtb.entity.Product;
import com.leksyit.task14vtb.mapper.ProductMapper;
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

    private final ProductMapper productMapper;

    private static final String PRODUCT = "product";
    private static final String PRODUCTS = "products";
    private static final String REDIRECT_PRODUCTS = "redirect:/products";

    @GetMapping
    public String showProductsList(Model model) {
        ProductDto productDto = new ProductDto();
        model.addAttribute(PRODUCTS,productMapper.listOfProductsToListOfProductsDto(productsService.getAllProducts()));
        model.addAttribute(PRODUCT, productDto);
        return PRODUCTS;
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute(value = PRODUCT) ProductDto productDto) {
        productsService.updateProduct(productDto);
        return REDIRECT_PRODUCTS;
    }

    @GetMapping("/show/{id}")
    public String showOneProduct(Model model, @PathVariable(value = "id") Long id) {
        ProductDto productDto = productMapper.productToProductDto(productsService.getById(id));
        model.addAttribute(PRODUCT, productDto);
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
        model.addAttribute(PRODUCTS, productMapper.listOfProductsToListOfProductsDto(productsService.getAllProductWithFilter(word)));
        model.addAttribute(PRODUCT, productDto);
        model.addAttribute("word", word);
        return PRODUCTS;
    }

    @PostMapping("/edit/{id}")
    public String updateStudent(@PathVariable(value = "id") Long id, @ModelAttribute(value = PRODUCT) ProductDto productDto, Model model) {
        ProductDto existingProduct =  productMapper.productToProductDto(productsService.getProductById(id));
        existingProduct.setProductTitle(productDto.getProductTitle());
         existingProduct.setProductPrice(productDto.getProductPrice());
        productsService.updateProduct(existingProduct);

        model.addAttribute(PRODUCTS, productsService.getAllProducts());
        return REDIRECT_PRODUCTS;
    }

    @GetMapping("/edit/{id}")
    public String openUpdateStudentPage(@PathVariable(value = "id") Long id, @ModelAttribute("product") ProductDto productDto) {
        return "edit-product";
    }

    @GetMapping("/max-to-min")
    public String sortedMinToMax(Model model, @ModelAttribute("product") ProductDto productDto) {
        List<Product> productSortedList = productsService.getAllProducts().stream().sorted((i, j) -> j.getPrice() - i.getPrice()).toList();
        List<ProductDto> productDtoSortedList = productMapper.listOfProductsToListOfProductsDto(productSortedList);
        model.addAttribute(PRODUCTS, productDtoSortedList);
        model.addAttribute(PRODUCT, productDto);
        return PRODUCTS;
    }

    @GetMapping("/min-to-max")
    public String sortedMaxToMin(Model model, @ModelAttribute("product") ProductDto productDto) {
        List<Product> productSortedList = productsService.getAllProducts().stream().sorted(Comparator.comparingInt(Product::getPrice)).toList();
        List<ProductDto> productDtoSortedList = productMapper.listOfProductsToListOfProductsDto(productSortedList);
        model.addAttribute(PRODUCTS, productDtoSortedList);
        model.addAttribute(PRODUCT, productDto);
        return PRODUCTS;
    }
}
