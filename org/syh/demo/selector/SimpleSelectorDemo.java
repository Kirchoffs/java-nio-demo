package org.syh.demo.selector;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;


public class SimpleSelectorDemo {
    public static void main(String[] args) throws Exception {
        Selector selector = Selector.open();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(9999));

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        
        Set<SelectionKey> selectedKeys = selector.selectedKeys();
        Iterator<SelectionKey> selectedKeysIterator = selectedKeys.iterator();
        while (selectedKeysIterator.hasNext()) {
            SelectionKey key = selectedKeysIterator.next();
            if (key.isAcceptable()) {

            } else if (key.isConnectable()) {

            } else if (key.isReadable()) {

            } else if (key.isWritable()) {

            }
        }

        selectedKeysIterator.remove();
    }
}
