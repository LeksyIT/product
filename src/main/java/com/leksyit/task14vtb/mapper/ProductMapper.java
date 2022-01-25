package com.leksyit.task14vtb.mapper;

import com.leksyit.task14vtb.dto.ProductDto;
import com.leksyit.task14vtb.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {


    @Mapping(target="productId", source="product.id")
    @Mapping(target="productTitle", source="product.title")
    @Mapping(target="productPrice", source="product.price")
    ProductDto productToProductDto(Product product);

    @Mapping(target="id", source="productDto.productId")
    @Mapping(target="title", source="productDto.productTitle")
    @Mapping(target="price", source="productDto.productPrice")
    Product productDtoToProduct(ProductDto productDto);

    List<ProductDto> listOfProductsToListOfProductsDto(List<Product> listOfProducts);

    List<Product> listOfProductsDtoToListOfProducts(List<ProductDto> listOfProductsDto);
}
