package com.hclSoftware.learningSpringBoot;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name="payment.provider", havingValue="razorpay")
public class BeanRazorPayPaymentService implements PaymentService{
    @Override
    public String pay(){
        String payment = "RazorPay";
        System.out.println("Payment Started: " + payment);
        return payment;
    }
}
