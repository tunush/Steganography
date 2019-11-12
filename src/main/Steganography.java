package main;
import main.Steg.*;

import java.awt.image.BufferedImage;

class Steganography {
    public static void main(String [] args) throws Exception {
        String in = "Here hidden the text :)";

        Convert c = new Convert();
        ImageProcess impro = new ImageProcess();
        byte [] txtBytes = c.txtToByte(in);
        BufferedImage img = impro.fetchImage();
        impro.hideText(img,txtBytes);
    }
}