package com.retirewise.service;

import com.retirewise.model.RetirementPlan;
import com.retirewise.model.User;
import com.retirewise.repository.RetirementPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RetirementPlanService {
    @Autowired
    private RetirementPlanRepository retirementPlanRepository;

    public List<RetirementPlan> getRecommendedPlans(User user, int currentAge, int retirementAge, 
                                                  double monthlyIncome, double savings, String riskTolerance) {
        // Calculate years until retirement
        int yearsUntilRetirement = retirementAge - currentAge;
        
        // Calculate target amount (simplified calculation)
        double targetAmount = calculateTargetAmount(monthlyIncome, yearsUntilRetirement);
        
        // Generate recommended plans based on risk tolerance
        return generateRecommendedPlans(user, targetAmount, riskTolerance);
    }

    private double calculateTargetAmount(double monthlyIncome, int yearsUntilRetirement) {
        // Simplified calculation: target 80% of current income for 20 years
        return monthlyIncome * 0.8 * 12 * 20;
    }

    private List<RetirementPlan> generateRecommendedPlans(User user, double targetAmount, String riskTolerance) {
        // Generate three plans with different risk levels and returns
        RetirementPlan basicPlan = createPlan(user, "Basic Retirement Plan", 500, 5, "low", targetAmount);
        RetirementPlan premiumPlan = createPlan(user, "Premium Retirement Plan", 1000, 7, "medium", targetAmount);
        RetirementPlan elitePlan = createPlan(user, "Elite Retirement Plan", 2000, 9, "high", targetAmount);

        // Save plans
        retirementPlanRepository.save(basicPlan);
        retirementPlanRepository.save(premiumPlan);
        retirementPlanRepository.save(elitePlan);

        // Return plans based on risk tolerance
        return retirementPlanRepository.findByUserAndRiskLevel(user, riskTolerance);
    }

    private RetirementPlan createPlan(User user, String name, double monthlyContribution, 
                                    double expectedReturns, String riskLevel, double targetAmount) {
        RetirementPlan plan = new RetirementPlan();
        plan.setUser(user);
        plan.setName(name);
        plan.setMonthlyContribution(monthlyContribution);
        plan.setExpectedReturns(expectedReturns);
        plan.setRiskLevel(riskLevel);
        plan.setStartDate(LocalDate.now());
        plan.setEndDate(LocalDate.now().plusYears(30));
        plan.setCurrentSavings(0);
        plan.setTargetAmount(targetAmount);
        return plan;
    }

    public List<RetirementPlan> getUserPlans(User user) {
        return retirementPlanRepository.findByUser(user);
    }
} 