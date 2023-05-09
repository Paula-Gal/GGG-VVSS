package pizzashop;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.PizzaService;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IntegrationTest {
    @AfterEach
    public void afterEach() throws IOException {
        new FileWriter("src/test/resources/data/payments.txt", false).close();
    }

    //Step 2
    @Test
    public void getPaymentsTest_PaymentMocked() {
        Payment payment = Mockito.mock(Payment.class);
        PaymentRepository repository = new PaymentRepository("src/test/resources/data/payments.txt");
        repository.add(payment);
        PizzaService pizzaService = new PizzaService(mock(MenuRepository.class),repository);

        List<Payment> paymentList = pizzaService.getPayments();

        assertEquals(1, paymentList.size());
        assertEquals(payment, paymentList.get(0));
    }

    @Test
    public void getTotalAmountTest_PaymentMocked() {
        Payment payment = Mockito.mock(Payment.class);
        when(payment.getType()).thenReturn(PaymentType.Cash);
        when(payment.getAmount()).thenReturn(20d);

        PaymentRepository repository = new PaymentRepository("src/test/resources/data/payments.txt");
        repository.add(payment);

        PizzaService pizzaService = new PizzaService(mock(MenuRepository.class),repository);


        Double result = pizzaService.getTotalAmount(PaymentType.Cash);


        assertEquals(20d, result);
    }

    //Step 3
    @Test
    public void getPaymentsTest() {
        Payment payment = new Payment(1,PaymentType.Cash,102.4);
        PaymentRepository repository = new PaymentRepository("src/test/resources/data/payments.txt");
        repository.add(payment);
        PizzaService pizzaService = new PizzaService(mock(MenuRepository.class),repository);

        List<Payment> paymentList = pizzaService.getPayments();

        assertEquals(1, paymentList.size());
        assertEquals(payment, paymentList.get(0));
    }

    @Test
    public void getTotalAmountTest() {
        Payment payment = new Payment(2,PaymentType.Card,102.4);
        PaymentRepository repository = new PaymentRepository("src/test/resources/data/payments.txt");
        repository.add(payment);

        PizzaService pizzaService = new PizzaService(mock(MenuRepository.class),repository);


        Double result = pizzaService.getTotalAmount(PaymentType.Card);


        assertEquals(102.4d, result);
    }
}
