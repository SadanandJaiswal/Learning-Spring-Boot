package com.hclSoftware.learningSpringBoot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LearningSpringBootApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LearningSpringBootApplication.class, args);
	}

    //  We are using new keyword here (without bean)
    // Here tight copuling is done, our application is fully dependent on razorpayservice
    //    private BeanRazorPayPaymentService paymentService = new BeanRazorPayPaymentService();

    /*
    // Using Bean
    // Without using new keyword : still object will be created for this - (dependency injection)
    // 1) dependency injection
    private final BeanRazorPayPaymentService paymentService;

    // Dependency injection will inject the bean (dependency)
    public LearningSpringBootApplication(BeanRazorPayPaymentService paymentService){
        this.paymentService = paymentService;
    }
    */

    /*
    // 2) bean using autowired
    @Autowired
    private BeanRazorPayPaymentService paymentService;
    */

    // Multiple Bean
    private final PaymentService paymentService;
    public LearningSpringBootApplication(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    // This func will run after spring boot is ready to run
    @Override
    public void run(String... args) throws Exception{
        String payment = paymentService.pay();
        System.out.println("Payment done: " + payment);
    }
}
