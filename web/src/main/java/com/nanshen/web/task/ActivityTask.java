//package com.nanshen.web.task;
//
//
//import com.nanshen.module.system.entity.Activity;
//import com.nanshen.module.system.service.ActivityService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Component
//public class ActivityTask {
//
//    @Autowired
//    ActivityService activityService;
//
//    private final Logger logger= LoggerFactory.getLogger(this.getClass());
//
//    @Scheduled(fixedRate=1000*60)
//    public void changeActivityStatus(){
//        logger.info("==============定时任务开始============");
//        Map map=new HashMap();
////        map.put("state",0);
//        Long timestamp=System.currentTimeMillis();
//        List<Activity> activities= activityService.findList(map);
//        for (Activity activity: activities
//             ) {
//            if(timestamp<=activity.getStartTime()){
//                activity.setState(1);
//                activityService.updateById(activity);
//            }else if(timestamp>activity.getStartTime()&& timestamp<activity.getEndTime()){
//                activity.setState(2);
//                activityService.updateById(activity);
//            }else if(timestamp>activity.getEndTime()){
//                activity.setState(3);
//                activityService.updateById(activity);
//            }
//        }
//        logger.info("==============定时任务结束============");
//
//    }
//}
