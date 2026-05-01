package com.example.FoodieBackend.Service;


import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.example.FoodieBackend.Models.Order;

@Service
public class OrderAsyncService {

    @Async("taskExecutor")
    public void processOrder(Order order) {

        System.out.println("Started processing order: " + order.getId()
                + " Thread: " + Thread.currentThread().getName());

        try {
            Thread.sleep(5000); // simulate heavy task
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Completed processing order: " + order.getId());
    }
}
