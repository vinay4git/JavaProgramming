package multi.threading;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockOrder9 {
    static ReentrantLock reentrantLock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {

        // Benefit of having reentrant lock over synchrozed is ... sync is over a method or a block. But with this
        // we can acquire lock in one method and release in another method if needed.
        reentrantLock.lock();
        try {

        } finally {
            // It is suggested to keep unlock in finally. Since in case of any exception lock will be failed to release.
            reentrantLock.unlock();
        }

        new Thread(() -> {
            try {
                reentrantLock.lock();
                accessResource();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                reentrantLock.unlock();
            }
        }).start();


        accessResourceRec(1);

        // same behaviour with syncronized also .. As the same thread is trying to access the syncrized block.
        // Since it already had the lock ... recrsive call will get the access.
        accessResourceRecSyncronized(1);


        // fair lock
        // This will guarantee that most waited lock will acquire lock
        // By default locks are unfair. During the lock release if any new thread just requested for access. Chances that
        // new thread might get the lock instead of most waited thread in case of default unfair behaviour.
        // This helps in speed up process. Since there is no need to put the new thread in blocking queue. It will save some processing.
        ReentrantLock fairReentrantLock = new ReentrantLock(true);
    }

    public static void accessResource() throws InterruptedException {
        Thread.sleep(1000);
    }


    /**
     * As this method call recusively ... same threod will acquire lock
     * but in background lock is not acquired. Jut the hold count will be increased.
     */
    public static void accessResourceRec(int count) throws InterruptedException {
        reentrantLock.lock();
        System.out.println("Lock hold count " + reentrantLock.getHoldCount());
        if (count < 10)
            accessResourceRec(count+ 1);

        reentrantLock.unlock();
    }

    public static synchronized void accessResourceRecSyncronized(int count) throws InterruptedException {
        System.out.println("Syncronized count" + count);
        if (count < 10)
            accessResourceRecSyncronized(count + 1);
    }

    /**
     * tryLock will get the lock and send true if available. Else return false.
     * This helps in doing some other actions
     */
    public static void accessResoureTryLock() {
        boolean tryLock = reentrantLock.tryLock();

        if (tryLock) {
            try {
                // access resource
            } finally {
                reentrantLock.unlock();
            }

        } else {
            // do something else
        }
    }

    /**
     * tryLock with timeout is helpful if we want to wait only sometime in acquiring the lock
     * if not do something after that duration
     */
    public static void accessResourceTryLockWithoutTimeout() throws InterruptedException {
        boolean tryLockWithTimeout = reentrantLock.tryLock(5, TimeUnit.SECONDS);

        if (tryLockWithTimeout) {
            try {
                // access resource
            } finally {
                reentrantLock.unlock();
            }

        } else {
            // do something else
        }
    }

    public static void makingTryLockFair() throws InterruptedException {
        ReentrantLock fairLock = new ReentrantLock(true);

        // eventhough we are using fair lock. tryLock will not honor the fairness
        boolean tryUnfairLock = fairLock.tryLock();

        // workaround for this use tryLock with wait time
        boolean tryFairLock = fairLock.tryLock(0, TimeUnit.SECONDS);
    }
}
