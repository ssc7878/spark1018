package com.atguigu.juc;

public class Lambda {
    public static void main(String[] args) {
        Foo foo = (x,y)->{return x+y;};
        System.out.println(foo.add(5, 10));
    }
}
@FunctionalInterface
interface Foo{
    public int add(int a , int b );
}
