package com.example.payment_requests_demo.model;

import com.example.payment_requests_demo.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;


@Entity
@Table(name = "payment_request")
@Data
public class PaymentRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String requesterName;


    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column(nullable = false)
    private Instant createdAt;

    private String rejectionReason;

    public PaymentRequest() {}

    public PaymentRequest(String requesterName, Double amount, String description, PaymentStatus status, Instant createdAt, String rejectionReason) {
        this.requesterName = requesterName;
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.rejectionReason = rejectionReason;
    }

}
