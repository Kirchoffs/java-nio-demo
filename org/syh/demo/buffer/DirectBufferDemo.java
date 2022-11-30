package org.syh.demo.buffer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class DirectBufferDemo {
    public static void main(String[] args) throws Exception {
        String inputFile = "resources/sample_src";
        FileInputStream inputStream = new FileInputStream(inputFile);
        FileChannel inputChannel = inputStream.getChannel();
        
        String outputFile = "resources/sample_dst";
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        FileChannel outputChannel = outputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        while (true) {
            buffer.clear();
            int read = inputChannel.read(buffer);
            if (read == -1) {
                break;
            }
            buffer.flip();
            outputChannel.write(buffer);
        }

        inputStream.close();
        outputStream.close();
    }
}
