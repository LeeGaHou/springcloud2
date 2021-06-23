package com.study.springcloud.service;

import com.study.springcloud.model.Payment;

public interface PaymentService {
    int create(Payment payment);
    Payment getPaymentById(Long id);
}
