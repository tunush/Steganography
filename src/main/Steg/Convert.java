package main.Steg;

import java.nio.charset.Charset;

public class Convert {
    public byte[] txtToByte(String s){
        byte [] arr = s.getBytes(Charset.forName("UTF-8"));
        return arr;
    }
}
