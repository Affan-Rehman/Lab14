public class LabTask1 {

  public LabTask1() {
    // Default constructor
  }

  public static void main(String[] args) {
    Thread numberThread = new Thread(new NumberPrinter());
    Thread squareThread = new Thread(new SquarePrinter());

    numberThread.start();
    squareThread.start();
  }
}

class NumberPrinter implements Runnable {

  @Override
  public void run() {
    try {
      System.out.println("Number Thread Started");
      for (int i = 1; i <= 10; i++) {
        System.out.println("Number: " + i);
        Thread.sleep(500);
      }
    } catch (InterruptedException e) {
      System.out.println("Number Thread interrupted");
    }
  }
}

class SquarePrinter implements Runnable {

  @Override
  public void run() {
    try {
      System.out.println("Square Thread Started");
      for (int i = 1; i <= 10; i++) {
        System.out.println("Square: " + (i * i));
        Thread.sleep(500);
      }
    } catch (InterruptedException e) {
      System.out.println("Square Thread interrupted");
    }
  }
}
