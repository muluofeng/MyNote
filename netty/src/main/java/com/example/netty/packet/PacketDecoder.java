package com.example.netty.packet;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 *
 * 当我们继承了 ByteToMessageDecoder 这个类之后，我们只需要实现一下 decode() 方法，
 * 这里的 in 大家可以看到，传递进来的时候就已经是 ByteBuf 类型，所以我们不再需要强转，第三个参数是 List 类型
 * ，我们通过往这个 List 里面添加解码后的结果对象，就可以自动实现结果往下一个 handler 进行传递，
 * 这样，我们就实现了解码的逻辑 handler
 * @author xiexingxing
 * @Created by 2020-07-15 13:57.
 */
public class PacketDecoder  extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out) throws Exception {

        //ByteBuf，我们使用netty  4.1.6.Final 版本，默认情况下用的是堆外内存，在 ByteBuf 这一小节中我们提到，堆外内存我们需要自行释放
        //而这里我们使用 ByteToMessageDecoder，Netty 会自动进行内存的释放，我们不用操心太多的内存管理方面的逻辑

        out.add(PacketCode.INSTANCE.decode(in));
    }
}