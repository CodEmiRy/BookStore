package pl.sda.intermediate11.threads;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ThreadsExample {

    @Test
    void bankWithThreads() {
        List<BankClientAction> clientsActions = Lists.newArrayList();
        for (int i = 0; i < 1000; i++) {
            clientsActions.add(new BankClientAction());
        }

        List<Thread> threads = Lists.newArrayList();
        for (BankClientAction clientsAction : clientsActions) {
            threads.add(new Thread(clientsAction));
        }

        for (Thread thread : threads) {
            thread.start(); //uruchamiamy zadania
        }

        for (Thread thread : threads) {
            try {
                thread.join(); //czekamy na zakończenie pracy
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("ilość operacji " + Bank.operations);
        System.out.println("stan konta " + Bank.balance);
    }

    @Test
    void runnableBasics() {
        Runnable ourRunnable = new OurRunnable(); //zadanie

        Runnable anonymous = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() +
                        " Anonimowa klasa");
            }
        };

        Runnable lambdaRunnable = () -> System.out.println(Thread.currentThread().getName() +
                " Lambda");
        //inna forma zapisu tego samego

        ourRunnable.run();
        anonymous.run();
        lambdaRunnable.run();

        Thread firstThread = new Thread(ourRunnable);
        Thread secondThread = new Thread(anonymous);
        Thread thirdThread = new Thread(lambdaRunnable);

        List<Thread> threads = Lists.newArrayList(firstThread, secondThread, thirdThread);
        for (Thread thread : threads) {
            thread.start();
        }
    }
}
