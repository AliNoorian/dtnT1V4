package com.dotin.service;

import com.dotin.beans.AccountDTO;
import com.dotin.beans.TransactionDTO;
import com.dotin.exception.LowDepositAmount;
import com.dotin.model.LoadFile;
import com.dotin.model.SaveFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MakePayment {


    LoadFile loadfile = new LoadFile();


    public MakePayment() {

    }


    public synchronized void doPay(String deptorDepositNumber, String creditorDepositNumber, BigDecimal amountPay) throws IOException {

        List<AccountDTO> accountList = loadfile.getAccountList();
        String fileContents = loadfile.readAccountFile();
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

                if (!(accountList.get(accountList.indexOf(first.get())).getAmount().compareTo(amountPay) < 0)) {

                    AccountDTO updateAccountDebtor;
                    updateAccountDebtor = accountList.get(accountList.indexOf(first.get()));
                    updateAccountDebtor.setAmount(updateAccountDebtor.getAmount().subtract(amountPay));
                    fileContents.replaceAll(
                            accountList.get(accountList.indexOf(first.get())).toString()
                            , updateAccountDebtor.toString());
                    System.out.println(updateAccountDebtor.toString());
                    AccountDTO updateAccountCreditor;
                    updateAccountCreditor = accountList.get(accountList.indexOf(first2.get()));
                    updateAccountCreditor.setAmount(updateAccountCreditor.getAmount().add(amountPay));
                    fileContents.replaceAll(
                            accountList.get(accountList.indexOf(first2.get())).toString()
                            , updateAccountCreditor.toString());

                    System.out.println(updateAccountCreditor.toString());

                    TransactionDTO newTransaction = new TransactionDTO(first.get().getDepositNumber(), first2.get().getDepositNumber(), amountPay);
                    String transactionString = newTransaction.toString();


                    SaveFile saveFile = new SaveFile();
                    saveFile.writeAccountFile(fileContents);
                    saveFile.setSaveFileWithAppend("transaction", transactionString);
                } else {

                    throw new LowDepositAmount(deptorDepositNumber, creditorDepositNumber);
                }
            }
        }

    }


}
