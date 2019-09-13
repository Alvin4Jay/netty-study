/**
 * @author xuanjian
 */
public class OverrideTest {


    private static class A {

        public Number say() {
            return null;
        }

    }

    private static class B extends A {
        public Integer say() {
            return (Integer) super.say();
        }
    }


}
