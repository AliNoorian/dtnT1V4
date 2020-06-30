package com.dotin.service;

import com.dotin.beans.AccountDTO;
import com.dotin.exception.LowDepositAmount;
import com.dotin.model.LoadFile;
import com.dotin.model.SaveFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws Exception {

        Random rand = new Random();
        LoadFile loadfile = new LoadFile();
        List<AccountDTO> accountList = loadfile.getAccountList();
        List<String> payListString = new ArrayList<String>();
        List<String> transactionListString = new ArrayList<String>();
        List<String> accountListString = new ArrayList<String>();



        MakePayment makePayment = new MakePayment();
        makePayment.setAccountList(accountList);


        int payLoopLength = rand.nextInt(100);
        for (int j = 0; j <= payLoopLength; j++) {

            String creditorDepositNumber = "1.20.100." + (rand.nextInt(200));
            BigDecimal amountPay;
            amountPay = new BigDecimal(rand.nextInt(100));

            makePayment.doPay("1.10.100.1", creditorDepositNumber, amountPay);

        }


        if (makePayment.isPayDone()) {

            for (AccountDTO accounts : accountList) {
                accountListString.add(accounts.toString());
            }
            SaveFile saveFile = new SaveFile();
            saveFile.setSaveFile("account", accountListString);
            saveFile.setSaveFile("pay", makePayment.getPayListString());
            saveFile.setSaveFile("transaction", makePayment.getTransactionListString());
        }


    }
}
