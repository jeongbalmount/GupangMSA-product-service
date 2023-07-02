package GupangMSA.productservice.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductRequest {

    private final Long sellerId;
    private final String name;
    private final int price;
    private final String category;
    private final String description;

    @Builder
    public ProductRequest(Long sellerId, String name, int price, String category, String description) {
        this.sellerId = sellerId;
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
    }
}
