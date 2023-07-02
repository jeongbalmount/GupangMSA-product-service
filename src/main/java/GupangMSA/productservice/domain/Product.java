package GupangMSA.productservice.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Product {

    private final Long id;
    private final Long sellerId;
    private final String name;
    private final int price;
    private final String category;
    private final String description;

    @Builder
    public Product(Long id, Long sellerId, String name, int price, String category, String description) {
        this.id = id;
        this.sellerId = sellerId;
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
    }

    public static Product create(ProductRequest request) {
        return Product.builder()
                .sellerId(request.getSellerId())
                .name(request.getName())
                .price(request.getPrice())
                .category(request.getCategory())
                .description(request.getDescription())
                .build();
    }

    public Product update(ProductUpdate update) {
        return Product.builder()
                .id(id)
                .sellerId(sellerId)
                .name(update.getName())
                .price(update.getPrice())
                .description(update.getDescription())
                .category(category)
                .build();
    }
}
