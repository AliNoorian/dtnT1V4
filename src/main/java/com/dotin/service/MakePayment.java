package com.dotin.service;

import com.dotin.beans.AccountDTO;
import com.dotin.beans.TransactionDTO;
import com.dotin.exception.LowDepositAmount;
import com.dotin.model.SaveFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MakePayment {


    private final List<String> accountListStrings = new ArrayList<>();
    private String transactionString;







    private boolean payCanDone = false;


    public MakePayment() {

    }

    public String getTransactionString() {
        return transactionString;
    }

    public boolean isPayCanDone() {
        return payCanDone;
    }



    public synchronized void doPay(List<AccountDTO> accountList, String deptorDepositNumber, String creditorDepositNumber, BigDecimal amountPay) throws IOException {

        Optional<AccountDTO> first = accountList.stream()
                .filter(x -> Objects.equals(deptorDepositNumber, x.getDepositNumber()))
                .findFirst();

        //find deposit number that you want to pay
        if (first.isPresent()) {


            //find account(creditor) in account
            Optional<AccountDTO> first2 = accountList.stream()
                    .filter(x -> Objects.equals(creditorDepositNumber, x.getDepositNumber()))
                    .findFirst();


            if (first2.isPresent()) {
                //System.out.println("found");
                if (!(accountList.get(accountList.indexOf(first.get())).getAmount().compareTo(amountPay) < 0)) {
                    accountList.get(accountList.indexOf(first.get())).setAmount(first.get().getAmount().subtract(amountPay));
                    accountList.get(accountList.indexOf(first2.get())).setAmount(first2.get().getAmount().add(amountPay));

                    TransactionDTO newTransaction = new TransactionDTO(first.get().getDepositNumber(), first2.get().getDepositNumber(), amountPay);
                    transactionString = newTransaction.toString();

                    for (AccountDTO accounts : accountList) {
                        accountListStrings.add(accounts.toString()+"\n");
                    }
                    SaveFile saveFile = new SaveFile();
                    saveFile.setSaveFile("account",accountListStrings);

                    payCanDone = true;
                } else {

                    throw new LowDepositAmount(deptorDepositNumber, creditorDepositNumber);
                }
            }
        }

    }



}
