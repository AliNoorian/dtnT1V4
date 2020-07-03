package com.dotin.service;

import com.dotin.beans.AccountDTO;
import com.dotin.exception.LowDepositAmount;
import com.dotin.model.LoadFile;
import com.dotin.model.SaveFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class PaymentThread implements Runnable {

    LoadFile loadfile = new LoadFile();
    SaveFile saveFile = new SaveFile();
    List<AccountDTO> accountList2 =loadfile.getAccountList();
    private List<AccountDTO> accountlist=accountList2;
    MakePayment makePayment = new MakePayment(accountlist);

    private String deptorDepositNumbert;
    private String creditorDepositNumber;
    private BigDecimal amount;
    private CountDownLatch latch;


    List<String> accountListString = new ArrayList<String>();

    public PaymentThread(List<AccountDTO> accountlist,String deptorDepositNumbert
            , String creditorDepositNumber
            , BigDecimal amount
            ,CountDownLatch latch) throws IOException {
        this.accountlist=accountlist;
        this.deptorDepositNumbert = deptorDepositNumbert;
        this.creditorDepositNumber = creditorDepositNumber;
        this.amount = amount;
        this.latch = latch;
    }



    @Override
    public void run() {


        try {

            makePayment.doPay(accountlist,deptorDepositNumbert
                    , creditorDepositNumber,
                    amount);

            if (makePayment.isPayDone()) {

                accountlist = makePayment.getAccountList();
                saveFile.setSaveFile("account", makePayment.getAccountListStrings());
                saveFile.setSaveFileWithAppend("transaction", makePayment.getTransactionString());

            }




        } catch (IOException | LowDepositAmount lowDepositAmount) {
            lowDepositAmount.printStackTrace();
        } finally {
            latch.countDown();
        }

    }


}
