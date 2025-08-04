package multi.threading;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class G_ProducerConsumerProblem {

    // Producer consumer problem is when there is a shared queue between a producer who puts data and consumer who takes data
    // And when is lag between them if producer is faster and consumer is slower or viceversa.
    // Then we will enter into issue of q being full or q being empty
    public static void main(String[] args) throws InterruptedException {
        // BlockingQueue using put and take is one way of implementing a fix for classic producer consumer problem
        // Even if we add and poll in place of put & take. It will become and normal q and fall in same issue.
/*        BlockingQueue<Integer> sharedQueue = new LinkedBlockingQueue<>(5);

        BlockingWayProducer blockingWayProducer = new BlockingWayProducer(sharedQueue);
        BlockingWayConsumer blockingWayConsumer = new BlockingWayConsumer(sharedQueue);
        new Thread(blockingWayProducer).start();
        new Thread(blockingWayConsumer).start();*/

        // The other way is to manually use wait and notify but is a overhead for developers
        //manualWay();
/*        ManualWayBuffer manualWayBuffer = new ManualWayBuffer(5);
        new Thread(new ManualWayProducer(manualWayBuffer)).start();
        new Thread(new ManualWayConsumer(manualWayBuffer)).start();*/

        // The other way is using the semaphore
/*
        SemaphoreWayBuffer semaphoreWayBuffer = new SemaphoreWayBuffer(5);

        new Thread(new SemaphoreWayProducer(semaphoreWayBuffer)).start();
        new Thread(new SemaphoreWayConsumer(semaphoreWayBuffer)).start();;
*/


        // Beauty of this is order of producer and consumer is maintained. if we use the fair locking
        ReentrantLockBuffer reentrantLockBuffer = new ReentrantLockBuffer(5, new ReentrantLock(true));
        new Thread(new ReentrantBufferProducer(reentrantLockBuffer)).start();
        new Thread(new ReentrantBufferConsumer(reentrantLockBuffer)).start();
    }

    private static void manualWay() {
        int maxSize = 5;
        ArrayList<Integer> sharedList = new ArrayList<>(maxSize);

        produce(sharedList, maxSize);
        consume(sharedList);
    }

    private static void produce(List<Integer> sharedList, int maxSize) {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                synchronized (sharedList) {
                    try {
                        Thread.sleep(100);
                        while (sharedList.size() == maxSize) {
                            sharedList.wait();
                        }
                        sharedList.add(i);
                        System.out.println("publish to q" + i);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    sharedList.notifyAll();
                }
            }
        }).start();
    }

    private static void consume(List<Integer> sharedList) {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                synchronized (sharedList) {
                    try {
                        Thread.sleep(200);
                        while (sharedList.isEmpty()) {
                            sharedList.wait();
                        }
                        Integer remove = sharedList.remove(0);
                        System.out.println("consuemd From q " + remove);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    sharedList.notifyAll();
                }
            }
        }).start();
    }
}

class BlockingWayProducer implements Runnable {

    private BlockingQueue<Integer> sharedQueue;

