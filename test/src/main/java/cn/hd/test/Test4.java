package cn.hd.test;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Test4 {
    public static void main(String[] args) throws IOException {
        String xx = "c/video.pngs/test.png";
        System.out.println(xx.lastIndexOf(".png"));
        String yy = xx.substring(0, xx.lastIndexOf(".png") );
        System.out.println(yy);

        File srcf = new File("c:/videos/test.png");
        BufferedImage image = ImageIO.read(srcf);
        int h = image.getHeight();
        int w = image.getWidth();
        // 比例32:35
        int z = (w * 35) / 32;
        int y = (h - z) / 2;
        int x = z + y;
        System.out.println(x + "  " + y);
        cutImage(srcf, 0, y, w, z);
    }

    private static void cutImage(File srcImg, int x, int y, int width, int height) throws IOException {
        Rectangle rect = new Rectangle(x, y, width, height);
        FileInputStream srcFileStream = new FileInputStream(srcImg);
        ImageInputStream iis = ImageIO.createImageInputStream(srcFileStream);
        ImageReader reader = ImageIO.getImageReadersBySuffix("png").next();
        reader.setInput(iis,true);
        ImageReadParam param = reader.getDefaultReadParam();
        param.setSourceRegion(rect);
        BufferedImage bi = reader.read(0, param);
        File destf = new File("c:/videos/test-cut.png");
        FileOutputStream fileOutputStream = new FileOutputStream(destf);
        ImageIO.write(bi, "png", fileOutputStream);
    }
}
