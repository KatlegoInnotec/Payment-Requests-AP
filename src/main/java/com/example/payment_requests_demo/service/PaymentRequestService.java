package com.example.payment_requests_demo.service;

import com.example.payment_requests_demo.dto.CreatePaymentRequestDTO;
import com.example.payment_requests_demo.enums.PaymentStatus;
import com.example.payment_requests_demo.exception.InvalidStateException;
import com.example.payment_requests_demo.exception.ResourceNotFoundException;
import com.example.payment_requests_demo.model.PaymentRequest;
import com.example.payment_requests_demo.repository.PaymentRequestRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;


@Service
public class PaymentRequestService {

    private PaymentRequestRepository repository;

    public PaymentRequestService(PaymentRequestRepository repository) {
        this.repository = repository;
    }

    public PaymentRequest create(CreatePaymentRequestDTO requestDTO){
        PaymentRequest paymentRequest = new PaymentRequest();

        paymentRequest.setRequesterName(requestDTO.getRequesterName());
        paymentRequest.setAmount(requestDTO.getAmount());
        paymentRequest.setDescription(requestDTO.getDescription());
        paymentRequest.setStatus(PaymentStatus.PENDING);
        paymentRequest.setCreatedAt(Instant.now());
        paymentRequest.setRejectionReason(null);

        return repository.save(paymentRequest);
    }

    public List<PaymentRequest> findAll(PaymentStatus  status ) {
        if (status == null) {
            return repository.findAll();
        }
        return repository.findByStatus(status);
    }

    public PaymentRequest findOne(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment request " + id + " not found"));
    }

    public PaymentRequest approve(Long id) {
        PaymentRequest request = findOne(id);
        requirePending(request, "approved");
        request.setStatus(PaymentStatus.APPROVED);
        return repository.save(request);
    }

    private void requirePending(PaymentRequest request, String action) {
        if (request.getStatus() != PaymentStatus.PENDING) {
            throw new InvalidStateException(
                    "Only PENDING requests can be " + action +
                            ". This request is already " + request.getStatus()
            );
        }
    }

    public PaymentRequest reject(Long id, String reason) {
        PaymentRequest request = findOne(id);
        requirePending(request, "rejected");
        request.setStatus(PaymentStatus.REJECTED);
        request.setRejectionReason(reason);
        return repository.save(request);
    }

}
