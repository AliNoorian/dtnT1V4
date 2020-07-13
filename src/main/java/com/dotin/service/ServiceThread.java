package com.dotin.service;

import com.dotin.beans.PaymentDTO;
import com.dotin.model.LoadFile;
import com.dotin.model.SaveFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class ServiceThread {


    LoadFile loadfile = new LoadFile();
    List<PaymentDTO> payList = loadfile.getPaymentList();

    public ServiceThread() throws IOException {
    }

    public CountDownLatch threadExecutor() throws IOException {

        SaveFile saveFile = new SaveFile();

        int coreCpuCout = Runtime.getRuntime().availableProcessors();
        AtomicReference<ExecutorService> service = new AtomicReference<>(Executors.newFixedThreadPool(coreCpuCout));

        //get size of task for thread
        int totalPaymentLines = payList.size();
        int payTaskLineBatch = 100;
        int taskCount = (int) Math.ceil(totalPaymentLines / payTaskLineBatch);
        CountDownLatch latch = new CountDownLatch(taskCount);
        List<String> fileContent = new ArrayList<>(Files.readAllLines(Paths.get(
                "Program Files\\account.txt"), StandardCharsets.UTF_8));

        int startLine = 0;
        int endLine;
        if (payList.size() > 100) {
            endLine = 99;
        } else {
            endLine = payList.size() - 1;
        }

        int lastIndex = endLine;


        for (int i = 0; i < taskCount; i++) {

            Runnable task = new PaymentThread(fileContent,
                    saveFile, payList,
                    startLine, endLine, latch);

            service.get().execute(task);

            startLine = lastIndex + 1;
            if (payList.size() > lastIndex + 100) {
                endLine = startLine + 99;
            } else {
                endLine = payList.size() - 1;
            }
            lastIndex = endLine;
        }
        service.get().shutdown();


        return latch;
    }




}
