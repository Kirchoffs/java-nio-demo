# java-nio-demo
Buffer & Channel & Selector  

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