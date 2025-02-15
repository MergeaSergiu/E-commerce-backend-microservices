package com.mycompany.app.service;

import com.mycompany.app.record.ProductRequest;
import com.mycompany.app.record.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    void saveProduct(ProductRequest productRequest, MultipartFile multipartFile) throws IOException;

    List<ProductResponse> getAllProducts();

    String updateProductQuantity(Integer id, Integer quantity);

    ProductResponse getProductById(Integer productId);

}
