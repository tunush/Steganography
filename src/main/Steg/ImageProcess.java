package main.Steg;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class ImageProcess {
    public BufferedImage fetchImage() throws Exception {
        BufferedImage img = ImageIO.read(getClass().getResourceAsStream("../tigr.bmp"));
        return img;
    }

    public void hideText(BufferedImage img, byte[] txt) throws Exception {
        int i = 0;
        int j = 0;
        for(byte b : txt) {
            for(int k = 7; k >= 0; k--) {
                try {
                    Color c = new Color(img.getRGB(j,i));
                    byte blue = (byte) c.getBlue();
                    int red = c.getRed();
                    int green = c.getGreen();
                    int bitVal = (b >>> k) & 1;
                    blue = (byte)((blue & 0xFE)| bitVal);
                    Color newColor = new Color(red, green, (blue & 0xFF));
                    img.setRGB(j, i, newColor.getRGB());
                }
                catch (ArrayIndexOutOfBoundsException ae){}
                j++;
            }
            i++;
        }

        System.out.println("Text Hidden");
        createImgWithMsg(img);
        System.out.println("Decode? Y or N");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        switch(in.readLine().trim()){
            case "Y":
            case "y":
            {
                String k = revealMsg(txt.length,0);
                System.out.println("Text is: " + k);
            }
            break;
            default:
                System.out.println("Program is now ending");
                break;
        }
    }

    void createImgWithMsg(BufferedImage img) {
        try{
            File ouptut = new File("C:/Users/ms_tu/IdeaProjects/ClassWork3/src/main/new_img.bmp");
            ImageIO.write(img, "bmp", ouptut);
        }
        catch(Exception ex) {}
    }

    BufferedImage newImageFetch() {
        File f = new File("C:/Users/ms_tu/IdeaProjects/ClassWork3/src/main/new_img.bmp");
        BufferedImage img = null;
        try {
            img = ImageIO.read(f);
        }
        catch(Exception ex) {}
        return img;
    }

    String revealMsg(int msgLen , int offset) {
        BufferedImage img = newImageFetch();
        byte [] msgBytes = extractHiddenBytes(img, msgLen, offset);
        if(msgBytes == null)
            return null;
        String msg = new String(msgBytes);
        return (msg);
    }

    byte[] extractHiddenBytes(BufferedImage img, int size, int offset){
        int i = 0;
        int j = 0;
        byte[] hiddenBytes = new byte[size];

        for(int l = 0; l < size; l++){
            for(int k = 0; k < 8; k++){
                try {
                    Color c = new Color(img.getRGB(j, i));
                    byte blue = (byte) c.getBlue();
                    int red = c.getRed();
                    int green = c.getGreen();
                    hiddenBytes[l] = (byte) ((hiddenBytes[l] << 1) | (blue & 1));
                }
                catch (ArrayIndexOutOfBoundsException ae){}
                j++;
            }
            i++;
        }
        return hiddenBytes;
    }
}
