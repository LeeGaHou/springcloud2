package com.study.springcloud.service;

import com.study.springcloud.model.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "001-springcloud-provider-payment")
public interface PaymentFeignServer {
    @GetMapping("/getPaymentById.do/{id}")
    CommonResult doGetPaymentById(@PathVariable("id") Long id);

    @GetMapping(value = "/payment/timeout")
    String getTimeout();
}
