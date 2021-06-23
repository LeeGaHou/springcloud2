package com.study.springcloud.controller;

import com.study.springcloud.model.CommonResult;
import com.study.springcloud.service.PaymentFeignServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentFeignController {

    @Resource
    private PaymentFeignServer paymentFeignServer;

    @GetMapping("/consumer/getPaymentById.do/{id}")
    public CommonResult doGetPaymentById(@PathVariable("id") Long id){
        return paymentFeignServer.doGetPaymentById(id);
    }

    @GetMapping("/consumer/payment/timeout")
    public String getTimeout(){
        return paymentFeignServer.getTimeout();
    }
}
