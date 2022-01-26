package com.leksyit.task14vtb.specification;

import com.leksyit.task14vtb.entity.Product;
import org.springframework.data.jpa.domain.Specification;


public class ProductSpecification {

    private ProductSpecification(){}

    public static Specification<Product> titleContains(String word) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.
                like(root.get("title"), "%" + word + "%");
    }

    public static Specification<Product> priceGreaterThanOrEqual(Integer value) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.
                greaterThanOrEqualTo(root.get("price"), value);
    }

    public static Specification<Product> priceLesserThanOrEqual(Integer value) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.
                lessThanOrEqualTo(root.get("price"), value);
    }

}
