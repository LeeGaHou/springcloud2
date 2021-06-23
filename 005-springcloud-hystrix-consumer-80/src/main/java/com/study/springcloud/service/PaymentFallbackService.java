package com.study.springcloud.service;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
public class PaymentFallbackService implements PaymentFeignService{
    @Override
    public @ResponseBody String payment_OK(Long id) {
        return "-----服务调用失败payment_OK，提示来自：cloud-consumer-feign-order80";
    }

    @Override
    public @ResponseBody String payment_Timeout(Long id) {
        return "-----服务调用失败payment_Timeout，提示来自：cloud-consumer-feign-order80";
    }
}
