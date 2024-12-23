import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class LabTask4 {

  public static void main(String[] args) throws InterruptedException {
    BankAccount account = new BankAccount(1000);

    Thread[] clients = new Thread[5];
    for (int i = 0; i < clients.length; i++) {
      clients[i] = new Thread(new BankClient(account), "Client-" + (i + 1));
      clients[i].start();
    }

    for (Thread client : clients) {
      client.join();
    }

    System.out.println("Final Balance: $" + account.getBalance());
  }
}

class BankAccount {

  private AtomicInteger balance;

  public BankAccount(int initialBalance) {
    this.balance = new AtomicInteger(initialBalance);
  }

  public boolean withdraw(int amount) {
    while (true) {
      int currentBalance = balance.get();
      if (currentBalance < amount) {
        return false;
      }
      if (balance.compareAndSet(currentBalance, currentBalance - amount)) {
        return true;
      }
    }
  }

  public void deposit(int amount) {
    balance.addAndGet(amount);
  }

  public int getBalance() {
    return balance.get();
  }
}

class BankClient implements Runnable {

  private final BankAccount account;
  private final ThreadLocalRandom random = ThreadLocalRandom.current();

  public BankClient(BankAccount account) {
    this.account = account;
  }

  @Override
  public void run() {
    for (int i = 0; i < 5; i++) {
      if (random.nextBoolean()) {
        int amount = random.nextInt(100, 501);
        account.deposit(amount);
        System.out.println(
          Thread.currentThread().getName() +
          " deposited $" +
          amount +
          ". New balance: $" +
          account.getBalance()
        );
      } else {
        int amount = random.nextInt(50, 251);
        boolean success = account.withdraw(amount);
        if (success) {
          System.out.println(
            Thread.currentThread().getName() +
            " withdrew $" +
            amount +
            ". New balance: $" +
            account.getBalance()
          );
        } else {
          System.out.println(
            Thread.currentThread().getName() +
            " failed to withdraw $" +
            amount +
            " (insufficient funds)"
          );
        }
      }

      try {
        Thread.sleep(random.nextInt(100, 300));
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        break;
      }
    }
  }
}
