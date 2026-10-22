package com.example.payment_requests_demo.dto;

import com.example.payment_requests_demo.enums.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateStatusDTO {

    @NotNull(message = "status is required")
    private PaymentStatus status;
    private String rejectionReason;
}
