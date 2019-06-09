package nia.chapter7;

import java.util.Collections;
import java.util.List;

/**
 * 7-1 在事件循环中执行任务
 *
 * @author xuanjian
 */
public class EventLoopExample {

    public static void executeTaskInEventLoop() {
        boolean terminated = false;

        while (!terminated) {
            // 阻塞，直到事件就绪
            List<Runnable> readyEvents = blockUntilEventReady();
            for (Runnable event : readyEvents) {
                event.run();
            }
        }

    }

    private static List<Runnable> blockUntilEventReady() {
        return Collections.singletonList(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

}
