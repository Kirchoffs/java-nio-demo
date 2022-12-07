package org.syh.demo.pipe;

import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.channels.Pipe.SinkChannel;
import java.nio.channels.Pipe.SourceChannel;

public class PipeDemo {
    public static void main(String[] args) throws Exception {
        Pipe pipe = Pipe.open();
        
        SinkChannel sinkChannel = pipe.sink();
        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
        writeBuffer.put("Hello".getBytes());
        writeBuffer.flip();
        sinkChannel.write(writeBuffer);

        SourceChannel sourceChannel = pipe.source();
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        int len = sourceChannel.read(readBuffer);
        System.out.println(new String(readBuffer.array(), 0, len));

        sinkChannel.close();
        sourceChannel.close();
    }
}
