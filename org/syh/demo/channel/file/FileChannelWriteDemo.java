package org.syh.demo.channel.file;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelWriteDemo {
    public static void main(String[] args) throws IOException {
        RandomAccessFile file = new RandomAccessFile("resources/sample_write", "rw");
        FileChannel channel = file.getChannel(); 

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        String data = "hello";
        buffer.clear();
        buffer.put(data.getBytes());
        buffer.flip();

        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
        
        channel.close();
    }
}
