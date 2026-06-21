package com.ecommerce.ventastec.service;

public interface StripePaymentService {

    String createCheckoutSession(Long amount, Long ventaId, String currency, String description);

}
