package com.hashedin.product.kyeazy.controllers;

import com.hashedin.product.kyeazy.dto.ActionDTO;
import com.hashedin.product.kyeazy.dto.OrderDTO;
import com.hashedin.product.kyeazy.entities.Company;
import com.hashedin.product.kyeazy.entities.CompanyOrder;
import com.hashedin.product.kyeazy.repositories.CompanyRepository;
import com.hashedin.product.kyeazy.repositories.OrderRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private  OrderRepository orderRepository;
    @Autowired
    private CompanyRepository companyRepository;

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
    @GetMapping("/payment-history/{companyId}")
    public List<CompanyOrder> getPaymentHistory(@PathVariable Integer companyId)
    {
        Pageable pageable=PageRequest.of(0, 5, Sort.by("companyOrderId").descending());
        return orderRepository.findByCompanyOrderId(companyId,pageable);
    }

    @GetMapping("/payment-success/{companyId}/{coins}/{orderId}/{paymentId}")
    public ActionDTO paymentSuccess(@PathVariable Integer companyId,@PathVariable Integer coins,@PathVariable String orderId,@PathVariable String paymentId) throws Exception {
        Company company=companyRepository.findById(companyId).get();
        company.setCoins(company.getCoins()+coins);
        CompanyOrder companyOrder=new CompanyOrder();
        companyOrder.setCompanyOrderId(companyId);
        companyOrder.setOrderId(orderId);
        companyOrder.setAmount(company.getCoins()+coins*company.getPlan());
        companyOrder.setPaymentId(paymentId);
        CompanyOrder savedOrder=orderRepository.save(companyOrder);
        return new ActionDTO(1,true,"Order Added Successfully");
    }
}