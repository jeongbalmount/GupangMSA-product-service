package GupangMSA.productservice.service;

import GupangMSA.productservice.domain.Product;
import GupangMSA.productservice.domain.ProductRequest;
import GupangMSA.productservice.domain.ProductUpdate;
import GupangMSA.productservice.exception.NoProductException;
import GupangMSA.productservice.service.port.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product create(ProductRequest request) {
        Product product = Product.create(request);
        return productRepository.save(product);
    }

    public List<Product> getSellerProducts(Long sellerId) {
        return productRepository.findBySellerId(sellerId);
    }

    public Product update(ProductUpdate productUpdate) {
        Optional<Product> optionalProduct = productRepository.findById(productUpdate.getId());
        Product product = optionalProduct.orElseThrow(() -> new NoProductException("no product error"));
        Product updatedProduct= product.update(productUpdate);

        return productRepository.save(updatedProduct);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new NoProductException("no product error"));
    }

    public void delete(Long id) {
        productRepository.delete(id);
    }

}
