package com.dotin.service;

import com.dotin.beans.AccountDTO;
import com.dotin.beans.PaymentDTO;
import com.dotin.model.LoadFile;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws Exception {

        LoadFile loadfile = new LoadFile();
        List<PaymentDTO> payList = loadfile.getPaymentList();

        int coreCpuCout = Runtime.getRuntime().availableProcessors();
        ExecutorService service = Executors.newFixedThreadPool(coreCpuCout);

        //get size of task for thread
        int batchSize = (int) Math.ceil(payList.size() / coreCpuCout);
        for (int i = 0; i < payList.size(); i++) {
            if (payList.size() > coreCpuCout) {
                for (int j = 0; j < batchSize; j++) {
                    if (payList.size() > batchSize * i + j) {
                        if (payList.get(batchSize * i + j).getDeptorOrCreditor().equals("creditor")) {

                            service.execute(new PaymentThread("1.10.100.1"
                                    , payList.get(batchSize * i + j).getDepositNumber()
                                    , payList.get(batchSize * i + j).getAmount()));
                        }
                    }
                }
            } else {

                if (payList.get(i).getDeptorOrCreditor().equals("creditor")) {

                    service.execute(new PaymentThread("1.10.100.1"
                            , payList.get(i).getDepositNumber()
                            , payList.get(i).getAmount()));

                }
            }
        }
        service.shutdown();




    }
}
