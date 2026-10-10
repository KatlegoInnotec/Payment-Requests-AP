package com.example.payment_requests_demo;

import com.example.payment_requests_demo.dto.CreatePaymentRequestDTO;
import com.example.payment_requests_demo.enums.PaymentStatus;
import com.example.payment_requests_demo.exception.InvalidStateException;
import com.example.payment_requests_demo.exception.ResourceNotFoundException;
import com.example.payment_requests_demo.model.PaymentRequest;
import com.example.payment_requests_demo.service.PaymentRequestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentRequestServiceTest {

    @Autowired
    private PaymentRequestService service;

    private CreatePaymentRequestDTO sampleDto() {
        CreatePaymentRequestDTO dto = new CreatePaymentRequestDTO();
        dto.setRequesterName("Lerato Dlamini");
        dto.setAmount(4500.00);
        dto.setDescription("Printing of A1 posters");
        return dto;
    }

    //  Test 1 — happy path: create sets status PENDING and createdAt
    @Test
    void create_shouldSetStatusPendingAndTimestamp() {
        PaymentRequest created = service.create(sampleDto());

        assertNotNull(created.getId());
        assertEquals(PaymentStatus.PENDING, created.getStatus());
        assertNotNull(created.getCreatedAt());
        assertNull(created.getRejectionReason());
    }

    //  Test 2 — CRITICAL: approving twice must fail
    @Test
    void approve_shouldFailWhenRequestIsNotPending() {
        PaymentRequest created = service.create(sampleDto());
        service.approve(created.getId());  // first approval works

        // Second approval must throw InvalidStateException
        InvalidStateException ex = assertThrows(
                InvalidStateException.class,
                () -> service.approve(created.getId())
        );
        assertTrue(ex.getMessage().contains("PENDING"));
    }

    //  Test 3 — rejecting requires status PENDING and sets reason
    @Test
    void reject_shouldSetStatusAndReason_whenPending() {
        PaymentRequest created = service.create(sampleDto());
        PaymentRequest rejected = service.reject(created.getId(), "Duplicate invoice");

        assertEquals(PaymentStatus.REJECTED, rejected.getStatus());
        assertEquals("Duplicate invoice", rejected.getRejectionReason());
    }


    @Test
    void findOne_shouldThrowWhenNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> service.findOne(99999L));
    }
}