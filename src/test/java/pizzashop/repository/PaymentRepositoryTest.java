package pizzashop.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentRepositoryTest {
    @AfterEach
    public void afterEach() throws IOException {
        new FileWriter("src/test/resources/data/payments.txt", false).close();
    }
    @Test
    public void testAdd() {
        Payment payment = Mockito.mock(Payment.class);
        PaymentRepository repository = new PaymentRepository("src/test/resources/data/payments.txt");

        repository.add(payment);

        List<Payment> paymentList = repository.getAll();
        assertEquals(1, paymentList.size());
        assertEquals(payment, paymentList.get(0));
    }

    @Test
    public void testWriteAll() throws IOException {
        PaymentRepository repository = Mockito.spy(new PaymentRepository("src/test/resources/data/payments.txt"));
        List<Payment> paymentList = new ArrayList<>();
        Payment payment1 = new Payment(1, PaymentType.Cash, 10.0);
        Payment payment2 = new Payment(2, PaymentType.Card, 20.0);
        paymentList.add(payment1);
        paymentList.add(payment2);
        Mockito.doReturn(paymentList).when(repository).getAll();

        repository.writeAll();

        File file = new File("src/test/resources/data/payments.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        assertEquals("1,Cash,10.0", br.readLine());
        assertEquals("2,Card,20.0", br.readLine());
        br.close();
    }
}
