package com.mycompany.app.service.impl;

import com.mycompany.app.entity.Product;
import com.mycompany.app.record.ProductRequest;
import com.mycompany.app.record.ProductResponse;
import com.mycompany.app.repository.ProductRepository;
import com.mycompany.app.service.ProductService;
import com.mycompany.app.service.RabbitMQProducer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;
    private final RabbitMQProducer rabbitMQProducer;

    public ProductServiceImpl(ProductRepository productRepository, RabbitMQProducer rabbitMQProducer) {
        this.productRepository = productRepository;
        this.rabbitMQProducer = rabbitMQProducer;
    }

    @Override
    public void saveProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.name())
                .price(productRequest.price())
                .quantity(productRequest.quantity())
                .description(productRequest.description())
                .build();
        productRepository.save(product);
        rabbitMQProducer.sendMessage("A new product was added: " + "\nName:" + product.getName() + "\n Price: " + product.getPrice());
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            return new ArrayList<>();
        }
        return products.stream()
                .map(product -> ProductResponse.builder()
                        .name(product.getName())
                        .price(product.getPrice())
                        .quantity(product.getQuantity())
                        .description(product.getDescription())
                        .build())
                .collect(Collectors.toList());
    }
}
