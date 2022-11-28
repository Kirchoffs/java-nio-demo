package org.syh.demo.channel.file;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class FileChannelTransferDemo {
    public static void main(String[] args) throws IOException {
        transferFromTest();
    }

    private static void transferFromTest() throws IOException {
        RandomAccessFile fileSrc = new RandomAccessFile("resources/sample_src", "rw");
        FileChannel fromChannel = fileSrc.getChannel();

        RandomAccessFile fileDst = new RandomAccessFile("resources/sample_dst", "rw");
        FileChannel toChannel = fileDst.getChannel();

        long position = 0;
        long size = fromChannel.size();
        toChannel.transferFrom(fromChannel, position, size);

        fileSrc.close();
        fileDst.close();
        System.out.println("Done");
    }

    private static void transferToTest() throws IOException {
        RandomAccessFile fileSrc = new RandomAccessFile("resources/sample_src", "rw");
        FileChannel fromChannel = fileSrc.getChannel();

        RandomAccessFile fileDst = new RandomAccessFile("resources/sample_dst", "rw");
        FileChannel toChannel = fileDst.getChannel();

        long position = 0;
        long size = fromChannel.size();
        fromChannel.transferTo(position, size, toChannel);

        fileSrc.close();
        fileDst.close();
        System.out.println("Done");
    }
}
