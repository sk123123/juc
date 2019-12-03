package com.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * 非阻塞的Echoserver
 *  在这个非阻塞的模式下，Echoserver只用启动一个主线程就能同事处理其他三件事： 
 * 1.接收客户连接
 * 2.接收客户发送的数据 
 * 3.向客户发回响应数据
 *
 * @author Administrator
 *
 */
public class NZSEchoServer{
    private Selector selector = null;
    private ServerSocketChannel serverSocketChannel = null;
    private int port = 8000;

    private Charset charset = Charset.forName("GBK");

    /**
     * 构造方法负责启动服务器，绑定端口
     *
     * @throws IOException
     */
    private NZSEchoServer() throws IOException {
        // 创建一个selector 对象
        selector = Selector.open();
        // 创建serverSocketChannel对象
        serverSocketChannel = ServerSocketChannel.open();
        // 设置参数 使得在同一个主机上关闭了服务器程序，紧接着再启动该服务器程序时可以顺利绑定到相同的端口
        serverSocketChannel.socket().setReuseAddress(true);
        // 使erverSocketChannel工作处于非阻塞模式
        serverSocketChannel.configureBlocking(false);
        // 把服务器进程与一个本地端口绑定
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        System.out.println("服务器启动！");
    }

    /**
     * 负责开头说的三件事 * 1.接收客户连接 2.接收客户发送的数据 3.向客户发回响应数据
     *
     * @throws IOException
     */
    public void service() throws IOException {
        // serverSocketChannel向Selector注册接收连接就绪事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (selector.select() > 0) {
            // 获得Selector的selected-keys集合
            Set readyKeys = selector.selectedKeys();
            Iterator it = readyKeys.iterator();

            //从集合中依次取出SelectionKey对象,判断是那种事件发生，然后进行处理
            while (it.hasNext()) {
                SelectionKey key = null;
                try {
                    // 处理selectionkey 取出第一个selectionkey
                    key = (SelectionKey) it.next();
                    // 把selectionKey从selected-key集合中删除
                    it.remove();

                    // 这个key 标识连接就绪事件 处理
                    if (key.isAcceptable()) {
                        //获得与SelectionKey相连的ServerSocketChannel
                        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                        //获得与客户端连接的SocketChannel
                        SocketChannel sockChannel = ssc.accept();
                        System.out.println("接收到客户端连接，来自：" + sockChannel.socket().getInetAddress() + ":"
                                + sockChannel.socket().getPort());

                        //设置SocketChannel为非阻塞模式
                        sockChannel.configureBlocking(false);
                        //创建一个用于存放用户发送来的数据的缓冲区
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        //SocketChannel向Selector注册读就绪事件和写就绪事件   关联了一个buffer
                        //这个byteBuffer将作为附件与新建的selectionKey对象关联
                        sockChannel.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE,buffer);

                    }
                    // 这个key 标识读就绪事件 处理
                    if (key.isReadable()) {
                        receive(key);
                    }
                    // 这个key 标识写就绪事件 处理
                    if (key.isWritable()) {
                        send(key);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        //使得这个selctionkey失效
                        //使得selectory不再监控这个selectionkey感兴趣的事件
                        if (key != null) {
                            key.cancel();
                            key.channel().close();
                        }
                    } catch (Exception e2) {
                        e.printStackTrace();
                    }

                }

            }

        }

    }
    /**
     * 处理写就绪事件
     * @param key
     * @throws IOException
     */
    private void send(SelectionKey key) throws IOException {
        //获取与selectionKey相关联的附件
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        SocketChannel socketChannel = (SocketChannel) key.channel();
//        flip的作用有两个：
//        1. 把limit设置为当前的position值 
//        2. 把position设置为0
//        然后处理的数据就是从position到limit直接的数据，也就是你刚刚读取过来的数据
        buffer.flip();
        //按照gbk编码，把buffer中的字节转换成字符串
        String data = decode(buffer);
        //如果还没有读到一行数据，之间返回
        if (data.indexOf("\r\n") == -1)
            return;

        //已经有一行以上数据，截取一行数据
        String outputData = data.substring(0, data.indexOf("\n") + 1);
        System.out.println(outputData);
        //把输出的字符串按照gbk编码，转换成字节，把他反正outputBuffer中
        ByteBuffer outputBuffer = encode("echo" + outputData);

        //outputBuffer.hasRemaining()判断是否还有未处理的字节
        //非阻塞模式下不确保write方法一次就把outputBuffer所有字节发送完，只是奉行能发多少就发送多少的原则，所以我们要采用循环
        while (outputBuffer.hasRemaining()) {
            socketChannel.write(outputBuffer);
        }

        //我觉得  相当于移动 栈指针 然后删除没有指向的数据段
        ByteBuffer temp = encode(outputData);
        //设置buffer的位置为temp的极限
        buffer.position(temp.limit());
        //删除buffer中已经处理的数据
        buffer.compact();
        //如果已经输出了字符串  "bye\r\n" ,就使selectionKet失效，并关闭SocketChannel
        if (outputData.equals("bye\r\n")) {
            key.cancel();
            socketChannel.close();
            System.out.println("关闭与客户端的连接！");
        }

    }

    /**
     *
     * 处理读就绪事件
     * 把收到的数据放入buffer
     *
     * @param key
     * @throws IOException
     */
    public void receive(SelectionKey key) throws IOException {
        //获得与SelectionKey关联的附件
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        //获得与SelectionKey关联的Sockethannel
        SocketChannel socketChannel = (SocketChannel) key.channel();
        //创建一个byteBuffer,用于存放读到的数据
        ByteBuffer readBuffer = ByteBuffer.allocate(32);
        socketChannel.read(readBuffer);
        readBuffer.flip();


        //把buffer的极限设为容量
        buffer.limit(buffer.capacity());
        //把readBuffer中的内容拷贝到buffer中
        //假定buffer的容量足够大，不会出现缓冲区溢出异常
        buffer.put(readBuffer); // 把读到的数据放到buffer中
    }

    /**
     * 编码  把字符串转换成自己序列
     *
     * @param string
     * @return
     */
    private ByteBuffer encode(String string) {
        return charset.encode(string);
    }

    /**
     * 解码  把字节序列转换为字符串
     *
     * @param buffer
     * @return
     */
    private String decode(ByteBuffer buffer) {
        CharBuffer charBuffer = charset.decode(buffer);
        return charBuffer.toString();
    }
    /**
     * 主程序
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        NZSEchoServer server=new NZSEchoServer();
        server.service();
    }
}