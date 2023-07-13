package com.mabaya.assignment.loader;

import com.mabaya.assignment.model.Product;
import com.mabaya.assignment.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Loader {

    @Autowired
    private ProductRepository productRepository;

    @PostConstruct
    private void loadData() {
        if(productRepository.count() > 0) return;
        List<String> categoriesList = List.of("1","2","3");
        List<Product> products = new ArrayList<>();

        for(int i=0; i<5;++i) {
            int randomCategory = (int) (Math.random() * 3);
            Product p = new Product("title" + i, categoriesList.get(randomCategory), (randomCategory+1)*10 );
            products.add(p);
        }

        productRepository.saveAll(products);

    }
}
