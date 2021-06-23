package com.study.springcloud.controller;

import com.study.springcloud.lb.LoadBalancer;
import com.study.springcloud.model.CommonResult;
import com.study.springcloud.model.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class OrderController {

    //private static final String PAYMENT_URL = "http://127.0.0.1:8081";
    /**
     * Ribbon和Eureka整合后服务消费者可以直接通过注册中心中的服务提供者服务名来调用服务，并且有负载功能
     * 无需再关心地址和端口号
     */
    private static final String PAYMENT_URL = "http://001-SPRINGCLOUD-PROVIDER-PAYMENT";

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private LoadBalancer loadBalancer;

    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping("/consumer/create.do")
    public CommonResult<Payment> doCreate(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL + "/create.do",payment,CommonResult.class);
    }

    /**
     * getForObject方法返回对象为响应体中数据转化成的对象，基本上可以理解为Json
     */
    @GetMapping("/consumer/getPaymentById.do/{id}")
    public CommonResult<Payment> doGetPaymentById(@PathVariable("id") Long id){
        return restTemplate.getForObject(PAYMENT_URL + "/getPaymentById.do/" + id,CommonResult.class);
    }

    /**
     * getForEntity方法返回对象为ResponseEntity对象，包含了响应中的一些重要信息，比如响应头、响应状态码、响应体等
     */
    @GetMapping("/consumer/entity/getPaymentById.do/{id}")
    public CommonResult<Payment> doGetPaymentById2(@PathVariable("id") Long id){
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/getPaymentById.do/" + id, CommonResult.class);
        if (entity.getStatusCode().is2xxSuccessful()){
            log.info(entity.getStatusCode() + "/t");
            log.info(entity.getHeaders() + "/t");
            log.info(entity.getStatusCodeValue() + "/t");
            return entity.getBody();
        }else {
            return new CommonResult(444,"查询操作失败，id：" + id,null);
        }
    }

    @GetMapping("/consumer/payment/lb")
    public String doLB(){
        List<ServiceInstance> instances = discoveryClient.getInstances("001-SPRINGCLOUD-PROVIDER-PAYMENT");
        if (instances == null || instances.size() <= 0){
            return "操作失败！";
        }
        ServiceInstance instance = loadBalancer.instances(instances);
        return restTemplate.getForObject(instance.getUri() + "/payment/lb",String.class);
    }
}
