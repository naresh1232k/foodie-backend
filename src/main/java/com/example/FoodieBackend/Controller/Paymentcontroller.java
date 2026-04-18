package com.example.FoodieBackend.Controller;


import lombok.RequiredArgsConstructor;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.example.FoodieBackend.Dto.Paymentdtos.PaymentVerifyRequest;
import com.example.FoodieBackend.Service.Paymentservice;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

import java.util.Map;
 
@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class Paymentcontroller {
 
    private final Paymentservice paymentService;
 
    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> data) {
        try {
            Long amount = ((Number) data.get("amount")).longValue();

            Map<String, Object> order = paymentService.createOrder(amount);

            return ResponseEntity.ok(order);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of(
                    "message", "Something went wrong",
                    "error", e.getMessage()
            ));
        }
    }
 
    // Step 2: Verify Razorpay signature after payment
    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(@RequestBody PaymentVerifyRequest req) {
        boolean valid = paymentService.verifyPayment(req);
        if (valid) {
            return ResponseEntity.ok(Map.of("success", true, "message", "Payment verified"));
        } else {
            return ResponseEntity.status(400)
                    .body(Map.of("success", false, "message", "Payment verification failed"));
        }
    }
}