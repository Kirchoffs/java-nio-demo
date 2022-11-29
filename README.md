# java-nio-demo

## Channel
### FileChannel
• position()  
• size()  
• truncate()  
• force()  
• transferTo()  
• transferFrom()

### ServerSocketChannel
### SocketChannel
### DatagramChannel
• send()  
• receive()  
• write() (Should call connect() first)  
• read() (Should call connect() first)

## Buffer
• position  
• limit  
• capacity  
When writing to buffer, limit = capacity  
When reading from buffer,  limit <= capacity  