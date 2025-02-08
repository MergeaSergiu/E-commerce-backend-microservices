package com.mycompany.app.service;

import com.mycompany.app.record.ProductRequest;
import com.mycompany.app.record.ProductResponse;

import java.util.List;

public interface ProductService {

    void saveProduct(ProductRequest productRequest);

    List<ProductResponse> getAllProducts();

    String updateProductQuantity(Integer id, Integer quantity);

    ProductResponse getProductById(Integer productId);

}
