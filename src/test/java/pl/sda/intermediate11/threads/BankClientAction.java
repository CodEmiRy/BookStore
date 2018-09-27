package pl.sda.intermediate11.threads;

import org.apache.commons.lang3.RandomUtils;

public class BankClientAction implements Runnable {
    @Override
    public void run() {
        int amount = RandomUtils.nextInt(1, 20);

        Bank.withdraw(amount);

        int sleepTime = 10;

        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Bank.getBack(amount);
    }
}
