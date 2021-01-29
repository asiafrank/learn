package com.asiafrank.nettydemo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import org.junit.Test;

/**
 * @author zhangxiaofan 2021/01/21-17:34
 */
public class NettyPoolArenaTest {
    @Test
    public void testPool() {
        PooledByteBufAllocator allocator = new PooledByteBufAllocator(true);
        ByteBuf buffer = allocator.buffer(8196); // 8kb + 4b
    }
}
