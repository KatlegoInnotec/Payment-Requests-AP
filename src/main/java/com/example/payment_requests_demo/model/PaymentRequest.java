package com.example.payment_requests_demo.model;

import com.example.payment_requests_demo.enums.PaymentStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "payment_request")
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

    public PaymentStatus getStatus() {return status;}

    public void setStatus(PaymentStatus status) {this.status = status;}

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getRejectionReason() { return rejectionReason;}

    public void setRejectionReason(String rejectionReason) {this.rejectionReason = rejectionReason;}

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getRequesterName() {return requesterName;}

    public void setRequesterName(String requesterName) {this.requesterName = requesterName;}

    public Double getAmount() {return amount;}

    public void setAmount(Double amount) {this.amount = amount;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}
}
