import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.*;
import static java.util.Arrays.asList;

public class VendingMachine {
    private BigDecimal  total;
    static BigDecimal[] acceptedCoins = { ONE, new BigDecimal(5), TEN, new BigDecimal(25) };

    private List<Product> products = new ArrayList<>();

    public VendingMachine() {
        this.total = ZERO;
    }

    public BigDecimal insertCoin(BigDecimal coin) {

        if(asList(acceptedCoins).contains(coin)){
            total = total.add(coin);
        }
        return total;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public BigDecimal selectProduct(Product product) throws Exception{

        if ( total.compareTo(product.price) > 0){
            total = total.subtract(product.price);
            products.add(product);
        }else {
            throw new Exception("In Sufficient Balance");
        }
        return total;
    }

    public BigDecimal returnProduct(Product product) {
        if(products.contains(product)){
            products.remove(product);
            total = total.add(product.price);
        }
        return total;
    }

    public void reset() {
        products.clear();
        total = ZERO;
    }

    public List<Product>    getProducts(){
        return products;
    }
}
