package com.atguigu.juc;

class AirConditon{
    private int number = 0;

    public synchronized void increment() throws Exception {
        while (number!=0){
            this.wait();
        }
        number++;
        System.out.println(Thread.currentThread().getName()+"---"+number);
        this.notifyAll();
    }
    public synchronized void decrement() throws Exception {
        while(number==0){
            this.wait();
        }
        number--;
        System.out.println(Thread.currentThread().getName()+"---"+number);
        this.notifyAll();
    }
}
public class Demo {
    public static void main(String[] args) {
        AirConditon airConditon = new AirConditon();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    airConditon.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        },"A").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    airConditon.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        },"B").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    airConditon.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        },"C").start();
        new Thread(()->{
                for (int i = 0; i < 10; i++) {
                    try {
                        airConditon.decrement();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

        },"D").start();
    }
}
