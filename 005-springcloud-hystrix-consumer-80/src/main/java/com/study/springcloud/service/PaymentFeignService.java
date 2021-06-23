package com.study.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "005-SPRINGCLOUD-HYSTRIX-PROVIDER-8085",
            fallback = PaymentFallbackService.class)
public interface PaymentFeignService {
    @GetMapping("/payment/ok/{id}")
    String payment_OK(@PathVariable("id") Long id);

    @GetMapping("/payment/timeout/{id}")
    String payment_Timeout(@PathVariable("id") Long id);
}
