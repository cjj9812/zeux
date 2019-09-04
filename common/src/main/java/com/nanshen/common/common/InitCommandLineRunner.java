package com.nanshen.common.common;

import com.nanshen.common.config.redis.StringRedisDao;
import com.nanshen.common.utils.EhCacheUtil;
import com.nanshen.common.vo.PageVO;
import net.sf.ehcache.Element;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;


@Component
public class InitCommandLineRunner implements CommandLineRunner {


    @Autowired
    private StringRedisDao stringRedisDao;

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
        PageVO pageVO=new PageVO();
        pageVO.setNextPage(4);
        pageVO.setPrePage(2);
        Map<String,Object> map=new HashMap<>();
        map.put("pageVO",pageVO);
        map.put("list","333333333333");
        stringRedisDao.set("key2",map);
        System.out.println(stringRedisDao.get("key2"));
    }

}
