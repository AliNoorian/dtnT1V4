package com.dotin.service;


import com.dotin.beans.AccountDTO;
import com.dotin.beans.PaymentDTO;
import com.dotin.beans.TransactionDTO;
import com.dotin.model.SaveFile;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CountDownLatch;


public class PaymentThread implements Runnable {
    public static final Logger logger = Logger.getLogger(Main.class);


    private final CountDownLatch latch;
    private final int startLine;
    private final int endLine;
    private final List<PaymentDTO> paymentList;
    private final SaveFile saveFile;
    private final List<String> fileContent;
    private boolean isFileUpdate = false;


    public PaymentThread(List<String> fileContent
            , SaveFile saveFile
            , List<PaymentDTO> paymentList
            , int startLine
            , int endLine
            , CountDownLatch latch) {
        this.paymentList = paymentList;
        this.startLine = startLine;
        this.endLine = endLine;
        this.latch = latch;
        this.saveFile = saveFile;
        this.fileContent = fileContent;
    }


    public synchronized void doThreadPay() throws IOException {
        TransactionDTO newTransaction = new TransactionDTO();
        for (int i = startLine; i <= endLine; i++) {
            PaymentDTO payment = paymentList.get(i);

            if (payment.getDeptorOrCreditor().equals("deptor")) {
                AccountDTO deptorAccount = new AccountDTO();
                String[] accountString = (fileContent.get(0).split("\t"));
                deptorAccount.setDepositNumber(accountString[0]);
                BigDecimal bd = new BigDecimal(accountString[1]);
                deptorAccount.setAmount(bd.subtract(payment.getAmount()));
                fileContent.set(0, deptorAccount.toString());
                isFileUpdate = true;


            }
            if (payment.getDeptorOrCreditor().equals("creditor")) {
                for (int j = 0; j < fileContent.size(); j++) {
                    if (fileContent.get(j).contains(payment.getDepositNumber())) {
                        AccountDTO creditorAccount = new AccountDTO();
                        String[] accountString2 = (fileContent.get(j).split("\t"));
                        creditorAccount.setDepositNumber(accountString2[0]);
                        BigDecimal bd2 = new BigDecimal(accountString2[1]);
                        creditorAccount.setAmount(bd2.add(payment.getAmount()));
                        fileContent.set(j, creditorAccount.toString());
                        newTransaction.setCreditorDepositNumber(payment.getDepositNumber());
                        newTransaction.setDebtorDepositNumber("1.10.100.1");
                        newTransaction.setAmount(payment.getAmount());
                        isFileUpdate = true;
                    }
                }
            }
            if (isFileUpdate) {
                if (!newTransaction.toString().equals("null\tnull\tnull")) {
                    String transactionString = newTransaction.toString();
                    saveFile.setSaveFileWithAppend("transaction", transactionString);
                }
            }
        }
        if (isFileUpdate) {
            Files.write(Paths.get(
                    "Program Files\\account.txt"),
                    fileContent, StandardCharsets.UTF_8);
        }

    }


    @Override
    public void run() {
        try {
            doThreadPay();
            latch.countDown();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            System.err.print(e.getMessage());
        }

    }

}
