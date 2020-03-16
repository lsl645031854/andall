package com.andall.sally.supply.netty;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 17:16 2020/3/15
 */
public class BioServer {

    public static void main(String[] args) throws IOException {
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();

        ServerSocket serverSocket = new ServerSocket(6666);

        System.out.println("服务端启动了");

        while (true) {
            // 监听客户端socket连接
            Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端");
            newCachedThreadPool.execute(() -> handler(socket));
        }

    }

    private static void handler(Socket socket) {
        try {

            System.out.println("线程信息：名称，" + Thread.currentThread().getName());

            byte[] bytes = new byte[1024];
            // 通过socket获取输入流
            InputStream inputStream = socket.getInputStream();
            // 循环读取客户端发送的数据
            while (true) {
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(new String(bytes, 0, read));
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("关闭和client的连接");
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
