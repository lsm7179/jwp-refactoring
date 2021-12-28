package kitchenpos.fixture;

import kitchenpos.product.domain.Product;

import java.math.BigDecimal;

public class ProductFixture {

    public static Product 생성(String name, BigDecimal price) {
        return new Product(name, price);
    }
}
