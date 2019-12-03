package com.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioServerDemo implements Runnable {
    private Selector selector ;
//    private Channel
    private ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);

    public NioServerDemo(int port) {
        try {
            this.selector = Selector.open();
            ServerSocketChannel socketChannel = ServerSocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.bind(new InetSocketAddress(port));
            socketChannel.register(selector,SelectionKey.OP_ACCEPT);

            System.out.println("Server start, port : " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true){
            try {
                this.selector.select();
                Iterator<SelectionKey>iterator = this.selector.selectedKeys().iterator();
                while(iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if(key.isValid()){
                        this.accept(key);
                    }
                    if(key.isReadable()){
                        this.read(key);
                    }
                    if(key.isWritable()){
                        this.write(key);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void write(SelectionKey key) throws IOException {
        SocketChannel sc = (SocketChannel) key.channel();
        this.writeBuffer.clear();
        byte[]bytes = new byte[1024];
        this.writeBuffer.put(bytes);
        this.writeBuffer.flip();
        sc.write(writeBuffer);
        sc.register(this.selector,SelectionKey.OP_READ);
    }
    private void read(SelectionKey key) throws IOException {
        this.readBuffer.clear();
        SocketChannel socketChannel = (SocketChannel) key.channel();
        int count  = socketChannel.read(this.readBuffer);
        //未读取到数据
        if(count == -1){
            key.channel().close();
            key.cancel();
            return;
        }else{
            this.readBuffer.flip();
            byte[]bytes = new byte[this.readBuffer.remaining()];
            this.readBuffer.get(bytes);
            String body = new String(bytes).trim();
            System.out.println("Server : " + body);
        }
    }
    // 通过key即可以拿到对应可以的channel  如果是阻塞的情况下，就执行accept方法
    private void accept(SelectionKey key) {
        try {
            //1. 获取服务通道
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();

            //2. 执行阻塞方法。（等待客户端的通道是否注册上了）
            SocketChannel sc = ssc.accept();

            //3. 设置阻塞模式
            sc.configureBlocking(false);

            //4. 注册到多路复用器上，并设置读取标志
            sc.register(this.selector, SelectionKey.OP_READ);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        new Thread(new NioServerDemo(8765)).start();
    }
}
