package com.andall.sally.supply.api;

import cn.hutool.core.lang.UUID;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.UploadFileRequest;
import org.apache.commons.lang3.StringUtils;
import org.jpedal.parser.shape.F;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 4:51 下午 2021/1/4
 */
public class OSSTest {
    @Test
    public void testUploadVideo() {
        String fileUrl = "/Users/shuailingli/Pictures/1609751696875352.mp4";
        try {
            String s = uploadFile(fileUrl);
            System.out.println(s);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Test
    public void testMockUploadVideo() throws Exception {
        String fileUrl = "/Users/shuailingli/Pictures/1609751696875352.mp4";

        MockMultipartFile multipart = new MockMultipartFile("file", "1609751696875352.mp4",
                "multipart/form-data", new FileInputStream(fileUrl));

        File dir = new File("/tmp/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File tmpFile = new File(dir, "1609751696875352.mp4");;
        multipart.transferTo(tmpFile);
        System.out.println(tmpFile.getAbsolutePath());
        tmpFile.delete();
    }

    @Test
    public void testCopy() throws Exception {
        String fileUrl = "/Users/shuailingli/Pictures/1609756875352.mp4";
        File source = new File(fileUrl);

        File         tmpFile     = null;
        OutputStream output      = null;

        File dir = new File("/tmp/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        tmpFile = new File(dir, "1609756875352.mp4");


        output = new FileOutputStream(tmpFile);
    }

    /**
     * 断点上传文件
     * @param fileurl  文件路径
     * @throws Exception ex
     */
    public String uploadFile(String fileurl) throws Throwable {
        String fileName= "video/" + UUID.fastUUID().toString() + ".mp4";
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient("oss-cn-hangzhou.aliyuncs.com",
                "IsWhD71BWc706ikN", "0lkgpj1mWfdnp3ZUCIuziE4a39yMSz");

        // 设置断点续传请求
        UploadFileRequest uploadFileRequest = new UploadFileRequest("lawson-dev",   fileName);
        // 指定上传的本地文件
        uploadFileRequest.setUploadFile(fileurl);
        // 指定上传并发线程数
        uploadFileRequest.setTaskNum(5);
        // 指定上传的分片大小
        uploadFileRequest.setPartSize(1024 * 1024);
        // 开启断点续传
        uploadFileRequest.setEnableCheckpoint(true);
        System.out.println("开始上传视频.....");
        // 断点续传上传
        ossClient.uploadFile(uploadFileRequest);
        System.out.println("上传成功！");
        URL url = generateURL("lawson-dev", fileName, ossClient);
        // 关闭client
        ossClient.shutdown();
        return "http://lawson-dev.oss-cn-hangzhou.aliyuncs.com" + url.getPath();
    }

    public URL generateURL(String bucket, String filename, OSSClient client) {

        String bucketName = bucket;

        // 过期时间在下载页面设置，暂时一分钟
        Date expiration = new Date(new Date().getTime() + 7 * 24 * 60 * 60 * 1000);

        // 生成URL
        URL url = client.generatePresignedUrl(bucketName, filename, expiration);

        return url;
    }
}
