package org.syh.demo.channel.socket;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;

public class DatagramChannelDemo {
    public static void main(String[] args) {
        new Thread(() -> sendDatagram()).start();
        new Thread(() -> receiveDatagram()).start();
    }

    public static void sendDatagram() {
        try {
            DatagramChannel sendChannel = DatagramChannel.open();
            InetSocketAddress sendAddress = new InetSocketAddress("127.0.0.1", 9999);
    
            while (true) {
                ByteBuffer buffer = ByteBuffer.wrap("hello".getBytes("UTF-8"));
                sendChannel.send(buffer, sendAddress);
                System.out.println("Sent");
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void receiveDatagram() {
        try {
            DatagramChannel receiveChannel = DatagramChannel.open();
            InetSocketAddress receiveAddress = new InetSocketAddress(9999);
            System.out.println("Ready to bind");
            receiveChannel.bind(receiveAddress);
    
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (true) {
                buffer.clear();
                System.out.println("Ready to receive");
                SocketAddress socketAddress = receiveChannel.receive(buffer);
                buffer.flip();
                System.out.println("Received: " + socketAddress.toString());
                System.out.println("Received: " + Charset.forName("UTF-8").decode(buffer));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
