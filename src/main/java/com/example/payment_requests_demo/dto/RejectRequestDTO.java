package com.example.payment_requests_demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RejectRequestDTO {
    @NotBlank(message = "rejectionReason is required" )
    private String rejectionReason;
}
