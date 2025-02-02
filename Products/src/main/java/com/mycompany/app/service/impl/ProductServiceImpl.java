package com.mycompany.app.service.impl;

import com.mycompany.app.entity.Product;
import com.mycompany.app.record.ProductRequest;
import com.mycompany.app.record.ProductResponse;
import com.mycompany.app.repository.ProductRepository;
import com.mycompany.app.service.ProductService;
import com.mycompany.app.service.RabbitMQProducer;
import jakarta.persistence.EntityNotFoundException;
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
                        .productId(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .quantity(product.getQuantity())
                        .description(product.getDescription())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public String updateProductQuantity(Integer id, Integer quantity) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("There is no product with id: " + id));
        if(quantity < product.getQuantity()) {
            throw new EntityNotFoundException("The product quantity is less than the current product quantity");
        }
        product.setQuantity(quantity);
        productRepository.save(product);
        return product.getName();
    }

    @Override
    public ProductResponse getProductById(Integer productId) {
        Product foundProduct = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("There is no product with id: " + productId));
        return ProductResponse.builder()
                .productId(foundProduct.getId())
                .name(foundProduct.getName())
                .price(foundProduct.getPrice())
                .quantity(foundProduct.getQuantity())
                .description(foundProduct.getDescription())
                .build();
    }
}
