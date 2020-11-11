import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import org.junit.Test;

/**
 * @author zhangxf created at 4/18/2018.
 */
public class MainTest {
    @Test
    public void test() {
        PooledByteBufAllocator allocator = new PooledByteBufAllocator(true);
        ByteBuf buffer = allocator.buffer(112);
        buffer.release();
        allocator.buffer(112);
    }
}
