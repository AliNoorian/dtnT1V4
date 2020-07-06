package com.dotin.beans;

import java.io.Serializable;
import java.math.BigDecimal;

public class PaymentDTO implements Serializable {
    private String deptorOrCreditor;
    private String depositNumber;
    private BigDecimal amount;


    public PaymentDTO() {

    }

    public synchronized String getDeptorOrCreditor() {
        return deptorOrCreditor;
    }

    public synchronized String getDepositNumber() {
        return depositNumber;
    }

    public synchronized BigDecimal getAmount() {
        return amount;
    }


    public synchronized void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public synchronized void setDepositNumber(String depositNumber) {
        this.depositNumber = depositNumber;
    }

    public synchronized void setDeptorOrCreditor(String deptorOrCreditor) {
        this.deptorOrCreditor = deptorOrCreditor;
    }


    @Override
    public synchronized String toString() {
        return deptorOrCreditor + "\t" + depositNumber + "\t" + amount ;
    }
}
