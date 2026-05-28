public class Counter {
    private int c = 0;

    public void increment() {
        c++;
    }

    public void decrement() {
        c--;
    }

    public int value() {
        return c;
    }

    public static void main(String[] args) throws InterruptedException {
        final int operations = 5_000_000;
        Counter counter = new Counter();

        Thread incrementThread = new Thread(() -> {
            for (int i = 0; i < operations; i++) {
                counter.increment();
                if ((i & 1023) == 0) {
                    Thread.yield();
                }
            }
        });

        Thread decrementThread = new Thread(() -> {
            for (int i = 0; i < operations; i++) {
                counter.decrement();
                if ((i & 1023) == 0) {
                    Thread.yield();
                }
            }
        });

        long start = System.nanoTime();
        incrementThread.start();
        decrementThread.start();

        incrementThread.join();
        decrementThread.join();
        long elapsedMs = (System.nanoTime() - start) / 1_000_000;

        System.out.println("Versao SEM synchronized");
        System.out.println("Valor final esperado: 0");
        System.out.println("Valor final obtido: " + counter.value());
        System.out.println("Tempo total (ms): " + elapsedMs);
    }
}