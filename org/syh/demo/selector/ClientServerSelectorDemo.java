package org.syh.demo.selector;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.rmi.server.ServerCloneException;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;


public class ClientServerSelectorDemo {
    public static void main(String[] args) throws Exception {
        new Thread(() -> server()).start();
        Thread.sleep(2000);
        new Thread(() -> client()).start();
    }

    private static void client() {
        try {
            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8080));
            socketChannel.configureBlocking(false);

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(new Date().toString().getBytes());

            buffer.flip();
            socketChannel.write(buffer);
            socketChannel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void server() {
        try {
            Selector selector = Selector.open();

            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(8080));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (selector.select() > 0) {
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> selectedKeysIterator = selectedKeys.iterator();
                while (selectedKeysIterator.hasNext()) {
                    SelectionKey key = selectedKeysIterator.next();
                    if (key.isAcceptable()) {
                        SocketChannel accept = serverSocketChannel.accept();
                        accept.configureBlocking(false);
                        accept.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int length;
                        while ((length = channel.read(buffer)) > 0) {
                            buffer.flip();
                            System.out.println(new String(buffer.array()));
                            buffer.clear();
                        }
                    }
                    selectedKeysIterator.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
