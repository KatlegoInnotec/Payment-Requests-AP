package com.example.payment_requests_demo.dto;

import com.example.payment_requests_demo.enums.PaymentStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateStatusDTO {

    @NotNull(message = "status is required")
    private PaymentStatus status;
    private String rejectionReason;

    public String getRejectionReason() {return rejectionReason;}

    public void setRejectionReason(String rejectionReason) {this.rejectionReason = rejectionReason;}

    public PaymentStatus getStatus() {return status;}

    public void setStatus(PaymentStatus status) {this.status = status;}
}
