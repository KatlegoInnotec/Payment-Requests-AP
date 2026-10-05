package com.example.payment_requests_demo.dto;

import jakarta.validation.constraints.NotBlank;

public class RejectRequestDTO {
    @NotBlank(message = "rejectionReason is required" )
    private String rejectionReason;

    public String getRejectionReason() {return rejectionReason;}

    public void setRejectionReason(String rejectionReason) {this.rejectionReason = rejectionReason;}
}
