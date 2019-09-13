import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class description here.
 *
 * @author xuanjian
 */
public class BitTest {

    @Test
    public void doubled() {
        int normalizedCapacity = 2047;
//        normalizedCapacity --;
        normalizedCapacity |= normalizedCapacity >>> 1;
        normalizedCapacity |= normalizedCapacity >>> 2;
        normalizedCapacity |= normalizedCapacity >>> 4;
        normalizedCapacity |= normalizedCapacity >>> 8;
        normalizedCapacity |= normalizedCapacity >>> 16;
        normalizedCapacity++;
        System.out.println(normalizedCapacity);
    }

    @Test
    public void test1() {
        int a = 8;

        int b = a ^ 1;

        System.out.println(b);

        System.out.println(Integer.toBinaryString(-(1 << 9)));
    }


    @Test
    public void test2() throws Exception {
        Object[] array = new Object[1_0000_0000];

        TimeUnit.SECONDS.sleep(3);
    }

    @Test
    public void test3() {
        int a = ~1; // 按位取反

        System.out.println(Integer.toBinaryString(a));
    }

    @Test
    public void test4() {
        int a = 0; // 按位取反

        switch (a) {
            case 0:
                System.out.println("0");
                return;
            case 1:
                System.out.println("1");
                break;
            case 2:
                System.out.println("2");
                break;
        }

        System.out.println("end");
    }

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(Integer.MIN_VALUE);
    private static final int OWN_THREAD_ID = ID_GENERATOR.getAndIncrement();  // TODO

    @Test
    public void test5() {
        System.out.println(OWN_THREAD_ID);
        System.out.println(OWN_THREAD_ID);
        System.out.println(OWN_THREAD_ID);
    }

}
