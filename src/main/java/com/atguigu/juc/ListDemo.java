package com.atguigu.juc;


import java.util.*;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class ListDemo {
    public static void main(String[] args) {
        //List<String> list = new CopyOnWriteArrayList<>();

        /*for (int i = 1; i < 50; i++) {
            new Thread(()->{ list.add(UUID.randomUUID().toString().substring(0,8));System.out.println(list); },i+"").start();
        }*/

       /* Set set = new CopyOnWriteArraySet();
        for (int i = 1; i < 30; i++) {
            new Thread(()->{ set.add(UUID.randomUUID().toString().substring(0,8));System.out.println(set); },i+"").start();
        }*/
       Map map = new ConcurrentHashMap();
        for (int i = 1; i < 30; i++) {
            new Thread(()->{map.put(Thread.currentThread().getName(),UUID.randomUUID().toString().substring(0,8));
                System.out.println(map);},""+i).start();
        }
    }
}
