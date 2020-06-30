package com.dotin.model;

import java.io.*;
import java.util.List;

public class SaveFile {

    public SaveFile() {
    }

    public void setSaveFile(String fileName, List<String> listName) throws IOException {
        File fileDir = new File(fileName + ".txt");
        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileDir), "UTF8"));
        PrintWriter pw = new PrintWriter(out);

        for (String names : listName) {
            pw.append(names);
        }
        pw.flush();
        pw.close();
    }
}
