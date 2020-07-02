package com.dotin.model;

import com.dotin.beans.AccountDTO;
import com.dotin.beans.PaymentDTO;
import com.dotin.exception.CanNotSaveFile;
import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class LoadFile {

    public LoadFile() {

    }


    public List<AccountDTO> getAccountList() throws IOException {
        List<AccountDTO> accountList = new ArrayList<>();
        List<String> accountListString = new ArrayList<>();
        String[] accountString;
        Random rand = new Random();
        try {


            File fileDir = new File("account.txt");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), StandardCharsets.UTF_8));
            String str;
            while ((str = in.readLine()) != null) {
                AccountDTO newAccount = new AccountDTO();
                accountString = (str.split("\t"));
                newAccount.setDepositNumber(accountString[0]);
                BigDecimal bd = new BigDecimal(accountString[1]);
                newAccount.setAmount(bd);
                accountList.add(newAccount);
            }
            in.close();


            return accountList;


        } catch (Throwable t) {


            AccountDTO newAccount = new AccountDTO();
            AccountDTO newAccount2 = new AccountDTO();

            String randomDepositNumber1 = "1.10.100.1";
            newAccount2.setDepositNumber(randomDepositNumber1);
            BigDecimal bigDecimal2 = new BigDecimal((rand.nextInt(9000) + 1000));
            newAccount2.setAmount(bigDecimal2);
            accountList.add(newAccount2);
            accountListString.add(newAccount2.toString());

            int accountLoopLength = (rand.nextInt(100));
            for (int i = 0; i <= accountLoopLength; i++) {
                String randomDepositNumber2 = "1.20.100." + (rand.nextInt(200));
                Optional<AccountDTO> first = accountList.stream()
                        .filter(x -> {
                            return Objects.equals(randomDepositNumber2, x.getDepositNumber());
                        })
                        .findFirst();
                if (!(first.isPresent())) {
                    newAccount.setDepositNumber(randomDepositNumber2);
                    BigDecimal bigDecimal = new BigDecimal((rand.nextInt(50)));
                    newAccount.setAmount(bigDecimal);
                    accountList.add(newAccount);
                    accountListString.add(newAccount.toString());
                }
            }

            SaveFile saveFile = new SaveFile();
            saveFile.setSaveFile("account", accountListString);

        }
        return accountList;


    }

    public List<PaymentDTO> getPaymentList() throws CanNotSaveFile, IOException {
        List<PaymentDTO> paymentList = new ArrayList<>();
        List<String> paymentListString = new ArrayList<>();
        String[] paymentString;
        Random rand = new Random();
        try {


            File fileDir = new File("pay.txt");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), StandardCharsets.UTF_8));
            String str;
            while ((str = in.readLine()) != null) {
                PaymentDTO newPayment = new PaymentDTO();
                paymentString = (str.split("\t"));
                newPayment.setDeptorOrCreditor(paymentString[0]);
                newPayment.setDepositNumber(paymentString[1]);
                BigDecimal bd = new BigDecimal(paymentString[2]);
                newPayment.setAmount(bd);
                paymentList.add(newPayment);
            }
            in.close();


            return paymentList;


        } catch (Throwable t) {


            PaymentDTO newPayment = new PaymentDTO();
            PaymentDTO newPayment2 = new PaymentDTO();

            String randomDepositNumber1 = "1.10.100.1";
            newPayment2.setDeptorOrCreditor("deptor");
            newPayment2.setDepositNumber(randomDepositNumber1);
            BigDecimal bigDecimal2 = new BigDecimal((rand.nextInt(9000) + 1000));
            newPayment2.setAmount(bigDecimal2);
            paymentList.add(newPayment2);
            paymentListString.add(newPayment2.toString());

            int accountLoopLength = (rand.nextInt(20));
            for (int i = 0; i <= accountLoopLength; i++) {
                String randomPayDepositNumber = "1.20.100." + (rand.nextInt(200));
                Optional<PaymentDTO> first = paymentList.stream()
                        .filter(x -> {
                            return Objects.equals(randomPayDepositNumber, x.getDepositNumber());
                        })
                        .findFirst();
                if (!(first.isPresent())) {
                    newPayment.setDeptorOrCreditor("creditor");
                    newPayment.setDepositNumber(randomPayDepositNumber);
                    BigDecimal bigDecimal = new BigDecimal((rand.nextInt(50)));
                    newPayment.setAmount(bigDecimal);
                    paymentList.add(newPayment);
                    paymentListString.add(newPayment.toString());
                }
            }

            SaveFile saveFile = new SaveFile();
            saveFile.setSaveFile("pay", paymentListString);
            return paymentList;

        }

    }
}


