package com.prodapt.capstoneproject.serviceimpltests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prodapt.capstoneproject.entities.Epaymethod;
import com.prodapt.capstoneproject.entities.Payments;
import com.prodapt.capstoneproject.exceptions.PaymentNotFoundException;
import com.prodapt.capstoneproject.model.PaymentDetails;
import com.prodapt.capstoneproject.repositories.PaymentsRepository;
import com.prodapt.capstoneproject.service.PaymentServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

	@Mock
	private PaymentsRepository payrep;

	@InjectMocks
	private PaymentServiceImpl paymentService;

	@Test
    void testAddPayment() {
        Payments payment = new Payments();
        when(payrep.save(any(Payments.class))).thenReturn(payment);

        Payments result = paymentService.addPayments(payment);

        assertNotNull(result);
        assertEquals(payment, result);
    }

    @Test
    void testUpdatePayment() throws PaymentNotFoundException {
        Payments payment = new Payments();
        when(payrep.findById(anyLong())).thenReturn(Optional.of(payment));
        when(payrep.save(any(Payments.class))).thenReturn(payment);

        Payments result = paymentService.updatePayments(payment);

        assertNotNull(result);
        assertEquals(payment, result);
    }

    @Test
    void testGetPayment() throws PaymentNotFoundException {
        Long id = 1L;
        Payments payment = new Payments();
        when(payrep.findById(id)).thenReturn(Optional.of(payment));

        Payments result = paymentService.getPayment(id);

        assertNotNull(result);
        assertEquals(payment, result);
    }

    @Test
    void testDeletePayment() throws PaymentNotFoundException {
        Long id = 1L;
        Payments payment = new Payments();
        when(payrep.findById(id)).thenReturn(Optional.of(payment));

        paymentService.deletePayment(id);

        verify(payrep, times(1)).deleteById(id);
    }

    @Test
    void testGetAllPayments() {
        List<Payments> payments = Arrays.asList(new Payments(), new Payments());
        when(payrep.findAll()).thenReturn(payments);

        List<Payments> result = paymentService.getAllPayments();

        assertNotNull(result);
        assertEquals(payments, result);
    }

	@Test
	void testGetPaymentDetailsReport_EmptyList() {

		// Act
		List<PaymentDetails> paymentDetails = paymentService.getPaymentDetailsReport();

		// Assert
		assertNotNull(paymentDetails);
		assertEquals(0, paymentDetails.size());
	}
	@Test
	void testGetPaymentDetailsReport_SingleRecord() {
	    // Arrange
	    List<Object[]> results = new ArrayList<>();
	    results.add(new Object[] { 1L, "John Doe", "johndoe@example.com", 1234567890L,
	            java.sql.Date.valueOf("2022-01-01"), 100, "BANK_TRANSFER" });
	    when(payrep.findPaymentDetails()).thenReturn(results);

	    // Act
	    List<PaymentDetails> paymentDetails = paymentService.getPaymentDetailsReport();

	    // Assert
	    assertNotNull(paymentDetails);
	    assertEquals(1, paymentDetails.size());
	    PaymentDetails paymentDetail = paymentDetails.get(0);
	    assertEquals(1L, paymentDetail.getPayment_id());
	    assertEquals("John Doe", paymentDetail.getCustomer_name());
	    assertEquals("johndoe@example.com", paymentDetail.getEmail());
	    assertEquals(1234567890L, paymentDetail.getPhone());
	    assertEquals(java.time.LocalDate.of(2022, 1, 1), paymentDetail.getPayment_date());
	    assertEquals(100, paymentDetail.getAmount());
	    assertEquals(Epaymethod.BANK_TRANSFER, paymentDetail.getMethod());
	}

	@Test
	void testGetPaymentDetailsReport_MultipleRecords() {
	    // Arrange
	    List<Object[]> results = new ArrayList<>();
	    results.add(new Object[] { 1L, "John Doe", "johndoe@example.com", 1234567890L,
	            java.sql.Date.valueOf("2022-01-01"), 100, "BANK_TRANSFER" });
	    results.add(new Object[] { 2L, "Jane Doe", "janedoe@example.com", 9876543210L,
	            java.sql.Date.valueOf("2022-01-15"), 200, "CREDIT_CARD" });
	    when(payrep.findPaymentDetails()).thenReturn(results);

	    // Act
	    List<PaymentDetails> paymentDetails = paymentService.getPaymentDetailsReport();

	    // Assert
	    assertNotNull(paymentDetails);
	    assertEquals(2, paymentDetails.size());
	    PaymentDetails paymentDetail1 = paymentDetails.get(0);
	    assertEquals(1L, paymentDetail1.getPayment_id());
	    assertEquals("John Doe", paymentDetail1.getCustomer_name());
	    assertEquals("johndoe@example.com", paymentDetail1.getEmail());
	    assertEquals(1234567890L, paymentDetail1.getPhone());
	    assertEquals(java.time.LocalDate.of(2022, 1, 1), paymentDetail1.getPayment_date());
	    assertEquals(100, paymentDetail1.getAmount());
	    assertEquals(Epaymethod.BANK_TRANSFER, paymentDetail1.getMethod());

	    PaymentDetails paymentDetail2 = paymentDetails.get(1);
	    assertEquals(2L, paymentDetail2.getPayment_id());
	    assertEquals("Jane Doe", paymentDetail2.getCustomer_name());
	    assertEquals("janedoe@example.com", paymentDetail2.getEmail());
	    assertEquals(9876543210L, paymentDetail2.getPhone());
	    assertEquals(java.time.LocalDate.of(2022, 1, 15), paymentDetail2.getPayment_date());
	    assertEquals(200, paymentDetail2.getAmount());
	    assertEquals(Epaymethod.CREDIT_CARD, paymentDetail2.getMethod());
	}
	@Test
	void testUpdatePayment_Failure_NotFound() {
		if (payrep != null) {
			Payments payment = new Payments();
			when(payrep.findById(payment.getPaymentid())).thenReturn(Optional.empty());

			PaymentNotFoundException exception = assertThrows(PaymentNotFoundException.class,
					() -> paymentService.updatePayments(payment));
			assertEquals("Payment not found with id: " + payment.getPaymentid(), exception.getMessage());
		}
	}

	@Test
	void testGetPayment_Failure_NotFound() {
		if (payrep != null) {
			Long id = 1L;
			when(payrep.findById(id)).thenReturn(Optional.empty());

			PaymentNotFoundException exception = assertThrows(PaymentNotFoundException.class,
					() -> paymentService.getPayment(id));
			assertEquals("Payment not found with id: " + id, exception.getMessage());
		}
	}

	@Test
	void testDeletePayment_Failure_NotFound() {
		if (payrep != null) {
			Long id = 1L;
			when(payrep.findById(id)).thenReturn(Optional.empty());

			PaymentNotFoundException exception = assertThrows(PaymentNotFoundException.class,
					() -> paymentService.deletePayment(id));
			assertEquals("Payment not found with id: " + id, exception.getMessage());
		}
	}
}