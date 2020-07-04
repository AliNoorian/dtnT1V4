package com.dotin.service;

import com.dotin.beans.AccountDTO;
import com.dotin.beans.PaymentDTO;
import com.dotin.exception.CanNotOpenFile;
import com.dotin.model.LoadFile;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

public class Main {
    public static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) throws CanNotOpenFile {

        try {
            LoadFile loadfile = new LoadFile();

            List<PaymentDTO> payList = loadfile.getPaymentList();
            List<AccountDTO> accountLists = loadfile.getAccountList();
            List<AccountDTO> synchronizedAccountList = Collections.synchronizedList(accountLists);

            int coreCpuCout = Runtime.getRuntime().availableProcessors();
            ExecutorService service = Executors.newFixedThreadPool(coreCpuCout);

            //get size of task for thread
            int batchSize = ((int) Math.ceil(payList.size() / coreCpuCout));
            CountDownLatch minlatch = new CountDownLatch(payList.size() - 1);
            CountDownLatch batchlatch = new CountDownLatch(batchSize);

            for (int i = 0; i < payList.size(); i++) {
                if (payList.size() > coreCpuCout) {
                    for (int j = 0; j < batchSize; j++) {
                        if (payList.size() > batchSize * i + j) {
                            if (payList.get(batchSize * i + j).getDeptorOrCreditor().equals("creditor")) {

                                service.execute(new PaymentThread(synchronizedAccountList
                                        , payList.get(0).getDepositNumber()
                                        , payList.get(batchSize * i + j).getDepositNumber()
                                        , payList.get(batchSize * i + j).getAmount(), batchlatch));
                            }
                        }
                    }
                } else {

                    if (payList.get(i).getDeptorOrCreditor().equals("creditor")) {

                        service.execute(new PaymentThread(synchronizedAccountList
                                , payList.get(0).getDepositNumber()
                                , payList.get(i).getDepositNumber()
                                , payList.get(i).getAmount(), minlatch));

                    }
                }

            }

            service.shutdown();


        } catch (Exception e) {

            logger.error(e.getMessage(), e);
            System.err.print(e.getMessage());
            throw new CanNotOpenFile();
        }
    }
}