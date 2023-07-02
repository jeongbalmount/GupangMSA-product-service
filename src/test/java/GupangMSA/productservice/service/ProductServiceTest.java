package GupangMSA.productservice.service;

import GupangMSA.productservice.domain.Product;
import GupangMSA.productservice.domain.ProductRequest;
import GupangMSA.productservice.domain.ProductUpdate;
import GupangMSA.productservice.exception.NoProductException;
import GupangMSA.productservice.infrastructure.ProductRepository;
import GupangMSA.productservice.mock.FakeProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
public class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository;

    @BeforeEach
    void init() {
        Product product1 = Product.builder()
                .sellerId(1L)
                .name("apple")
                .price(1000)
                .category("fruit")
                .description("delicious apple")
                .build();
        Product product2 = Product.builder()
                .sellerId(1L)
                .name("pear")
                .price(2000)
                .category("fruit")
                .description("delicious pear")
                .build();
        Product product3 = Product.builder()
                .sellerId(2L)
                .name("phone")
                .price(100000)
                .category("tech")
                .description("cheap phone")
                .build();


        ProductRepository productRepository = new FakeProductRepository();
        this.productRepository = productRepository;
        this.productRepository.save(product1);
        this.productRepository.save(product2);
        this.productRepository.save(product3);

        this.productService = new ProductService(productRepository);
    }

    @Test
    void create_메서드로_Product_도메인을_저장할_수_있다() {
        // given
        ProductRequest request = ProductRequest.builder()
                .sellerId(1L)
                .name("new apples")
                .price(1000)
                .category("fruit")
                .description("delicious fruits")
                .build();

        // when
        Product product = this.productService.create(request);

        // then
        Optional<Product> optionalProduct = this.productRepository.findById(product.getId());
        Product foundProduct = optionalProduct.orElseThrow(() -> new NoProductException("no product"));
        assertThat(foundProduct.getId()).isNotNull();
        assertThat(foundProduct.getName()).isEqualTo("new apples");
        assertThat(foundProduct.getDescription()).isEqualTo("delicious fruits");
    }

    @Test
    void findBySellerId로_seller가_등록한_상품들을_조회할_수_있다() {
        // given

        // when
        List<Product> sellerProducts = this.productService.getSellerProducts(1L);

        // then
        assertThat(sellerProducts.get(0).getName()).isEqualTo("apple");
        assertThat(sellerProducts.get(1).getName()).isEqualTo("pear");
        assertThat(sellerProducts.get(1).getDescription()).isEqualTo("delicious pear");
    }

    @Test
    void update_함수로_Product_도메인을_수정할_수_있다() {
        // given
        List<Product> sellerProducts = this.productService.getSellerProducts(2L);
        Product product = sellerProducts.get(0);
        Long id = product.getId();
        String name = product.getName();

        ProductUpdate update = ProductUpdate.builder()
                .id(id)
                .name(name)
                .price(77000)
                .description("updated description")
                .build();

        // when
        Product updatedProduct = this.productService.update(update);

        // then
        assertThat(updatedProduct.getName()).isEqualTo("phone");
        assertThat(updatedProduct.getPrice()).isEqualTo(77000);
        assertThat(updatedProduct.getDescription()).isEqualTo("updated description");
        // update는 Product 도큐먼트에 데이터를 덮어 씌워야 한다.
        assertThat(this.productService.getSellerProducts(2L).size()).isEqualTo(1);
    }

    // delete
    @Test
    void delete_함수로_Product_도큐먼트를_삭제할_수_있다() {
        // given
        List<Product> sellerProducts = this.productService.getSellerProducts(2L);
        Product product = sellerProducts.get(0);
        Long id = product.getId();

        // when
        this.productService.delete(id);

        // then
        assertThatThrownBy(() -> this.productService.getProductById(id))
                .isInstanceOf(NoProductException.class);
    }

    /*
    - Product 저장할 때
    1. productSearch-service에 Product 필드들 전달
    2. Inventory-service에 Product count 전달
     */
}
