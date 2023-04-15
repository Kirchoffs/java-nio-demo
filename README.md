# java-nio-demo
__Buffer__ & __Channel__ & __Selector__ 

Each channel will have a buffer.  
Each selector has a corresponding thread.  
Each thread contains multiple channels (connection).
## Channel
### FileChannel
• position()  
• size()  
• truncate()  
• force()  
• transferTo()  
• transferFrom()

`FileChannel` does not support non-blocking mode, also it does not extends `SelectableChannel`.

### AsynchronousFileChannel
```
public abstract <A> void read(
    ByteBuffer dst,
    long position,
    A attachment,
    CompletionHandler<Integer,? super A> handler
)
```
```
channel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        Charset charset = StandardCharsets.UTF_8;
        String content = charset.decode(attachment).toString();
        System.out.println("Read content: " + content);
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        System.err.println("Failed to read file: " + exc.getMessage());
    }
});
```

AsynchronousFileChannel#read parameters explanation:

- __dst:__ This is the ByteBuffer object where the read data will be stored.

- __position:__ This is the position in the file where the read operation will begin.

- __attachment:__ This is an object that can be used to attach additional data to the asynchronous read operation. In the example, we attach the same ByteBuffer object as the first parameter, so that it can be used in the completed method to extract the read data.

- __handler:__ This is the CompletionHandler object that will be called when the asynchronous read operation completes, either successfully or with an error.

### ServerSocketChannel
### SocketChannel
### DatagramChannel
• send()  
• receive()  
• write() (Should call connect() first)  
• read() (Should call connect() first)

## Buffer
ByteBuffer & CharBuffer & ShortBuffer & IntBuffer & LongBuffer & Float Buffer & DoubleBuffer  
• mark  
• position  
• limit  
• capacity  
When writing to buffer, limit = capacity  
When reading from buffer,  limit <= capacity  

## Selector