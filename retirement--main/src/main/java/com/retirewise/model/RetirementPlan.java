package com.retirewise.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import jakarta.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "retirement_plans")
public class RetirementPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String name;

    private double monthlyContribution;

    private double expectedReturns;

    private String riskLevel;

    private LocalDate startDate;

    private LocalDate endDate;

    private double currentSavings;

    private double targetAmount;
}