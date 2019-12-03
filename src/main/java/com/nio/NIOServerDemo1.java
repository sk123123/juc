package com.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class NIOServerDemo1 {
//    private ServerSocketChannel serverSocketChannel;
    private ByteBuffer wBuffer = ByteBuffer.allocate(1024);
    private ByteBuffer rBuffer = ByteBuffer.allocate(1024);
    private static Selector selector;
    private int port = 8080;

    public NIOServerDemo1(int port)  {
        this.port = port;
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void init() throws IOException {
        selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(port));
        serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
        System.out.println("服务器启动");
    }
    private void listen(){
        try {
            selector.select();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
