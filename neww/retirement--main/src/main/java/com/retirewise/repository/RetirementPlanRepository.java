package com.retirewise.repository;

import com.retirewise.model.RetirementPlan;
import com.retirewise.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RetirementPlanRepository extends JpaRepository<RetirementPlan, Long> {
    List<RetirementPlan> findByUser(User user);
    List<RetirementPlan> findByUserAndRiskLevel(User user, String riskLevel);
} 