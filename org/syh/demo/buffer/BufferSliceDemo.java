package org.syh.demo.buffer;

import java.nio.ByteBuffer;

public class BufferSliceDemo {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }

        buffer.position(3);
        buffer.limit(7);
        ByteBuffer bufferSlice = buffer.slice();

        for (int i = 0; i < bufferSlice.capacity(); i++) {
            byte cur = bufferSlice.get(i);
            cur *= 5;
            bufferSlice.put(i, cur);
        }

        buffer.position(0);
        buffer.limit(buffer.capacity());
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
    }
}
