package com.example.payment_requests_demo.controller;


import com.example.payment_requests_demo.dto.CreatePaymentRequestDTO;
import com.example.payment_requests_demo.dto.RejectRequestDTO;
import com.example.payment_requests_demo.enums.PaymentStatus;
import com.example.payment_requests_demo.model.PaymentRequest;
import com.example.payment_requests_demo.service.PaymentRequestService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment-request")
public class  PaymentRequestController {


    private final PaymentRequestService requestService;

    public PaymentRequestController(PaymentRequestService requestService) {this.requestService = requestService;}

    @PostMapping
    public ResponseEntity<PaymentRequest> create(@Valid @RequestBody CreatePaymentRequestDTO paymentRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(requestService.create(paymentRequestDTO));
    }

    @GetMapping
    public List<PaymentRequest> getAll(@RequestParam(required = false) PaymentStatus status) {
        return requestService.findAll(status);
    }

    @GetMapping("/{id}")
    public PaymentRequest getOne(@PathVariable Long id) {
        return requestService.findOne(id);
    }

    @PostMapping("/{id}/approve")
    public PaymentRequest approve(@PathVariable Long id) {
        return requestService.approve(id);
    }

    @PostMapping("/{id}/reject")
    public PaymentRequest reject(@PathVariable Long id, @Valid @RequestBody RejectRequestDTO dto) {
        return requestService.reject(id, dto.getRejectionReason());
    }
}
