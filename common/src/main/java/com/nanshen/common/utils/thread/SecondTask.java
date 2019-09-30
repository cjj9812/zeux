package com.nanshen.common.utils.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 类描述：使用ReentrantLock交替打印10组ABC
 *
 * @author scnu_chennanshen*
 * @date 2019/9/28 - 13:06
 */
public class SecondTask implements Runnable{
    private String name;
    private Integer status;
    private static volatile int state=1;
    private static volatile int count=30;

    public SecondTask(String name,Integer status){
        this.name=name;
        this.status=status;
    }

    public static ReentrantLock lock=new ReentrantLock();
    public static Condition condition=lock.newCondition();

    @Override
    public void run() {
        try {
            while (count > 0) {
                lock.lock();
                while (state != status) {
                    condition.await(100, TimeUnit.MILLISECONDS);
                }
                if (count <= 0) {
                    break;
                }
                System.out.println(name);
                count--;
                state++;
                if (state == 4) {
                    state = 1;
                }
                condition.signalAll();
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }


    public static void main(String[] args) {
        Thread threadA=new Thread(new SecondTask("A",1));
        Thread threadB=new Thread(new SecondTask("B",2));
        Thread threadC=new Thread(new SecondTask("C",3));
        threadA.start();
        threadB.start();
        threadC.start();
    }
}
