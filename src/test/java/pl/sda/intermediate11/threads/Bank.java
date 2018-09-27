package pl.sda.intermediate11.threads;

public class Bank {

    protected static int balance = 1000;

    public static int operations;

    public static synchronized void withdraw(int howMuch) {
        balance = balance - howMuch;
        System.out.println(Thread.currentThread().getName() + " Stan konta po wypłacie klienta : " + balance);
    }

    public static synchronized void getBack(int howMuch) {
        balance = balance + howMuch;
        System.out.println(Thread.currentThread().getName() + " Stan konta po wpłacie klienta : " + balance);
        operations++;
    }
}
