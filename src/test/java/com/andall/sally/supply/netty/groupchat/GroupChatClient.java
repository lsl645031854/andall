package com.andall.sally.supply.netty.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 20:03 2020/5/3
 */
public class GroupChatClient {
    // 定义相关属性
    private final String HOST = "127.0.0.1";
    private final int PORT = 6667;
    private Selector selector;
    private SocketChannel socketChannel;

    private String username;

    public GroupChatClient() throws IOException {
        selector = Selector.open();

        // 连接服务器
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));

        socketChannel.configureBlocking(false);

        socketChannel.register(selector, SelectionKey.OP_READ);

        // 得到userName
        username = socketChannel.getLocalAddress().toString();
        System.out.println(username + "is ok");
    }

    // 向服务器发送消息
    private void send(String info) {
        info = username + "说：" + info;
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 从服务器读取消息
    private void read() {
        try {
            int readChannels = selector.select();
            if (readChannels > 0) {
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();

                if (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        channel.read(buffer);
                        String message = new String(buffer.array());
                        System.out.println(message.trim());
                    }

                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        // 启动客户端
        GroupChatClient groupChatClient = new GroupChatClient();

        // 启动线程,每两秒从服务端获取数据.
        new Thread(() -> {
            while (true) {
                groupChatClient.read();
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // 发送数据
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String s = scanner.nextLine();
            groupChatClient.send(s);
        }
    }
}
