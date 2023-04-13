package com.gigaforce.paymentservice.service;

import com.gigaforce.paymentservice.model.PaymentRequest;
import com.gigaforce.paymentservice.model.PaymentResponse;
import org.springframework.http.HttpStatus;

public interface PaymentService {
    Long doPayment (PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId (Long orderId);
}
