package com.example.FoodieBackend.Dto;


import lombok.Data;

public class Paymentdtos {

   @Data
   public static class PaymentOrderRequest {
       private Long amount; // in paise (1 INR = 100 paise)
   }

   @Data
   public static class PaymentVerifyRequest {
       private String razorpay_order_id;
       private String razorpay_payment_id;
       private String razorpay_signature;
   }

   @Data
   public static class StatusUpdateRequest {
       private String status;
   }
}
