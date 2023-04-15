package org.syh.demo.channel.file;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AsynchronousFileChannelReadDemo {

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("resources/sample_read");

        AsynchronousFileChannel channel = AsynchronousFileChannel.open(path);
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        channel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                attachment.flip();
                Charset charset = StandardCharsets.UTF_8;
                String content = charset.decode(attachment).toString();
                System.out.println(String.format("Read %d bytes, content: %s", result, content));
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                System.err.println("Failed to read file: " + exc.getMessage());
            }
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

