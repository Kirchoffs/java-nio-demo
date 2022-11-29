package org.syh.demo.buffer;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

public class BufferReadWriteDemo {

    public static void main(String[] args) throws Exception {
        byteBufferDemo();
        intBufferDemo();
    }

    public static void byteBufferDemo() throws Exception {
        RandomAccessFile file = new RandomAccessFile("resources/sample_src", "rw");
        FileChannel channel = file.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int bytesRead = channel.read(buffer);
        while (bytesRead != -1) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                System.out.println((char) buffer.get());
            }
            buffer.clear();
            bytesRead = channel.read(buffer);
        }
        file.close();
    }

    public static void intBufferDemo() throws Exception {
        IntBuffer buffer = IntBuffer.allocate(8);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put(2 * (i + 1));
        }
        buffer.flip();
        while (buffer.hasRemaining()) {
            int num = buffer.get();
            System.out.print(num + " ");
        }
        System.out.println();
    }
}