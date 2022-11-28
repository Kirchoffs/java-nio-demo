package org.syh.demo.channel.socket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerSocketChannelDemo {
    public static void main(String[] args) throws Exception {
        int port = 8888;
        ByteBuffer buffer = ByteBuffer.wrap("hello".getBytes());

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(port));
        ssc.configureBlocking(false);

        while (true) {
            System.out.println("Waiting for connection");
            SocketChannel sc = ssc.accept();
            if (sc == null) {
                System.out.println("null");
                Thread.sleep(2000);
            } else {
                System.out.println("Incoming connection from: " + sc.socket().getRemoteSocketAddress());
                buffer.rewind();
                sc.write(buffer);
                sc.close();
            }
        }
    }
}
