package com.dotin.model;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SaveFile {

    public SaveFile() {
    }

    public  synchronized void setSaveFile(String fileName, List<String> listName) throws IOException {
        File fileDir = new File("Program Files\\" + fileName + ".txt");
        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileDir), StandardCharsets.UTF_8));
        PrintWriter pw = new PrintWriter(out);

        for (String names : listName) {
            pw.append(names);
        }
        pw.flush();
        pw.close();
    }
    public  synchronized void saveFile(String fileName) throws IOException {
        File fileDir = new File("Program Files\\" + fileName + ".txt");
        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileDir), StandardCharsets.UTF_8));
        PrintWriter pw = new PrintWriter(out);
        pw.flush();
        pw.close();
    }
}