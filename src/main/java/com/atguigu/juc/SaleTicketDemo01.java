package com.atguigu.juc;

public class SaleTicketDemo01 extends Thread{
    static int tickets = 30;
    public static void main(String[] args) {

        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                while (tickets != 0) {
                    tickets -=1;
                    System.out.println(tickets);
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                while (tickets != 0) {
                    tickets -=1;
                    System.out.println(tickets);
                }
            }
        });
        Thread thread3 = new Thread(new Runnable() {
            public void run() {
                while (tickets != 0) {
                    tickets -=1;
                    System.out.println(tickets);
                }
            }
        });

        thread1.run();
        thread2.run();
        thread3.run();

    }
}
