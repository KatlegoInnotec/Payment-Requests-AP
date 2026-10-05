package com.example.payment_requests_demo.repository;

import com.example.payment_requests_demo.enums.PaymentStatus;
import com.example.payment_requests_demo.model.PaymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRequestRepository extends JpaRepository<PaymentRequest,Long> {
    List<PaymentRequest> findByStatus(PaymentStatus paymentStatus);
}
