package com.study.springcloud.service;


public interface PaymentService {
    String payment_OK(Long id);

    String payment_Timeout(Long id);

    String paymentCircuitBreaker(Integer id);
}
