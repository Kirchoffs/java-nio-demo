package org.syh.demo.selector;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

public class ClientServerSelectorSecondDemo {
    public static void main(String[] args) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        server(latch);
        latch.await();
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
            // There is bug, if we call close method, the server will always have readable events, and read will always return -1.
            // So we need to handle -1 cases as client closed event.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void server(CountDownLatch latch) {
        try {
            Selector serverSelector = Selector.open();
            Selector clientSelector = Selector.open();

            new Thread(() -> {
                try {
                    ServerSocketChannel listenerChannel = ServerSocketChannel.open();
                    listenerChannel.socket().bind(new InetSocketAddress(8080));
                    listenerChannel.configureBlocking(false);
                    listenerChannel.register(serverSelector, SelectionKey.OP_ACCEPT);
                    
                    latch.countDown();
                    while (true) {
                        System.out.println("Blocker for server selector");
                        if (serverSelector.select(2000) > 0) {
                            System.out.println("Some channels are ready for connecting");
                            Set<SelectionKey> set = serverSelector.selectedKeys();
                            Iterator<SelectionKey> keyIterator = set.iterator();

                            while (keyIterator.hasNext()) {
                                SelectionKey key = keyIterator.next();

                                if (key.isAcceptable()) {
                                    try {
                                        System.out.println("Get new connect");
                                        SocketChannel clientChannel = listenerChannel.accept();
                                        clientChannel.configureBlocking(false);
                                        clientChannel.register(clientSelector, SelectionKey.OP_READ);
                                        System.out.println("Connect done");
                                    } finally {
                                        keyIterator.remove();
                                    }
                                }

                            }
                        }
                    }
                } catch (Exception ignored) {}
            }).start();


            new Thread(() -> {
                try {
                    while (true) {
                        System.out.println("Blocker for client selector");
                        if (clientSelector.select(2000) > 0) {
                            System.out.println("Some channels are ready for reading");

                            Set<SelectionKey> set = clientSelector.selectedKeys();
                            Iterator<SelectionKey> keyIterator = set.iterator();

                            while (keyIterator.hasNext()) {
                                SelectionKey key = keyIterator.next();

                                if (key.isReadable()) {
                                    try {
                                        System.out.println("Get new data input");
                                        SocketChannel clientChannel = (SocketChannel) key.channel();
                                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                                        int len = clientChannel.read(byteBuffer);
                                        if (len == -1) {
                                            key.channel().close();
                                            break;
                                        }
                                        System.out.println(String.format("There are %d number of bytes of data", len));
                                        byteBuffer.flip();
                                        System.out.println(new String(byteBuffer.array(), 0, len));
                                        System.out.println("Read done");
                                    } catch (Exception e) {
                                        System.out.println("Read error");
                                    } finally {
                                        keyIterator.remove();
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception ignored) {}
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
