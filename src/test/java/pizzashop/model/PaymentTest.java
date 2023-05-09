package pizzashop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentTest {
    @Test
    public void constructorAndGetterTest(){
        Payment payment = new Payment(1,PaymentType.Card,10d);

        assertEquals(1,payment.getTableNumber());
        assertEquals(PaymentType.Card,payment.getType());
        assertEquals(10d,payment.getAmount());
    }

    @Test
    public void setterTest(){
        Payment payment = new Payment(1,PaymentType.Card,10d);
        payment.setTableNumber(2);
        payment.setType(PaymentType.Cash);
        payment.setAmount(20.5);

        assertEquals(2,payment.getTableNumber());
        assertEquals(PaymentType.Cash,payment.getType());
        assertEquals(20.5,payment.getAmount());
    }
}
