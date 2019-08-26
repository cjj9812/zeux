package com.nanshen.common.common;

import com.nanshen.common.utils.EhCacheUtil;
import net.sf.ehcache.Element;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.ParseException;




@Component
public class InitCommandLineRunner implements CommandLineRunner {


    /**
     * 项目启动时初始化操作
     * @param var1
     * @throws ParseException
     */
    @Override
    public void run(String... var1){
        System.out.println("初始化...");
        EhCacheUtil.getDictCache().put(new Element("key1","111111111"));
        System.out.println(EhCacheUtil.getDictCache().get("key1").getObjectKey());
    }

}
