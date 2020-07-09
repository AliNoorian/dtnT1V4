package com.dotin.service;

import com.dotin.beans.PaymentDTO;
import com.dotin.exception.CanNotOpenFile;
import com.dotin.model.LoadFile;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) throws CanNotOpenFile {

        try {
            LoadFile loadfile = new LoadFile();
            if (loadfile.getAccountList().isEmpty()) {
                loadfile.getAccountList();
            }
            if (loadfile.getPaymentList().isEmpty()) {
                loadfile.getPaymentList();
            }
            List<PaymentDTO> payList = loadfile.getPaymentList();

            int coreCpuCout = Runtime.getRuntime().availableProcessors();
            ExecutorService service = Executors.newFixedThreadPool(coreCpuCout);

            //get size of task for thread
            int batchSize = ((int) Math.ceil(payList.size() / coreCpuCout));
            System.out.println("Proccess started!!!");
            for (int i = 0; i < payList.size(); i++) {
                if (payList.size() > coreCpuCout) {

                    for (int j = 0; j < batchSize; j++) {
                        if (payList.size() > batchSize * i + j) {
                            if (payList.get(batchSize * i + j).getDeptorOrCreditor().equals("creditor")) {


                                service.submit(new PaymentThread(payList.get(0).getDepositNumber()
                                        , payList.get(batchSize * i + j).getDepositNumber()
                                        , payList.get(batchSize * i + j).getAmount()));

                                service.awaitTermination(70, TimeUnit.MILLISECONDS);
                            }
                        }

                    }

                } else {

                    if (payList.get(i).getDeptorOrCreditor().equals("creditor")) {

                        service.submit(new PaymentThread(payList.get(0).getDepositNumber()
                                , payList.get(i).getDepositNumber()
                                , payList.get(i).getAmount()));

                        service.awaitTermination(70, TimeUnit.MILLISECONDS);
                    }
                }

            }

            service.shutdown();
            System.out.println("Proccess finished!!!");

        } catch (Exception e) {

            logger.error(e.getMessage(), e);
            System.err.print(e.getMessage());
            throw new CanNotOpenFile();
        }
    }
}