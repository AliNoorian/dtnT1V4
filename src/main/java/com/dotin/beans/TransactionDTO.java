package com.dotin.beans;

import java.io.Serializable;
import java.math.BigDecimal;

public class TransactionDTO implements Serializable {


    private String debtorDepositNumber;
    private String creditorDepositNumber;
    private BigDecimal amount;

    public void setDebtorDepositNumber(String debtorDepositNumber) {
        this.debtorDepositNumber = debtorDepositNumber;
    }

    public void setCreditorDepositNumber(String creditorDepositNumber) {
        this.creditorDepositNumber = creditorDepositNumber;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionDTO() {
    }

    public TransactionDTO(String debtorDepositNumber, String creditorDepositNumber, BigDecimal amount) {
        this.debtorDepositNumber = debtorDepositNumber;
        this.creditorDepositNumber = creditorDepositNumber;
        this.amount = amount;

    }


    @Override
    public String toString() {
        return debtorDepositNumber + "\t" + creditorDepositNumber + "\t" + amount + "\n";
    }
}
