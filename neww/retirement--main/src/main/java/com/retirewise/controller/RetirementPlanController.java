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
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5501","http://127.0.0.1:5501"}, allowCredentials = "true")
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

        // Handle the possibility of receiving an Integer or Double for monthlyIncome and savings
        double monthlyIncome = convertToDouble(request.get("monthlyIncome"));
        double savings = convertToDouble(request.get("savings"));

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
// Helper method to safely convert Integer or Double to Double
private double convertToDouble(Object value) {
    if (value instanceof Integer) {
        return ((Integer) value).doubleValue();
    } else if (value instanceof Double) {
        return (Double) value;
    }
    return 0.0; // Default if not valid
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