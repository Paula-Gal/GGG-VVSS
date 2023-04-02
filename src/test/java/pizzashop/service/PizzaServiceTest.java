package pizzashop.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class PizzaServiceTest {
    private PizzaService pizzaService;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private MenuRepository menuRepository;

    @BeforeEach
    void init() {
        pizzaService = new PizzaService(menuRepository, paymentRepository);
    }

    @AfterEach
    void after() {
        pizzaService = null;
    }

    //ECP
    @Test
    void whenTableUnder1OrAbove8_expectInvalidTableException() {
        //bonus
        RuntimeException ex1 = assertThrows(RuntimeException.class, () -> pizzaService.addPayment(-2, PaymentType.Card, 13));
        assertEquals("Invalid table", ex1.getMessage());

        RuntimeException ex2 = assertThrows(RuntimeException.class, () -> pizzaService.addPayment(15, PaymentType.Cash, 100));
        assertEquals("Invalid table", ex2.getMessage());
    }

    @Test
    void whenTableBetween1And8_expectRepoCalled() {
        pizzaService.addPayment(5, PaymentType.Cash, 100);

        Mockito.verify(paymentRepository).add(Mockito.any(Payment.class));
    }

    @Test
    void whenAmountUnder0_expectInvalidTableException() {
        RuntimeException ex = assertThrows(RuntimeException.class, () -> pizzaService.addPayment(4, PaymentType.Card, -4));
        assertEquals("Invalid amount", ex.getMessage());
    }

    //BVA
    @Test
    void whenTableIs0_expectInvalidTableException() {
        RuntimeException ex2 = assertThrows(RuntimeException.class, () -> pizzaService.addPayment(0, PaymentType.Cash, 100));
        assertEquals("Invalid table", ex2.getMessage());
    }

    @Test
    void whenTableIs1_expectRepoCalled() {
        pizzaService.addPayment(1, PaymentType.Cash, 100);

        Mockito.verify(paymentRepository).add(Mockito.any(Payment.class));
    }

    @Test
    void whenTableIs2_expectRepoCalled() {
        pizzaService.addPayment(2, PaymentType.Card, 200);

        Mockito.verify(paymentRepository).add(Mockito.any(Payment.class));
    }

    @Test
    void whenTableIs7_expectRepoCalled() {
        pizzaService.addPayment(7, PaymentType.Cash, 120);

        Mockito.verify(paymentRepository).add(Mockito.any(Payment.class));
    }

    @Test
    void whenTableIs8_expectRepoCalled() {
        pizzaService.addPayment(8, PaymentType.Cash, 123);

        Mockito.verify(paymentRepository).add(Mockito.any(Payment.class));
    }

    @Test
    void whenTableIs9_expectInvalidTableException() {
        RuntimeException ex2 = assertThrows(RuntimeException.class, () -> pizzaService.addPayment(9, PaymentType.Cash, 999));
        assertEquals("Invalid table", ex2.getMessage());
    }
}
