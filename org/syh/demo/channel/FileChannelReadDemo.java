package org.syh.demo.channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelReadDemo {
    public static void main(String[] args) throws IOException {
        RandomAccessFile file = new RandomAccessFile("resources/sample_read", "rw");
        FileChannel fileChannel = file.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        
        int bytesRead = fileChannel.read(byteBuffer);
        while (bytesRead != -1) {
            System.out.println(String.format("Read %d bytes.", bytesRead));
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                System.out.println((char) byteBuffer.get());
            }
            byteBuffer.clear();
            bytesRead = fileChannel.read(byteBuffer);
        }

        file.close();
        System.out.println("Finished.");
    }
}