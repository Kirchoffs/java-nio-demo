package org.syh.demo.channel.socket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelDemo {
    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("www.google.com", 80));
        socketChannel.configureBlocking(false);

        // or
        // SocketChannel socketChannel = SocketChannel.open();
        // socketChannel.connect(new InetSocketAddress("www.google.com", 80));

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        socketChannel.read(buffer);
        socketChannel.close();
        System.out.println("Read over");
    }
}
