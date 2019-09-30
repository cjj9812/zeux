package com.nanshen.common.utils.thread;

/**
 * 类描述：
 *
 * @author scnu_chennanshen*
 * @date 2019/9/28 - 13:06
 */
public class FirstTask implements Runnable{

    private String name;
    private Integer status;
    private static volatile int state=1;
    private static volatile int count=30;

    private static Object object=new Object();
   public FirstTask(String name,Integer status){
       this.name=name;
       this.status=status;
   }

    @Override
    public void run() {
       while (count>0){
            synchronized (object){
                while(state!=status){
                    try {
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(name);
                setCount();
                object.notifyAll();
            }
        }

    }


    private void setCount(){
       state++;
        count--;
        if(state==4) {state=1;}
    }

    public static void main(String[] args) {
        Thread threadA=new Thread(new FirstTask("A",1));
        Thread threadB=new Thread(new FirstTask("B",2));
        Thread threadC=new Thread(new FirstTask("C",3));
        threadA.start();
        threadB.start();
        threadC.start();
    }

}
