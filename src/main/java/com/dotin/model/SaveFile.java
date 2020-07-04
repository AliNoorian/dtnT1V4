package com.dotin.model;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SaveFile {

    public SaveFile() {
    }

    public synchronized void setSaveFile(String fileName, List<String> listName) throws IOException {
        File fileDir = new File("Program Files\\"+fileName + ".txt");
        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileDir), StandardCharsets.UTF_8));
        PrintWriter pw = new PrintWriter(out);

        for (String names : listName) {
            pw.append(names);
        }
        pw.flush();
        pw.close();
    }
    public synchronized void setSaveFileWithAppend(String fileName, String stringData) throws IOException {
        File fileDir = new File("Program Files\\"+fileName + ".txt");
        FileWriter fr = new FileWriter(fileDir, true);
        BufferedWriter br = new BufferedWriter(fr);
        PrintWriter pr = new PrintWriter(br);
        pr.println(stringData);
        pr.close();
        br.close();
        fr.close();
    }
}
