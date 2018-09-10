##buffer
  通道是I/O传输发生时通过的入口，而缓冲区是这些数据传输的来源或目标。对于离开缓冲区的传输，您想传递出去的数据被置于一个缓冲区，被传送到通道。
  对于传回缓冲区的传输，
一个通道将数据放置在您所提供的缓冲区中。这种在协同对象（通常是您所写的对象以及一到多个Channel对象）之间进行的缓冲区数据传递是高效数据处理的关键
                                                                Buffer
               CharBuffer IntBuffer        DoubleBuffer         ShortBuffer     LongBuffer    FloatBuffer   ByteBuffer
                                                                                                            MappedByteBuffer
##channel
