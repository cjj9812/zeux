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
    private static volatile int count=20;

    private static Object object=new Object();
   public FirstTask(String name,Integer status){
       this.name=name;
       this.status=status;
   }

    @Override
    public void run() {
       while (count>0){
           synchronized (object){
               if(status!=state){
                   try {
                       object.wait();
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
               System.out.print(this.name);
               count--;
               state++;
               if(state==4) state=1;
               object.notifyAll();
           }
       }

    }

}
