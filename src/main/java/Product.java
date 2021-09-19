import java.math.BigDecimal;

public enum Product {
    COKE("Coke",new BigDecimal(25)),
    PEPSI("Pepsi",new BigDecimal(35)),
    SODA("Soda",new BigDecimal(45));

    String name;
    BigDecimal  price;

    Product(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }
}