    public BlockingWayProducer(BlockingQueue<Integer> sharedQueue) {
        this.sharedQueue = sharedQueue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            try {
                sharedQueue.put(i);
                System.out.println("publish to q" + i);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class BlockingWayConsumer implements Runnable {

    private BlockingQueue<Integer> sharedQueue;

    public BlockingWayConsumer(BlockingQueue<Integer> sharedQueue) {
        this.sharedQueue = sharedQueue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {

            try {
                Integer take = sharedQueue.take();
                System.out.println("consume from q" + take);

                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}

class SemaphoreWayBuffer {
    private final Semaphore emptySemaphore;
    private final Semaphore fullSemaphore;
    private final Semaphore mutexSemaphore;
    private final Queue<Integer> sharedList;


    public SemaphoreWayBuffer(int capacity) {
        emptySemaphore = new Semaphore(capacity);
        fullSemaphore = new Semaphore(0); // For semZero all acquire() calls will block and tryAcquire() calls will return false, until you do a release()
        mutexSemaphore = new Semaphore(1);
        sharedList = new LinkedList<>();
    }

    public void produce(int item) throws InterruptedException {
        emptySemaphore.acquire();
        mutexSemaphore.acquire();

        sharedList.add(item);
        System.out.println("Produced to q " + item);

        mutexSemaphore.release();
        fullSemaphore.release();
    }

    public void consume() throws InterruptedException {
        fullSemaphore.acquire();
        mutexSemaphore.acquire();

        Integer poll = sharedList.poll();
        System.out.println("Consumed from q " + poll);

        mutexSemaphore.release();
        emptySemaphore.release();
    }
}

class SemaphoreWayProducer implements Runnable {
    private SemaphoreWayBuffer buffer;

    public SemaphoreWayProducer(SemaphoreWayBuffer semaphoreWayBuffer) {
        this.buffer = semaphoreWayBuffer;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                buffer.produce(i);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class SemaphoreWayConsumer implements Runnable {
    private final SemaphoreWayBuffer semaphoreWayBuffer;

    public SemaphoreWayConsumer(SemaphoreWayBuffer semaphoreWayBuffer) {
        this.semaphoreWayBuffer = semaphoreWayBuffer;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                semaphoreWayBuffer.consume();
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class ManualWayBuffer {
    private final Queue<Integer> sharedList;
    private int mazSize;

    public ManualWayBuffer(Integer capacity) {
        this.sharedList = new LinkedList<>();
        this.mazSize = capacity;
    }

    public void produce(int item) throws InterruptedException {
        synchronized (sharedList) {
            while (sharedList.size() == mazSize) {
                sharedList.wait();
            }
            sharedList.add(item);
            System.out.println("Produced to Q " + item);
            Thread.sleep(100);

            sharedList.notifyAll();
        }
    }

    public void consume() throws InterruptedException {
        synchronized (sharedList) {
            while (sharedList.isEmpty()){
                sharedList.wait();
            }
            Integer poll = sharedList.poll();
            System.out.println("Consumed from Q " + poll);

            Thread.sleep(200);

            sharedList.notifyAll();
        }
    }
}

class ManualWayProducer implements Runnable {

    private ManualWayBuffer manualWayBuffer;

    public ManualWayProducer(ManualWayBuffer manualWayBuffer) {
        this.manualWayBuffer = manualWayBuffer;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                manualWayBuffer.produce(i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class ManualWayConsumer implements Runnable {

    private final ManualWayBuffer manualWayBuffer;

    public ManualWayConsumer(ManualWayBuffer manualWayBuffer) {
        this.manualWayBuffer = manualWayBuffer;
    }

    @Override
    public void run() {

        for (int i = 0; i < 100; i++) {
            try {
                manualWayBuffer.consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class ReentrantLockBuffer {
    List<Integer> sharedList;
    ReentrantLock reentrantLock;
    Condition removed;
    Condition added;
    int capacity;

    public ReentrantLockBuffer(int capacity, ReentrantLock reentrantLock) {
        this.capacity = capacity;
        this.sharedList = new ArrayList<>(capacity);
        this.reentrantLock = reentrantLock;
        removed = reentrantLock.newCondition();
        added = reentrantLock.newCondition();
    }

    public synchronized void produce(int i) throws InterruptedException {
        reentrantLock.lock();

        // In some rare cases thread might come of await state it is called as spurious wakeups.
        // To avoid this we should use while instead of If.
        while (sharedList.size() == capacity)
            removed.await();

        System.out.println("Produced " + i);
        sharedList.add(i);

        added.signal();
        reentrantLock.unlock();
    }

    public void consume() throws InterruptedException {
        reentrantLock.lock();

        while(sharedList.isEmpty())
            added.await();

        Integer i = sharedList.remove(0);
        System.out.println("Consumed " + i);

        removed.signal();
        reentrantLock.unlock();
    }
}

class ReentrantBufferProducer implements Runnable {

    ReentrantLockBuffer reentrantLockBuffer;

    public ReentrantBufferProducer(ReentrantLockBuffer reentrantLockBuffer) {
        this.reentrantLockBuffer = reentrantLockBuffer;
    }

    @Override
    public void run() {
        for (int i = 0; i < 500; i++) {
            try {
                reentrantLockBuffer.produce(i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


class ReentrantBufferConsumer implements Runnable {

    ReentrantLockBuffer reentrantLockBuffer;

    public ReentrantBufferConsumer(ReentrantLockBuffer reentrantLockBuffer) {
        this.reentrantLockBuffer = reentrantLockBuffer;
    }

    @Override
    public void run() {
        for (int i = 0; i < 500; i++) {
            try {
                reentrantLockBuffer.consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

