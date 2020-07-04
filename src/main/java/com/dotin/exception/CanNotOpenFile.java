package com.dotin.exception;

import java.io.IOException;

public class CanNotOpenFile extends IOException {




    public CanNotOpenFile() {
        super("Can not open source file \n");
    }
}
