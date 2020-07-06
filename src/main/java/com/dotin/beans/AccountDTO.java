package com.dotin.beans;

import java.io.Serializable;
import java.math.BigDecimal;

public class AccountDTO implements Serializable {
    private String DepositNumber;
    private BigDecimal amount;


    public AccountDTO() {

    }



    public synchronized String getDepositNumber() {

        return DepositNumber;
    }

    public synchronized BigDecimal getAmount() {
        return amount;
    }


    public synchronized void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public synchronized void setDepositNumber(String DepositNumber) {
        this.DepositNumber = DepositNumber;
    }

    @Override
    public synchronized String toString() {
        return DepositNumber + "\t" + amount ;
    }

}
