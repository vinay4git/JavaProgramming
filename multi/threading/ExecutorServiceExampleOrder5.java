package multi.threading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceExampleOrder5 {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            singleThreadExecutor.execute(() -> {
                System.out.println("Single thread Pool Executor Service " + (finalI +1) + " " + Thread.currentThread().getName());
            });
        }
        singleThreadExecutor.shutdown();

        Thread.sleep(5000);

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            fixedThreadPool.execute(() -> {
                System.out.println("Fixed thread Pool Executor Service " + (finalI +1) + " " + Thread.currentThread().getName());

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        fixedThreadPool.shutdown();

        Thread.sleep(5000);

        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            cachedThreadPool.execute(() -> {
                System.out.println("Cached thread Pool Executor Service " + (finalI +1) + " " + Thread.currentThread().getName());

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        cachedThreadPool.shutdown();

        Thread.sleep(5000);

        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(2);
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            scheduledThreadPool.schedule(() -> {
                System.out.println("Scheduled thread Pool Executor Service " + (finalI +1) + " " + Thread.currentThread().getName());

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, 3, TimeUnit.SECONDS);
        }
        scheduledThreadPool.shutdown();
    }
}
