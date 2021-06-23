package com.study.springcloud.controller;

import com.study.springcloud.model.CommonResult;
import com.study.springcloud.model.Payment;
import com.study.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping("/create.do")
    public CommonResult doCreate(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("*****插入操作的返回结果" + result);
        if (result > 0){
            return new CommonResult(200,"插入操作成功，serverPort=" + serverPort,result);
        }else {
            return new CommonResult(444,"插入操作失败，serverPort=" + serverPort,null);
        }
    }

    @GetMapping("/getPaymentById.do/{id}")
    public CommonResult doGetPaymentById(@PathVariable("id") Long id){
        Payment result = paymentService.getPaymentById(id);
        log.info("*****插入操作的返回结果" + result);
        if (result != null){
            return new CommonResult(200,"查询操作成功，serverPort=" + serverPort,result);
        }else {
            return new CommonResult(444,"查询操作失败，serverPort=" + serverPort + "，id：" + id,null);
        }
    }

    @GetMapping("/consumer/discovery.do")
    public Object doDiscovery(){
        List<String> services = discoveryClient.getServices();
        for (String service:services) {
            log.info("*****service:" + service);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("001-SPRINGCLOUD-PROVIDER-PAYMENT");
        for (ServiceInstance instance:instances) {
            log.info(instance.getServiceId() + "\t" + instance.getHost() + "\t" + instance.getPort() + "\t"
                    + instance.getUri());
        }
        return this.discoveryClient;
    }

    @GetMapping(value = "/payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult<Payment> getPaymentGet(@PathVariable("id") Long id){
        Payment result = paymentService.getPaymentById(id);
        log.info("*****插入操作的返回结果" + result);
        if (result != null){
            return new CommonResult(200,"查询操作成功，serverPort=" + serverPort,result);
        }else {
            return new CommonResult(444,"查询操作失败，serverPort=" + serverPort + "，id：" + id,null);
        }
    }
}
