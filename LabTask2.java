public class LabTask2 {

  private static int counter = 0;

  public static void main(String[] args) throws InterruptedException {
    Thread t1 = new Thread(new CounterIncrementer(), "Thread-1");
    Thread t2 = new Thread(new CounterIncrementer(), "Thread-2");
    Thread t3 = new Thread(new CounterIncrementer(), "Thread-3");

    t1.start();
    t2.start();
    t3.start();

    t1.join();
    t2.join();
    t3.join();

    System.out.println("Final Counter Value: " + counter);
  }

  static class CounterIncrementer implements Runnable {

    @Override
    public void run() {
      for (int i = 0; i < 100; i++) {
        incrementCounter();
      }
    }

    private static synchronized void incrementCounter() {
      counter++;
      System.out.println(
        Thread.currentThread().getName() + " incremented counter to: " + counter
      );
    }
  }
}
