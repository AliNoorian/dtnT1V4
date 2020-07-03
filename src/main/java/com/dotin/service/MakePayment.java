package com.dotin.service;

import com.dotin.beans.AccountDTO;
import com.dotin.beans.PaymentDTO;
import com.dotin.beans.TransactionDTO;
import com.dotin.exception.LowDepositAmount;
import com.dotin.model.SaveFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class MakePayment {




    private List<String>accountListStrings= new ArrayList<String>();
    private List<AccountDTO> accountList;
    private String transactionString;
    private String updateDeptorAccountString;
    private String updateCreditorAccountString;
    private boolean payDone = false;




    public MakePayment(List<AccountDTO> accountList) throws IOException {

        this.accountList = accountList;

    }

    public List<String> getAccountListStrings() {
        return accountListStrings;
    }

    public String getUpdateDeptorAccountString() {
        return updateDeptorAccountString;
    }

    public String getUpdateCreditorAccountString() {
        return updateCreditorAccountString;
    }

    public String getTransactionString() {
        return transactionString;
    }

    public boolean isPayDone() {
        return payDone;
    }

    public List<AccountDTO> getAccountList() {
        return accountList;
    }




    public void doPay(List<AccountDTO> accountList, String deptorDepositNumber, String creditorDepositNumber, BigDecimal amountPay) throws LowDepositAmount, IOException {

        String inputDepositNumber = deptorDepositNumber;

        Optional<AccountDTO> first = accountList.stream()
                .filter(x -> Objects.equals(inputDepositNumber, x.getDepositNumber()))
                .findFirst();

        //find deposit number that you want to pay
        if (first.isPresent()) {

            //pay(deptor)
            PaymentDTO newPay = new PaymentDTO();
            newPay.setDeptorOrCreditor("debtor");
            newPay.setDepositNumber(inputDepositNumber);
            newPay.setAmount(first.get().getAmount());

            //pay(creditor)
            String inputDepositNumber2 = creditorDepositNumber;
            Optional<AccountDTO> first2 = accountList.stream()
                    .filter(x -> Objects.equals(creditorDepositNumber, x.getDepositNumber()))
                    .findFirst();

            //find account(creditor) in account
            if (first2.isPresent()) {
                //System.out.println("found");
                BigDecimal bigDecimal;
                bigDecimal = new BigDecimal(amountPay.toString());

                if (!(accountList.get(accountList.indexOf(first.get())).getAmount().compareTo(bigDecimal) < 0)) {

                    accountList.get(accountList.indexOf(first.get())).setAmount(first.get().getAmount().subtract(bigDecimal));
                    accountList.get(accountList.indexOf(first2.get())).setAmount(first2.get().getAmount().add(bigDecimal));
                    TransactionDTO newTransaction = new TransactionDTO(first.get().getDepositNumber(), first2.get().getDepositNumber(), bigDecimal);
                    transactionString = newTransaction.toString();

                    AccountDTO updateAccount = new AccountDTO((first.get().getDepositNumber()), first.get().getAmount());
                    updateDeptorAccountString = updateAccount.toString();
                    AccountDTO updateAccount2 = new AccountDTO((first2.get().getDepositNumber()), first2.get().getAmount());
                    updateCreditorAccountString = updateAccount2.toString();

                    for (AccountDTO accounts : accountList) {
                        accountListStrings.add(accounts.toString());

                    }


                    payDone = true;
                } else {

                    throw new LowDepositAmount(deptorDepositNumber, creditorDepositNumber);
                }
            }
        }
    }


}
