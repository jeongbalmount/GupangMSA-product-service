package GupangMSA.productservice.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest {

    @Test
    void create_메서드로_Product_도메인을_만들수_있다() {

        // given
        ProductRequest request = ProductRequest.builder()
                .sellerId(1L)
                .name("apple")
                .price(1000)
                .category("fruit")
                .description("delicious fruits")
                .build();

        // when
        Product product = Product.create(request);

        // then
        assertThat(product.getSellerId()).isEqualTo(1L);
        assertThat(product.getCategory()).isEqualTo("fruit");
    }

    @Test
    void update_메서드로_Product_도메인_정보를_업데이트할_수_있다() {
        // name price description
        // given
        Product product = Product.builder()
                .id(3L)
                .sellerId(1L)
                .name("apple")
                .price(1000)
                .category("fruit")
                .description("delicious fruits")
                .build();

        ProductUpdate update = ProductUpdate.builder()
                .id(3L)
                .name("apple")
                .price(1500)
                .description("delicious and pretty fruits")
                .build();

        // when
        Product updatedProduct = product.update(update);

        // then
        assertThat(updatedProduct.getName()).isEqualTo("apple");
        assertThat(updatedProduct.getPrice()).isEqualTo(1500);
        assertThat(updatedProduct.getDescription()).isEqualTo("delicious and pretty fruits");
    }

}
