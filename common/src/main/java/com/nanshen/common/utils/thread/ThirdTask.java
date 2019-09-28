package com.nanshen.common.utils.thread;

/**
 * 类描述：
 *
 * @author scnu_chennanshen*
 * @date 2019/9/28 - 17:14
 */
public class ThirdTask implements Runnable {
    @Override
    public void run() {
        System.out.println("this is third task");
    }
}
