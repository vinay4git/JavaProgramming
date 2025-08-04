package multi.threading;

public class B_VisibilityProblem {
    public static void main(String[] args) {
        SharedData sharedData = new SharedData();

        // Here in this example both t1 and t2 trying to access sharedData object. t2 is kept waiting untill shared Flag has become
        // true. Even after making it true in t1. t2 could exit while loop. This is called visibility problem
        // Since CPUs first loads the required objects in to cache and registers of respective threads. So here t1 and t2 loaded
        // the copy of sharedObject in the Caches&Registers. So even after t1 updating the flag is not reflected in the registers of t2.
        // So t2 won't get the true value.
        // Fix is to make the shared flag as volatile. Volatile suggests not to load into cache&Registers. Direct heap variable will be read and updated.
        // private volatile boolean sharedFlag = false;
        // And the other way is to make the getters synchronized
        Runnable r1 = () -> {
            System.out.println("t1 Started");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            sharedData.setSharedFlag(true);
            System.out.println("t1 Ended");
        };
        Thread t1 = new Thread(r1, "r1");
        t1.start();


        Runnable r2 = () -> {
            System.out.println("t2 Started");
            while (!sharedData.isSharedFlag()) {

            }
            System.out.println("t2 Ended");
        };
        Thread t2 = new Thread(r2, "r2");
        t2.start();
    }
}

class SharedData {
    private boolean sharedFlag = false;

    public synchronized boolean isSharedFlag() {
        return sharedFlag;
    }

    public void setSharedFlag(boolean sharedFlag) {
        this.sharedFlag = sharedFlag;
    }
}