package com.dotin.model;

import com.dotin.beans.AccountDTO;
import com.dotin.exception.CanNotSaveFile;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class LoadFile {

    public LoadFile() {

    }


    public List<AccountDTO> getAccountList() throws CanNotSaveFile, IOException {
        List<AccountDTO> accountList = new ArrayList<AccountDTO>();
        List<String> accountListString = new ArrayList<String>();
        String[] accountString = null;
        Random rand = new Random();
        try {


            File fileDir = new File("account.txt");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF8"));
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
            BigDecimal bigDecimal2 = new BigDecimal((rand.nextInt(9000)+1000));
            newAccount2.setAmount(bigDecimal2);
            accountListString.add(newAccount2.toString());

            int accountLoopLength = (rand.nextInt(100));
            for (int i = 0; i <= accountLoopLength; i++) {
                String randomDepositNumber2 = "1.20.100." + (rand.nextInt(200));
                Optional<AccountDTO> first = accountList.stream()
                        .filter(x -> Objects.equals(randomDepositNumber2, x.getDepositNumber()))
                        .findFirst();
                if (!(first.isPresent())) {
                    newAccount.setDepositNumber(randomDepositNumber2);
                    BigDecimal bigDecimal = new BigDecimal((rand.nextInt(50)));
                    newAccount.setAmount(bigDecimal);
                    accountListString.add(newAccount.toString());
                }
            }

            SaveFile saveFile = new SaveFile();
            saveFile.setSaveFile("account", accountListString);

        }
        return accountList;


    }
}


