package com.example.FoodieBackend.Service;


import com.example.FoodieBackend.Dto.Paymentdtos.PaymentVerifyRequest;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
 
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;
import java.util.Map;
 
@Service
@Slf4j
public class Paymentservice {
 
    @Value("${razorpay.key.id}")
    private String keyId;
 
    @Value("${razorpay.key.secret}")
    private String keySecret;
 
    public Map<String, Object> createOrder(Long amountInPaise) {
        try {
            RazorpayClient client = new RazorpayClient(keyId, keySecret);
 
            JSONObject options = new JSONObject();
            options.put("amount", amountInPaise);
            options.put("currency", "INR");
            options.put("receipt", "fodie_order_" + System.currentTimeMillis());
            options.put("payment_capture", 1);
 
            com.razorpay.Order order = client.orders.create(options);
 
            Map<String, Object> result = new HashMap<>();
            result.put("id", order.get("id"));
            result.put("amount", order.get("amount"));
            result.put("currency", order.get("currency"));
            result.put("receipt", order.get("receipt"));
            return result;
 
        } catch (RazorpayException e) {
            log.error("Razorpay order creation failed: {}", e.getMessage());
            throw new RuntimeException("Payment gateway error: " + e.getMessage());
        }
    }
 
    public boolean verifyPayment(PaymentVerifyRequest req) {
        try {
            String payload = req.getRazorpay_order_id() + "|" + req.getRazorpay_payment_id();
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(keySecret.getBytes(), "HmacSHA256"));
            byte[] hash = mac.doFinal(payload.getBytes());
 
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
 
            System.out.println("ORDER ID: " + req.getRazorpay_order_id());
            System.out.println("PAYMENT ID: " + req.getRazorpay_payment_id());
            System.out.println("SIGNATURE: " + req.getRazorpay_signature());

            String expectedSignature = hexString.toString();
            boolean valid = expectedSignature.equals(req.getRazorpay_signature());
 
            if (!valid) log.warn("Payment signature mismatch for order: {}", req.getRazorpay_order_id());
            return valid;
 
        } catch (Exception e) {
            log.error("Payment verification error: {}", e.getMessage());
            throw new RuntimeException("Payment verification failed");
        }
    }
}