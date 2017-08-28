/**
 * 
 */
package com.jeeweb.framework.core.listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author 袁进勇
 *
 */
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(ContextRefreshedEventListener.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext ac = event.getApplicationContext();
        // Spring Boot项目中，只有一个AnnotationConfigEmbeddedWebApplicationContext
        onStartup(ac);
    }

    private void onStartup(ApplicationContext ac) {
        LOG.info("========执行启动项开始========");
        long startTime = System.currentTimeMillis();

        Map<String, StartupItem> itemMap = ac.getBeansOfType(StartupItem.class);
        List<StartupItem> items = new ArrayList<>();
        for (Map.Entry<String, StartupItem> entry : itemMap.entrySet()) {
            items.add(entry.getValue());
        }
        Collections.sort(items, new Comparator<StartupItem>() {
            @Override
            public int compare(StartupItem arg0, StartupItem arg1) {
                Startup item0 = arg0.getClass().getAnnotation(Startup.class);
                Startup item1 = arg1.getClass().getAnnotation(Startup.class);
                return item0.order() - item1.order();
            }
        });

        for (StartupItem item : items) {
            String desc = item.getClass().getAnnotation(Startup.class).desc();
            LOG.info("========" + desc + "开始========");
            long invokeTime = System.currentTimeMillis();
            item.invoke(ac);
            LOG.info("========" + desc + "完成，耗时：{}ms========", (System.currentTimeMillis() - invokeTime));
        }

        LOG.info("========执行启动项完成，耗时：{}ms========", (System.currentTimeMillis() - startTime));
    }
}
