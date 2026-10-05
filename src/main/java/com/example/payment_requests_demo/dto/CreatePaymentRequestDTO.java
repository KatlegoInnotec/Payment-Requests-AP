package com.example.payment_requests_demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CreatePaymentRequestDTO {

    @NotBlank(message = "requesterName is required")
    private String requesterName;

    @NotNull(message = "amount is required")
    @Positive(message = "amount must be greater than 0")
    private Double amount;

    @NotBlank(message = "description is required")
    private String description;

    public String getRequesterName() {return requesterName;}

    public void setRequesterName(String requesterName) {this.requesterName = requesterName;}

    public Double getAmount() {return amount;}

    public void setAmount(Double amount) {this.amount = amount;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}
}
