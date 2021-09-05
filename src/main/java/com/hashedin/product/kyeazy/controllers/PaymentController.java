package com.hashedin.product.kyeazy.controllers;

import com.hashedin.product.kyeazy.dto.ActionDTO;
import com.hashedin.product.kyeazy.dto.OrderDTO;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/payment")
public class PaymentController {

    @GetMapping("/create-order/{amount}")
    public OrderDTO generateOrder(@PathVariable int amount) throws Exception {
        RazorpayClient razorpay = new RazorpayClient("rzp_test_51mlvZBHt5Cbjq", "NyOFOInK35B9BvW9RSQrhjun");
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount); // amount in the smallest currency unit
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "order_rcptid_1");
        Order order = razorpay.Orders.create(orderRequest);
        JSONObject jsonObject = new JSONObject(String.valueOf(order));
        String id = jsonObject.getString("id");

        return new OrderDTO(id);
    }

    @GetMapping("/payment-success/{orderId}/{paymentId}/{razorpaySignature}")
    public OrderDTO paymentSuccess(@PathVariable int amount) throws Exception {
        System.out.println("chalaaaa");
        return null;

    }
}