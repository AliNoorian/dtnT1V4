package com.dotin.service;


import com.dotin.beans.AccountDTO;
import com.dotin.beans.TransactionDTO;
import com.dotin.exception.LowDepositAmount;
import com.dotin.model.SaveFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;


public class PaymentThread implements Runnable {


    private final String deptorDepositNumber;
    private final String creditorDepositNumber;
    private final BigDecimal amount;


    private final CountDownLatch latch;
    private boolean isNegativeDeptorAmount;


    public PaymentThread(String deptorDepositNumber
            , String creditorDepositNumber
            , BigDecimal amount
            , CountDownLatch latch) {
        this.deptorDepositNumber = deptorDepositNumber;
        this.creditorDepositNumber = creditorDepositNumber;
        this.amount = amount;
        this.latch = latch;
    }


    public synchronized void doThreadPay() throws IOException {
        List<String> fileContent = new ArrayList<>(Files.readAllLines(Paths.get(
                "Program Files\\account.txt"), StandardCharsets.UTF_8));

        for (int i = 0; i < fileContent.size(); i++) {
            if (fileContent.get(i).contains(deptorDepositNumber)) {
                AccountDTO deptorAccount = new AccountDTO();
                String[] accountString = (fileContent.get(i).split("\t"));
                deptorAccount.setDepositNumber(accountString[0]);
                BigDecimal bd = new BigDecimal(accountString[1]);
                deptorAccount.setAmount(bd.subtract(amount));
                if ((bd.compareTo(amount) < 0)) {
                    isNegativeDeptorAmount = true;
                }
                fileContent.set(i, deptorAccount.toString());
                break;
            }
        }
        for (int i = 0; i < fileContent.size(); i++) {
            if (fileContent.get(i).contains(creditorDepositNumber)) {
                AccountDTO creditorAccount = new AccountDTO();
                String[] accountString = (fileContent.get(i).split("\t"));
                creditorAccount.setDepositNumber(accountString[0]);
                BigDecimal bd = new BigDecimal(accountString[1]);
                creditorAccount.setAmount(bd.add(amount));
                fileContent.set(i, creditorAccount.toString());
                break;
            }
        }
        if (isNegativeDeptorAmount) {
            throw new LowDepositAmount(deptorDepositNumber, creditorDepositNumber);
        } else {
            Files.write(Paths.get(
                    "Program Files\\account.txt"),
                    fileContent, StandardCharsets.UTF_8);
            TransactionDTO newTransaction = new TransactionDTO(deptorDepositNumber, creditorDepositNumber, amount);
            String transactionString = newTransaction.toString();
            SaveFile saveFile = new SaveFile();
            saveFile.setSaveFileWithAppend("transaction", transactionString);
        }
        latch.countDown();
    }

    @Override
    public synchronized void run() {
        try {
            doThreadPay();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
