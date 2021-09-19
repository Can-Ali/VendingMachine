import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static java.math.BigDecimal.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class VendingMachineTest {

    static private VendingMachine  vendingMachine;
    BigDecimal[] coins = { new BigDecimal(50), new BigDecimal(25),ONE, new BigDecimal(5), TEN, new BigDecimal(25) };
    BigDecimal[] unAcceptedCoins = { new BigDecimal(50), new BigDecimal(20), new BigDecimal(100)};

    @Test
    @DisplayName("Don't Accept If they are not 1,5,10,25 Cents i.e. penny, nickel, dime, and quarter.")
    public void unacceptCoinTest(){
        vendingMachine = new VendingMachine();

        Stream.of(unAcceptedCoins).forEach($ -> vendingMachine.insertCoin($));

        assertThat(vendingMachine.getTotal()).isEqualTo(new BigDecimal(0));
    }

    @Test
    @DisplayName("Accepts coins of 1,5,10,25 Cents i.e. penny, nickel, dime, and quarter.")
    public void acceptCoinTest(){
        vendingMachine = new VendingMachine();

        Stream.of(coins).forEach($ -> vendingMachine.insertCoin($));

        assertThat(vendingMachine.getTotal()).isEqualTo(new BigDecimal(66));
    }

    @Test
    @DisplayName("Allow user to select products Coke(25), Pepsi(35), Soda(45)")
    public void selectProductTest(){
        vendingMachine = new VendingMachine();

        Stream.of(coins).forEach($ -> vendingMachine.insertCoin($));
        BigDecimal total = vendingMachine.getTotal();


        assertThatThrownBy(() -> {
            vendingMachine.selectProduct(Product.COKE);
            vendingMachine.selectProduct(Product.SODA);
            vendingMachine.selectProduct(Product.PEPSI);
        }).isInstanceOf(Exception.class);
        BigDecimal spending = Product.COKE.price.add(Product.PEPSI.price);

        assertThat(vendingMachine.getTotal()).isEqualTo(new BigDecimal(41));
        assertThat(spending).isEqualTo(new BigDecimal(60));
    }

    @Test
    @DisplayName("Allow user to take refund by cancelling the request.")
    public void refundTest(){
        vendingMachine = new VendingMachine();
        Stream.of(coins).forEach($ -> vendingMachine.insertCoin($));
        BigDecimal total = vendingMachine.getTotal();

        BigDecimal remaining = ZERO;

        try {
            remaining = vendingMachine.selectProduct(Product.COKE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThat(remaining).isEqualTo(total.subtract(Product.COKE.price));
    }

    @Test
    @DisplayName("Return selected product and remaining change if any.")
    public  void returnProductTest(){
        vendingMachine = new VendingMachine();
        Stream.of(coins).forEach($ -> vendingMachine.insertCoin($));

        BigDecimal total = vendingMachine.getTotal();

        BigDecimal remaining = ZERO;

        try {
            remaining = vendingMachine.selectProduct(Product.COKE);
            remaining = vendingMachine.selectProduct(Product.PEPSI);
        } catch (Exception e) {
            e.printStackTrace();
        }

        remaining = vendingMachine.returnProduct(Product.COKE);
        remaining = vendingMachine.returnProduct(Product.COKE);
        remaining = vendingMachine.returnProduct(Product.SODA);

        assertThat(remaining).isEqualTo(total.subtract(Product.PEPSI.price));
    }

    @Test
    @DisplayName("Allow reset operation for vending machine supplier.")
    public void resetTest(){
        vendingMachine = new VendingMachine();
        Stream.of(coins).forEach($ -> vendingMachine.insertCoin($));

        BigDecimal remaining = ZERO;

        try {
            remaining = vendingMachine.selectProduct(Product.COKE);
            remaining = vendingMachine.selectProduct(Product.PEPSI);
            vendingMachine.reset();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThat(vendingMachine.getTotal()).isEqualTo(ZERO);
        assertThat(vendingMachine.getProducts()).hasSize(0);
    }
}