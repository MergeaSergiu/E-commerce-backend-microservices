package com.mycompany.app.service.impl;

import com.mycompany.app.entity.Product;
import com.mycompany.app.record.ProductRequest;
import com.mycompany.app.record.ProductResponse;
import com.mycompany.app.repository.ProductRepository;
import com.mycompany.app.service.ProductService;
import com.mycompany.app.service.RabbitMQProducer;
import com.mycompany.app.service.S3Service;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;
    private final RabbitMQProducer rabbitMQProducer;
    private final S3Service s3Service;

    public ProductServiceImpl(ProductRepository productRepository,
                              RabbitMQProducer rabbitMQProducer,
                              S3Service s3Service) {
        this.productRepository = productRepository;
        this.rabbitMQProducer = rabbitMQProducer;
        this.s3Service = s3Service;
    }

    @Override
    public void saveProduct(ProductRequest productRequest, MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            throw new IOException("File is empty or missing!");
        }

        String fileName = multipartFile.getOriginalFilename();
        if(fileName == null || fileName.isEmpty()) {
            throw new IOException("Filename is empty or missing!");
        }

        File tempFile = new File(System.getProperty("java.io.tmpdir"), fileName);
        multipartFile.transferTo(tempFile);

        if (!tempFile.exists()) {
            throw new IOException("File was not saved properly.");
        }

        String imageURL = s3Service.uploadFile(fileName, tempFile);

        Product product = Product.builder()
                .name(productRequest.name())
                .price(productRequest.price())
                .quantity(productRequest.quantity())
                .description(productRequest.description())
                .imageURL(imageURL)
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
                        .imageURL(product.getImageURL())
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
                .imageURL(foundProduct.getImageURL())
                .description(foundProduct.getDescription())
                .build();
    }
}
