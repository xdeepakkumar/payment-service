package com.gigaforce.paymentservice.service;

import com.gigaforce.paymentservice.entity.TransactionDetails;
import com.gigaforce.paymentservice.enums.PaymentMode;
import com.gigaforce.paymentservice.model.PaymentRequest;
import com.gigaforce.paymentservice.model.PaymentResponse;
import com.gigaforce.paymentservice.repository.TransactionDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;

    @Override
    public Long doPayment (PaymentRequest paymentRequest) {
        log.info("Recording payment details: {}", paymentRequest);
        TransactionDetails transactionDetails = TransactionDetails.builder()
                .amount(paymentRequest.getAmount())
                .paymentDate(Instant.now())
                .paymentMode(paymentRequest.getPaymentMode().name())
                .referenceNumber(paymentRequest.getReferenceNumber())
                .orderId(paymentRequest.getOrderId())
                .paymentStatus("SUCCESS")
                .build();
        transactionDetailsRepository.save(transactionDetails);
        log.info("Transaction completed with Id : {}",transactionDetails.getId());
        return transactionDetails.getId();
    }


    @Override
    public PaymentResponse getPaymentDetailsByOrderId (Long orderId) {
        log.info("Getting the payment details for orderId : {}", orderId);
        TransactionDetails transactionDetails = transactionDetailsRepository.findByOrderId(orderId);
        return PaymentResponse.builder()
                .paymentId(transactionDetails.getId())
                .paymentMode(PaymentMode.valueOf(transactionDetails.getPaymentMode()))
                .paymentDate(transactionDetails.getPaymentDate())
                .orderId(transactionDetails.getOrderId())
                .amount(transactionDetails.getAmount())
                .status(transactionDetails.getPaymentStatus())
                .build();
    }
}
