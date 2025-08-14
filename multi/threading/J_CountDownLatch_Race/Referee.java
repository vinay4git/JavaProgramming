package multi.threading.J_CountDownLatch_Race;

import java.util.concurrent.CountDownLatch;

class Referee implements Runnable {
    private CountDownLatch readyLatch;
    private CountDownLatch startLatch;

    public Referee(CountDownLatch readyLatch, CountDownLatch startLatch) {
        this.readyLatch = readyLatch;
        this.startLatch = startLatch;
    }

    @Override
    public void run() {
        try {
            System.out.println("Referee: Waiting for all participants to get ready...");
            readyLatch.await();  // Wait for all participants to be ready
            System.out.println("Referee: All participants ready. On your marks... Get set... GO!");
            startLatch.countDown();  // Start the race
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}