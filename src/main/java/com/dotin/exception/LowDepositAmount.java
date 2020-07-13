package com.dotin.exception;

import java.io.IOException;

public class LowDepositAmount extends IOException {


    public LowDepositAmount() {

        super("!!!!!!Attention!!!!!!\n|||||***********>...Low amount for your transaction...<**********|||||" + "\n");
    }

    public LowDepositAmount(String deptorDepositNumber, String creditordepositNumber) {
        super("Low amount on deposit number: " + deptorDepositNumber + " for pay to " + creditordepositNumber + "\n");
    }


}
