package com.dotin.service;

import com.dotin.beans.AccountDTO;
import com.dotin.beans.PaymentDTO;
import com.dotin.exception.LowDepositAmount;
import com.dotin.model.LoadFile;
import com.dotin.model.SaveFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PaymentThread implements Runnable {

    LoadFile loadfile = new LoadFile();
    SaveFile saveFile = new SaveFile();
    MakePayment makePayment = new MakePayment();

    private String deptorDepositNumbert;
    private String creditorDepositNumber;
    private BigDecimal amount;


    List<AccountDTO> accountlist = loadfile.getAccountList();
    List<String> accountListString = new ArrayList<String>();

    public PaymentThread(String deptorDepositNumbert, String creditorDepositNumber, BigDecimal amount) throws IOException {
        this.deptorDepositNumbert = deptorDepositNumbert;
        this.creditorDepositNumber = creditorDepositNumber;
        this.amount = amount;
    }


    public List<AccountDTO> getAccountlist() {
        return accountlist;
    }

    @Override
    public void run() {


        try {
            makePayment.setAccountList(accountlist);
            makePayment.doPay(deptorDepositNumbert
                    , creditorDepositNumber,
                    amount);

            if (makePayment.isPayDone()) {

                accountlist=makePayment.getAccountList();

                for (AccountDTO accounts : accountlist) {
                    accountListString.add(accounts.toString());
                }
                saveFile.setSaveFile("account", accountListString);
                saveFile.setSaveFileWithAppend("transaction", makePayment.getTransactionString());

            }

        } catch (LowDepositAmount | IOException lowDepositAmount) {
            lowDepositAmount.printStackTrace();
        }

    }


}
