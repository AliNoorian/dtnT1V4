package com.dotin.service;

import com.dotin.beans.AccountDTO;
import com.dotin.beans.PaymentDTO;
import com.dotin.beans.TransactionDTO;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class PaymentThread implements Runnable {
    private static final Logger logger = Logger.getLogger(Main.class);

    private final CountDownLatch latch;
    private final int startLine;
    private final int endLine;
    private final List<PaymentDTO> paymentList;
    private boolean isFileUpdate = false;
    private final List<String> accountlist;

    public PaymentThread(List<String> accountlist, List<PaymentDTO> paymentList, int startLine, int endLine,
            CountDownLatch latch) {
        this.paymentList = paymentList;
        this.startLine = startLine;
        this.endLine = endLine;
        this.latch = latch;
        this.accountlist = accountlist;
    }

    public synchronized void doThreadPay() throws IOException {
        TransactionDTO newTransaction = new TransactionDTO();
        for (int i = startLine; i <= endLine; i++) {
            PaymentDTO payment = paymentList.get(i);

            if (payment.getDeptorOrCreditor().equals("deptor")) {
                AccountDTO deptorAccount = new AccountDTO();
                String[] accountString = (accountlist.get(0).split("\t"));
                deptorAccount.setDepositNumber(accountString[0]);
                BigDecimal bd = new BigDecimal(accountString[1]);
                deptorAccount.setAmount(bd.subtract(payment.getAmount()));
                accountlist.set(0, deptorAccount.toString());
                isFileUpdate = true;

            }
            if (payment.getDeptorOrCreditor().equals("creditor")) {
                for (int j = 0; j < accountlist.size(); j++) {
                    if (accountlist.get(j).contains(payment.getDepositNumber())) {
                        AccountDTO creditorAccount = new AccountDTO();
                        String[] accountString2 = (accountlist.get(j).split("\t"));
                        creditorAccount.setDepositNumber(accountString2[0]);
                        BigDecimal bd2 = new BigDecimal(accountString2[1]);
                        creditorAccount.setAmount(bd2.add(payment.getAmount()));
                        accountlist.set(j, creditorAccount.toString());
                        newTransaction.setCreditorDepositNumber(payment.getDepositNumber());
                        newTransaction.setDebtorDepositNumber("1.10.100.1");
                        newTransaction.setAmount(payment.getAmount());
                        isFileUpdate = true;
                    }
                }
            }
            if (isFileUpdate) {
                if (!(newTransaction.toString().contains("null\tnull\tnull"))) {
                    Files.write(Paths.get("Program Files\\transaction.txt"), newTransaction.toString().getBytes(),
                            StandardOpenOption.APPEND);
                }
            }
        }
        if (isFileUpdate) {

            Files.write(Paths.get("Program Files\\account.txt"), accountlist, StandardCharsets.UTF_8);

        }
    }

    @Override
    public void run() {
        try {
            doThreadPay();
            latch.countDown();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

}
