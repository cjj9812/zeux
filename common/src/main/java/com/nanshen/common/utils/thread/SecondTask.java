package com.nanshen.common.utils.thread;

/**
 * 类描述：
 *
 * @author scnu_chennanshen*
 * @date 2019/9/28 - 13:06
 */
public class SecondTask implements Runnable{

    @Override
    public void run() {
        System.out.println("this is second task");
    }
}
