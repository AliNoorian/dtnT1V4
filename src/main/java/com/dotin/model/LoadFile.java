package com.dotin.model;

import com.dotin.beans.AccountDTO;
import com.dotin.beans.PaymentDTO;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class LoadFile {

    public LoadFile() {

    }


    public synchronized List<AccountDTO> getAccountList() throws IOException {
        List<AccountDTO> accountList = new ArrayList<>();
        List<String> accountListString = new ArrayList<>();
        String[] accountString;
        Random rand = new Random();
        try {


            File fileDir = new File("Program Files\\account.txt");
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

            ArrayList<Integer> numbers = new ArrayList<>();
            AccountDTO newAccount = new AccountDTO();
            AccountDTO newAccount2 = new AccountDTO();

            String randomDepositNumber1 = "1.10.100.1";
            newAccount2.setDepositNumber(randomDepositNumber1);
            BigDecimal bigDecimal2 = new BigDecimal((rand.nextInt(9000) + 1000));
            newAccount2.setAmount(bigDecimal2);
            accountList.add(newAccount2);
            accountListString.add(newAccount2.toString() + "\n");

            int number = (rand.nextInt(200));
            numbers.add(number);

            int accountLoopLength = (rand.nextInt(100));
            for (int i = 0; i <= accountLoopLength; i++) {
                do {
                    number = (rand.nextInt(200));
                } while (numbers.contains(number));
                numbers.add(number);
                String randomDepositNumber2 = "1.20.100." + number;
                Optional<AccountDTO> first = accountList.stream()
                        .filter(x -> Objects.equals(randomDepositNumber2, x.getDepositNumber()))
                        .findFirst();
                if (!(first.isPresent())) {
                    newAccount.setDepositNumber(randomDepositNumber2);
                    BigDecimal bigDecimal = new BigDecimal((rand.nextInt(50)));
                    newAccount.setAmount(bigDecimal);
                    accountList.add(newAccount);
                    accountListString.add(newAccount.toString() + "\n");
                }
            }

            SaveFile saveFile = new SaveFile();
            saveFile.setSaveFile("account", accountListString);

        }
        return accountList;


    }

    public synchronized List<PaymentDTO> getPaymentList() throws IOException {
        List<PaymentDTO> paymentList = new ArrayList<>();
        List<String> paymentListString = new ArrayList<>();
        String[] paymentString;
        Random rand = new Random();
        try {


            File fileDir = new File("Program Files\\pay.txt");
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

            ArrayList<Integer> numbers2 = new ArrayList<>();
            PaymentDTO newPayment = new PaymentDTO();
            PaymentDTO newPayment2 = new PaymentDTO();

            String randomDepositNumber1 = "1.10.100.1";
            newPayment2.setDeptorOrCreditor("deptor");
            newPayment2.setDepositNumber(randomDepositNumber1);
            BigDecimal bigDecimal2 = new BigDecimal((rand.nextInt(9000) + 1000));
            newPayment2.setAmount(bigDecimal2);
            paymentList.add(newPayment2);
            paymentListString.add(newPayment2.toString() + "\n");

            int number2 = (rand.nextInt(200));
            numbers2.add(number2);

            int accountLoopLength = (rand.nextInt(20));
            for (int i = 0; i <= accountLoopLength; i++) {

                do {
                    number2 = (rand.nextInt(200));
                } while (numbers2.contains(number2));
                numbers2.add(number2);
                String randomPayDepositNumber = "1.20.100." + number2;

                Optional<PaymentDTO> first = paymentList.stream()
                        .filter(x -> Objects.equals(randomPayDepositNumber, x.getDepositNumber()))
                        .findFirst();
                if (!(first.isPresent())) {
                    newPayment.setDeptorOrCreditor("creditor");
                    newPayment.setDepositNumber(randomPayDepositNumber);
                    BigDecimal bigDecimal = new BigDecimal((rand.nextInt(40) + 10));
                    newPayment.setAmount(bigDecimal);
                    paymentList.add(newPayment);
                    paymentListString.add(newPayment.toString() + "\n");
                }
            }

            SaveFile saveFile = new SaveFile();
            saveFile.setSaveFile("pay", paymentListString);
            return paymentList;

        }

    }

    public synchronized String readAccountFile() throws FileNotFoundException {

        String filePath = "Program Files\\account.txt";
        Scanner sc = new Scanner(new File(filePath));
        StringBuilder buffer = new StringBuilder();
        while (sc.hasNextLine()) {
            buffer.append(sc.nextLine()).append(System.lineSeparator());
        }
        String s = buffer.toString();
        sc.close();
        return s;
    }
}


