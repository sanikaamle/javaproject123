package com.retirewise.controller;

import com.retirewise.model.RetirementPlan;
import com.retirewise.model.User;
import com.retirewise.service.RetirementPlanService;
import com.retirewise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RetirementPlanController {
    @Autowired
    private RetirementPlanService retirementPlanService;

    @Autowired
    private UserService userService;

    @PostMapping("/recommendations")
    public ResponseEntity<?> getRecommendations(@RequestBody Map<String, Object> request) {
        try {
            String email = (String) request.get("email");
            int currentAge = (int) request.get("currentAge");
            int retirementAge = (int) request.get("retirementAge");
            double monthlyIncome = (double) request.get("monthlyIncome");
            double savings = (double) request.get("savings");
            String riskTolerance = (String) request.get("riskTolerance");

            User user = userService.findByEmail(email);
            List<RetirementPlan> plans = retirementPlanService.getRecommendedPlans(
                user, currentAge, retirementAge, monthlyIncome, savings, riskTolerance
            );

            return ResponseEntity.ok(plans);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/schemes/{id}")
    public ResponseEntity<?> getSchemeDetails(@PathVariable Long id) {
        try {
            // In a real application, implement this method to get detailed scheme information
            return ResponseEntity.ok().body("Scheme details for ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 