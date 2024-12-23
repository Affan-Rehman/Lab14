import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LabTask3 {

  private static final CopyOnWriteArrayList<Integer> sharedList = new CopyOnWriteArrayList<>();

  public static void main(String[] args) throws InterruptedException {
    ExecutorService executorService = Executors.newFixedThreadPool(3);

    for (int i = 0; i < 3; i++) {
      executorService.submit(new ListWriter());
    }

    for (int i = 0; i < 2; i++) {
      executorService.submit(new ListReader());
    }

    executorService.shutdown();
    executorService.awaitTermination(10, TimeUnit.SECONDS);

    System.out.println("Final list size: " + sharedList.size());
  }

  static class ListWriter implements Runnable {

    @Override
    public void run() {
      for (int i = 0; i < 5; i++) {
        int number = (int) (Math.random() * 100);
        sharedList.add(number);
        System.out.println(
          Thread.currentThread().getName() + " added number: " + number
        );
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    }
  }

  static class ListReader implements Runnable {

    @Override
    public void run() {
      while (!Thread.currentThread().isInterrupted()) {
        for (Integer number : sharedList) {
          System.out.println(
            Thread.currentThread().getName() + " read number: " + number
          );
        }
        try {
          Thread.sleep(200);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    }
  }
}
