package com.hclSoftware.learningSpringBoot;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name="payment.provider", havingValue="stripe")
public class BeanStripePaymentService implements PaymentService{
    @Override
    public String pay(){
        String payment = "Stripe";
        System.out.println("Payment Started: " + payment);
        return payment;
    }
}
