package com.dotin.service;

import com.dotin.beans.AccountDTO;
import com.dotin.model.SaveFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CountDownLatch;


public class PaymentThread implements Runnable {

    SaveFile saveFile = new SaveFile();


    private final String deptorDepositNumbert;
    private final String creditorDepositNumber;
    private final BigDecimal amount;
    private final CountDownLatch latch;
    List<AccountDTO> accountList;


    public PaymentThread(List<AccountDTO> accountList, String deptorDepositNumbert
            , String creditorDepositNumber
            , BigDecimal amount
            , CountDownLatch latch) {
        this.deptorDepositNumbert = deptorDepositNumbert;
        this.creditorDepositNumber = creditorDepositNumber;
        this.amount = amount;
        this.latch = latch;
        this.accountList = accountList;
    }


    @Override
    public void run() {
        MakePayment makePayment = new MakePayment();


        try {

            makePayment.doPay(accountList, deptorDepositNumbert
                    , creditorDepositNumber,
                    amount);

            if (makePayment.isPayCanDone()) {

                saveFile.setSaveFileWithAppend("transaction", makePayment.getTransactionString());

            }


        } catch (IOException lowDepositAmount) {
            lowDepositAmount.printStackTrace();
        } finally {

            latch.countDown();
        }
    }
}
