package pl.sda.intermediate11.threads;

public class OurRunnable implements Runnable{

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() +
                " Specjalna nowa klasa");
    }
}
