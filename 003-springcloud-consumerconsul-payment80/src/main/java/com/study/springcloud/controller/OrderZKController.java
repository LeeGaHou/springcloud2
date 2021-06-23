package com.study.springcloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import javax.annotation.Resource;

@RestController
public class OrderZKController {

    private static final String PAYMENT_ZK_URL = "http://springcloud-providerconsul-payment8084";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/payment/consul")
    public String paymentZK(){
        return "服务消费者--->" + restTemplate.getForObject(PAYMENT_ZK_URL + "/payment/consul", String.class);
    }
}
