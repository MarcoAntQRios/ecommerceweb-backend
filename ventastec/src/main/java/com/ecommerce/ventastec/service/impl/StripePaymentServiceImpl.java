package com.ecommerce.ventastec.service.impl;

import com.ecommerce.ventastec.exception.StripePaymentException;
import com.ecommerce.ventastec.service.StripePaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import org.springframework.stereotype.Service;


@Service
public class StripePaymentServiceImpl implements StripePaymentService {


    @Override
    public String createCheckoutSession(Long amount, Long ventaId, String currency, String description) {
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                // URL a donde redirige Stripe tras pago exitoso
                //.setSuccessUrl("http://localhost:4200/mis-compras")
                .setSuccessUrl("http://localhost:4200/checkout-success?ventaId=" + ventaId)
                // URL si el usuario cancela
                .setCancelUrl("http://localhost:4200/checkout-cancel?ventaId=" + ventaId)
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency(currency)
                                                .setUnitAmount(amount)
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName(description)
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();

        Session session = null;
        try {
            session = Session.create(params);
        } catch (StripeException e) {
            throw new StripePaymentException("Error al crear la sesión de pago");
        }

        // Devuelve la URL de la página de pago de Stripe
        return session.getUrl();
    }

}
