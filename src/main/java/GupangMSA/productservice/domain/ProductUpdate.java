package GupangMSA.productservice.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductUpdate {

    private final Long id;
    private final String name;
    private final int price;
    private final String description;

    @Builder
    public ProductUpdate(Long id, String name, int price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
