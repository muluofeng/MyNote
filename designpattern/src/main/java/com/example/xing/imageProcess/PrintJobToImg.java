package com.example.xing.imageProcess;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author xiexingxing
 * @Created by 2019-10-27 19:27.
 */
public class PrintJobToImg {


    public static void main(String[] args) throws IOException {
        //1.将图片读取到内存中
        BufferedImage img = ImageIO.read(new File("/Users/xing/Desktop/xinchen_ps.jpg"));
//2.获取图片的画布
        Graphics g = img.getGraphics();
//3.在画布的指定位置上画文字

        Color color = new Color(91, 91, 91);
        g.setColor(color);
        g.setFont(new Font("苹方特粗", Font.PLAIN, 18));
        g.drawString("2019年10月28号", 215, 495);
//4.输出图片
        g.dispose();
        ImageIO.write(img, "jpg", new File("/Users/xing/Desktop/axinchen_new.jpg"));
    }

}