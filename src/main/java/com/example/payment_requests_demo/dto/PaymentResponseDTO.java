package com.example.payment_requests_demo.dto;

public class PaymentResponseDTO {
    private Long id;
    private String requesterName;
    private Double amount;
    private String description;

    // Constructors, Getters, and Setters
    public PaymentResponseDTO(Long id, String requesterName, Double amount, String description) {
        this.id = id;
        this.requesterName = requesterName;
        this.amount = amount;
        this.description = description;
    }

    public Long getId() { return id; }
    public String getRequesterName() { return requesterName; }
    public Double getAmount() { return amount; }
    public String getDescription() { return description; }
}
