package com.leksyit.task14vtb.service;

import org.springframework.web.bind.annotation.PathVariable;

public interface ProductService{
    void deleteProduct(@PathVariable Long id);
}
