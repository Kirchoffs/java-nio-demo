package org.syh.demo.buffer;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappedByteBufferDemo {
    public static void main(String[] args) throws Exception {
        int start = 0, size = 1024;
        
        RandomAccessFile raf = new RandomAccessFile("resources/sample_src", "rw");
        FileChannel fc = raf.getChannel();
        MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, start, size);
    }
}  
