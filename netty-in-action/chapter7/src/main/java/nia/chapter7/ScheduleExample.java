package nia.chapter7;

import io.netty.channel.Channel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 任务调度
 *
 * @author xuanjian
 */
public class ScheduleExample {

    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();

    /**
     * 使用ScheduledExecutorService调度任务 7-2
     */
    public static void schedule() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);

        ScheduledFuture<?> future = executor.schedule(() -> {
            System.out.println("60 seconds later");
        }, 60, TimeUnit.SECONDS);

        // ...

        executor.shutdown();
    }

    /**
     * 使用EventLoop调度任务 7-3
     */
    public static void scheduleViaEventLoop() {
        Channel channel = CHANNEL_FROM_SOMEWHERE;

        io.netty.util.concurrent.ScheduledFuture future = channel.eventLoop().schedule(() -> {
            System.out.println("60 seconds later");
        }, 60, TimeUnit.SECONDS);
    }

    /**
     * 使用EventLoop调度周期性任务 7-4
     */
    public static void scheduleFixedViaEventLoop() {
        Channel channel = CHANNEL_FROM_SOMEWHERE;

        io.netty.util.concurrent.ScheduledFuture future = channel.eventLoop().scheduleAtFixedRate(() -> {
            System.out.println("Run every 60 seconds");
        }, 60, 60, TimeUnit.SECONDS);
    }

    /**
     * 使用ScheduledFuture取消任务的执行 7-5
     */
    public static void cancelingTaskUsingScheduledFuture() {
        Channel channel = CHANNEL_FROM_SOMEWHERE;

        io.netty.util.concurrent.ScheduledFuture future = channel.eventLoop().scheduleAtFixedRate(() -> {
            System.out.println("Run every 60 seconds");
        }, 60, 60, TimeUnit.SECONDS);

        // Some other code that runs...

        boolean mayInterruptIfRunning = true;
        // 取消任务的执行
        future.cancel(mayInterruptIfRunning);
    }

}
