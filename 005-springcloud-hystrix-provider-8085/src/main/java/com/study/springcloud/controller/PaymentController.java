package com.study.springcloud.controller;

import com.study.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @GetMapping("/payment/ok/{id}")
    public String payment_OK(@PathVariable("id") Long id){
        return paymentService.payment_OK(id);
    }

    @GetMapping("/payment/timeout/{id}")
    public String payment_Timeout(@PathVariable("id") Long id){
        return paymentService.payment_Timeout(id);
    }

    //服务熔断
    @GetMapping("/payment/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        String result = paymentService.paymentCircuitBreaker(id);
        log.info("****result: "+result);
        return result;
    }
}
