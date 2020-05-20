package com.andall.sally.supply.media;

import com.alibaba.excel.util.FileUtils;
import com.idrsolutions.image.png.PngCompressor;
import net.coobird.thumbnailator.Thumbnails;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ImgTest {

    @Test
    public void imgPngTest() throws Exception {
        String filePath = "/Users/shuailingli/Pictures/ives.jpg";
        File file = new File(filePath);
        FileInputStream inputStream = new FileInputStream(file);

        File outfile=new File("/Users/shuailingli/Pictures/ives1.jpg");
        FileOutputStream outputStream = new FileOutputStream(outfile);
//        PngCompressor.compress(file,outfile);

        PngCompressor.compress(inputStream, outputStream);
    }

    @Test
    public void imgPngTest1() throws Exception {
        String filePath = "/Users/shuailingli/Pictures/peer.png";
        File file = new File(filePath);
        FileInputStream inputStream = new FileInputStream(file);

        File outfile=new File("/Users/shuailingli/Pictures/peer1.jpg");
        FileOutputStream outputStream = new FileOutputStream(outfile);
//        PngCompressor.compress(inputStream,outputStream);

        Thumbnails.of(inputStream)
                .scale(1f)
                .outputFormat("jpg")
                .toOutputStream(outputStream);
    }


}
