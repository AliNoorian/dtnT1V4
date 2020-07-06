package com.dotin.beans;

import java.io.Serializable;
import java.math.BigDecimal;

public class TransactionDTO implements Serializable {


    private final String debtorDepositNumber;
    private final String creditorDepositNumber;
    private final BigDecimal amount;


    public TransactionDTO(String debtorDepositNumber, String creditorDepositNumber, BigDecimal amount) {
        this.debtorDepositNumber = debtorDepositNumber;
        this.creditorDepositNumber = creditorDepositNumber;
        this.amount = amount;

    }


    @Override
    public  String toString() {
        return debtorDepositNumber + "\t" + creditorDepositNumber + "\t" + amount ;
    }
}
