import static org.junit.Assert.*;

import org.junit.Test;

import java.nio.channels.SelectionKey;

/**
 * @author xuanjian
 */
public class SelectedSelectionKeySetTest {

    @Test
    public void arrayClone() {
        SelectionKey[] setA = new SelectionKey[1024];
        assertEquals(1024, setA.length);

        SelectionKey[] setB = setA.clone();
        assertEquals(1024, setB.length);

        assertNotEquals(setA, setB);
    }

    @Test
    public void powerOfTwo() {
        assertTrue(isPowerOfTwo(16));
        assertTrue(isPowerOfTwo(32));
        assertFalse(isPowerOfTwo(31));
    }

    private static boolean isPowerOfTwo(int val) {
        return (val & -val) == val;
    }


}
