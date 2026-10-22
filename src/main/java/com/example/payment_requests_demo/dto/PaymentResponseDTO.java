package com.example.payment_requests_demo.dto;

import lombok.Data;

@Data
public class PaymentResponseDTO {
    private Long id;
    private String requesterName;
    private Double amount;
    private String description;

    // Constructors
    public PaymentResponseDTO(Long id, String requesterName, Double amount, String description) {
        this.id = id;
        this.requesterName = requesterName;
        this.amount = amount;
        this.description = description;
    }
}
