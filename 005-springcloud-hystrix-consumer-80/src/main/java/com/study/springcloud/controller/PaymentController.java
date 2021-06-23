package com.study.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.study.springcloud.service.PaymentFeignService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
public class PaymentController {

    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping("/consumer/payment/ok/{id}")
    public String payment_OK(@PathVariable("id") Long id){
        return paymentFeignService.payment_OK(id);
    }

    //@HystrixCommand(fallbackMethod = "payment_Timeout_Fallback",
            //commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1500")})
    @GetMapping("/consumer/payment/timeout/{id}")
    public String payment_Timeout(@PathVariable("id") Long id){
        return paymentFeignService.payment_Timeout(id);
    }

    @HystrixCommand//加了@DefaultProperties属性注解，并且没有写具体方法名字，就用统一全局的
    @GetMapping("/consumer/payment/timeout2/{id}")
    public String payment_Timeout2(@PathVariable("id") Long id){
        return paymentFeignService.payment_Timeout(id);
    }

    public String payment_Timeout_Fallback(@PathVariable("id") Long id){
        return "线程池:"+Thread.currentThread().getName()+"  80访问服务出现系统繁忙或自身出错,id: "+id+"\t"+"o(╥﹏╥)o";
    }

    public String payment_Global_FallbackMethod(){
        return "Global异常处理信息，请稍后再试，/(ㄒoㄒ)/~~";
    }
}
