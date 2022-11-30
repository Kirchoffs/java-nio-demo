package org.syh.demo.buffer;

import java.nio.ByteBuffer;

public class ReadOnlyBufferDemo {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }

        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        for (int i = 0; i < buffer.capacity(); i++) {
            byte cur = buffer.get(i);
            cur *= 5;
            buffer.put(i, cur);
        }

        readOnlyBuffer.position(0);
        readOnlyBuffer.limit(buffer.capacity());
        while (readOnlyBuffer.hasRemaining()) {
            System.out.println(readOnlyBuffer.get());
        }
    }
}
