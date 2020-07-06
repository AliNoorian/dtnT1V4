package com.dotin.service;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;


public class PaymentThread implements Runnable {


    private final String deptorDepositNumber;
    private final String creditorDepositNumber;
    private final BigDecimal amount;
    private final CountDownLatch latch;
    MakePayment makePayment = new MakePayment();


    public PaymentThread(String deptorDepositNumber
            , String creditorDepositNumber
            , BigDecimal amount
            , CountDownLatch latch) {
        this.deptorDepositNumber = deptorDepositNumber;
        this.creditorDepositNumber = creditorDepositNumber;
        this.amount = amount;
        this.latch = latch;
    }


    @Override
    public void run() {
        try {
            makePayment.doPay(deptorDepositNumber
                    , creditorDepositNumber,
                    amount);
        } catch (IOException lowDepositAmount) {
            lowDepositAmount.printStackTrace();
        } finally {
            latch.countDown();
        }
    }
}
