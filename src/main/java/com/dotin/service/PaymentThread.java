package com.dotin.service;

import com.dotin.beans.AccountDTO;
import com.dotin.beans.PaymentDTO;
import com.dotin.exception.LowDepositAmount;
import com.dotin.model.SaveFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PaymentThread implements Runnable {

    private List<String> accountListString = new ArrayList<String>();
    private String transactionString;

    public String getTransactionString() {
        return transactionString;
    }

    public List<String> getAccountListString() {
        return accountListString;
    }

    public void setAccountListString(List<String> accountListString) {
        this.accountListString = accountListString;
    }

    private String deptorDepositNumbert;
    private String creditorDepositNumber;
    private BigDecimal amount;
    MakePayment makePayment = new MakePayment();

    public PaymentThread(String deptorDepositNumbert, String creditorDepositNumber, BigDecimal amount) {
        this.deptorDepositNumbert = deptorDepositNumbert;
        this.creditorDepositNumber = creditorDepositNumber;
        this.amount = amount;
    }


    @Override
    public void run() {

        try {
            MakePayment makePayment = new MakePayment();
            makePayment.doPay(deptorDepositNumbert
                    , creditorDepositNumber,
                    amount);

            if (makePayment.isPayDone()) {
                SaveFile saveFile = new SaveFile();
                saveFile.setSaveFile("account", makePayment.getAccountListString());
                saveFile.setSaveFileWithAppend("transaction", makePayment.getTransactionString());
            }
        } catch (LowDepositAmount | IOException lowDepositAmount) {
            lowDepositAmount.printStackTrace();
        }

    }
}
