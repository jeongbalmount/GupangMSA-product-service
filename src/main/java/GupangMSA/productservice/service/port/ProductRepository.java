package GupangMSA.productservice.service.port;

import GupangMSA.productservice.domain.Product;
import GupangMSA.productservice.domain.ProductRequest;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    // CRUD

    Product save(Product product);

    List<Product> findBySellerId(Long sellerId);

    void delete(Long id);

    Optional<Product> findById(Long id);


}
