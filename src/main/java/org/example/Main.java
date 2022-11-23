package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
//import java.ByteGrayColor;

public class Main {

    public static void main(String[] args) {

        File file = new File("image/capture.PNG");

        HashMap<Integer, Integer> intensiteFreq = new HashMap<Integer, Integer>();
        int pixelFreq = 0;
        int nbrPixels = 0;

        BufferedImage imgBuffer, originalImage;

        try {
            imgBuffer = ImageIO.read(file);
            originalImage = ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int height = imgBuffer.getHeight(null);
        int width = imgBuffer.getWidth(null);
        System.out.println("width:" + width + " " + "height: " + height);

        for (int i = 0; i < imgBuffer.getHeight(); i++) {
            for (int j = 0; j < imgBuffer.getWidth(); j++) {


                // ******Colorful image To Gray conversion start********
                Color c = new Color(imgBuffer.getRGB(j, i));
                int red = (int) (c.getRed() * 0.299);
                int green = (int) (c.getGreen() * 0.587);
                int blue = (int) (c.getBlue() * 0.114);

                Color newColor = new Color(red + green + blue, red + green + blue, red + green + blue);

                imgBuffer.setRGB(j, i, newColor.getRGB());
                // ****** To Gray conversion end ********


                // Store in HashMap
                int pixel = imgBuffer.getRGB(j, i);

                pixelFreq = intensiteFreq.getOrDefault(pixel, 0); // add if this couple doesn't exists
                pixelFreq++;
                intensiteFreq.put(pixel, pixelFreq);

                // print value
                //System.out.print(Integer.toHexString(imgBuffer.getRGB(j, i)).substring(2) + " ");
            }
            //System.out.println(" ");
        }

        for (Integer element : intensiteFreq.keySet()) {
            int key = element;
            int value = intensiteFreq.get(key);
            nbrPixels = nbrPixels + value;
            System.out.println(Integer.toHexString(key).substring(2) + " => " + value);
        }
        System.out.println("Nombre des pixels " + nbrPixels);


        JFrame gui = new JFrame();
        gui.setVisible(true);
        gui.setSize(900, 700);

        gui.getContentPane().setLayout(new FlowLayout());
        gui.getContentPane().setBackground(Color.darkGray);


        gui.getContentPane().add(new JLabel(new ImageIcon(originalImage)));
        gui.getContentPane().add(new JLabel(new ImageIcon(imgBuffer)));

    }

}