package com.dotin.service;

import com.dotin.beans.AccountDTO;
import com.dotin.beans.PaymentDTO;
import com.dotin.beans.TransactionDTO;
import com.dotin.exception.LowDepositAmount;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class MakePayment {

    private List<AccountDTO> accountList;
    private List<String> payListString = new ArrayList<String>();
    private List<String> transactionListString = new ArrayList<String>();
    private List<String> accountListString = new ArrayList<String>();
    private boolean payDone = false;

    public boolean isPayDone() {
        return payDone;
    }

    public void setPayDone(boolean payDone) {
        this.payDone = payDone;
    }

    public List<String> getPayListString() {
        return payListString;
    }

    public void setPayListString(List<String> payListString) {
        this.payListString = payListString;
    }

    public List<String> getTransactionListString() {
        return transactionListString;
    }

    public void setTransactionListString(List<String> transactionListString) {
        this.transactionListString = transactionListString;
    }

    public List<String> getAccountListString() {
        return accountListString;
    }

    public void setAccountListString(List<String> accountListString) {
        this.accountListString = accountListString;
    }

    public List<AccountDTO> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<AccountDTO> accountList) {
        this.accountList = accountList;
    }


    public void doPay(String deptorDepositNumber, String creditorDepositNumber, BigDecimal amountPay) throws LowDepositAmount {


        String inputDepositNumber = deptorDepositNumber;
        Optional<AccountDTO> first = accountList.stream()
                .filter(x -> Objects.equals(inputDepositNumber, x.getDepositNumber()))
                .findFirst();

        //find deposit number that you want to pay
        if (first.isPresent()) {

            //pay(deptor)
            PaymentDTO newPay = new PaymentDTO();
            newPay.setDeptorOrCreditor("debtor");
            newPay.setDepositNumber(deptorDepositNumber);
            newPay.setAmount(first.get().getAmount());

            //pay(creditor)
            PaymentDTO newPay2 = new PaymentDTO();
            String inputDepositNumber2 = creditorDepositNumber;
            Optional<AccountDTO> first2 = accountList.stream()
                    .filter(x -> Objects.equals(inputDepositNumber2, x.getDepositNumber()))
                    .findFirst();

            //find account(creditor) in account
            if (first2.isPresent()) {

                //System.out.println("found");
                newPay2.setDeptorOrCreditor("creditor");
                newPay2.setDepositNumber(inputDepositNumber2);
                BigDecimal bigDecimal;
                bigDecimal = amountPay;

                if (!(accountList.get(accountList.indexOf(first.get())).getAmount().compareTo(bigDecimal) < 0)) {
                    accountList.get(accountList.indexOf(first.get())).setAmount(first.get().getAmount().subtract(bigDecimal));
                    accountList.get(accountList.indexOf(first2.get())).setAmount(first2.get().getAmount().add(bigDecimal));
                    newPay2.setAmount(bigDecimal);

                    //save deposit number that you want to pay(deptor) in to the pay list
                    payListString.add(newPay.toString());

                    //save transactions in to the transaction list list
                    TransactionDTO newTransaction = new TransactionDTO(first.get().getDepositNumber(), first2.get().getDepositNumber(), bigDecimal);
                    transactionListString.add(newTransaction.toString());

                    //save deposit number that give pay (creditor) in to the pay list
                    payListString.add(newPay2.toString());
                    payDone = true;
                } else {

                    throw new LowDepositAmount(deptorDepositNumber, creditorDepositNumber);
                }
            }
        }
    }
}
