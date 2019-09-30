package com.nanshen.common.utils.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadDemo {
    private volatile static int count=0;
    private volatile static int state=1;


    public static void main(String[] args) {

        ExecutorService executorService=Executors.newCachedThreadPool();


        executorService.execute(()-> {
            while (count < 28){
                if (state == 1) {
                    System.out.println("A");
                    state = 2;
                    count++;
                }
            }
        });

        executorService.execute(()->{
            while (count < 29){
                if (state==2){
                    System.out.println("B");
                    state=3;
                    count++;

                }
            }
        });

        executorService.execute(()->{
            while (count < 30){
                if (state==3){
                    System.out.println("C");
                    state=1;
                    count++;
                }
            }
        });
    }


}
