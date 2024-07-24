package de.tum.in.ase.eist;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        detectDeadlock(new SwimmingPool());
    }

    public static void detectDeadlock(SwimmingPool swimmingPool) {

        // TODO 2
        Thread thread = new Thread(() -> swimmingPool.handleEntryRequest(new Swimmer(),SwimmingPoolActionOrder.LOCKER_BEFORE_CHANGING_ROOM));
        Thread thread1 = new Thread(()-> swimmingPool.handleEntryRequest(new Swimmer(),SwimmingPoolActionOrder.CHANGING_ROOM_BEFORE_LOCKER));
        thread.start();
        thread1.start();
        try {
            thread.join();
            thread1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
