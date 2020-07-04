package com.dotin.beans;

import java.io.Serializable;
import java.math.BigDecimal;

public class AccountDTO implements Serializable {
    private String DepositNumber;
    private BigDecimal amount;


    public AccountDTO() {

    }



    public String getDepositNumber() {

        return DepositNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }


    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setDepositNumber(String DepositNumber) {
        this.DepositNumber = DepositNumber;
    }

    @Override
    public String toString() {
        return DepositNumber + "\t" + amount ;
    }

}
