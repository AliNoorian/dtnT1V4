package com.dotin.exception;

public class LowDepositAmount extends Exception {


    public LowDepositAmount() {
    }

    public LowDepositAmount(String deptorDepositNumber,String creditordepositNumber) {
        super("Low amount on deposit number: " + deptorDepositNumber +" for pay to "+creditordepositNumber +"\n");
    }


}
