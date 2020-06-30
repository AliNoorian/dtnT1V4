package com.dotin.exception;

public class CanNotSaveFile extends Exception {


    public CanNotSaveFile() {
    }

    public CanNotSaveFile(String fileName) {
        super("Can not save " + fileName + ".txt"+"\n");
    }
}
