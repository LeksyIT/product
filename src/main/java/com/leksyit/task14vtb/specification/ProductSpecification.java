package com.leksyit.task14vtb.specification;

import com.leksyit.task14vtb.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class ProductSpecification {

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
