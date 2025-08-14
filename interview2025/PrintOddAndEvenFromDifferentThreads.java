package interview2025;

class PrintOddAndEvenFromDifferentThreads {
    public static void main(String[] args) {
        Num num = new Num(1);

        Thread oddThread = new Thread(new Odd(num));
        oddThread.start();
        Thread evenThread = new Thread(new Even(num));
        evenThread.start();
    }
}

class Even implements Runnable {
    Num num;

    public Even(Num num) {
        this.num = num;
    }

    @Override
    public void run() {
        while (num.number <= 100) {
            synchronized (num) {
                while (num.number %2 != 0) {
                    try {
                        num.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("Print from Even Thread " + num.number);
                num.number++;
                num.notify();
            }
        }
    }
}

class Odd implements Runnable {
    Num num;

    public Odd(Num num) {
        this.num = num;
    }

    @Override
    public void run() {
        while (num.number <= 100 ) {
            synchronized (num) {
                while (num.number %2 == 0) {
                    try {
                        num.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("Print from Odd Thread " + num.number);
                num.number++;
                num.notify();
            }
        }
    }
}

class Num {
    public int number;

    public Num(int number) {
        this.number = number;
    }
}