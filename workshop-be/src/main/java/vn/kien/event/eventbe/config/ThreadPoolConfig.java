package vn.kien.event.eventbe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.kien.event.eventbe.base.BaseObjectLoggable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Le-Hong-Quan
 * Date: 19/02/2023
 * Time: 11:29
 */
@Configuration
public class ThreadPoolConfig extends BaseObjectLoggable {
    @Bean(name = "executorService")
    public ExecutorService createExecutorService() {
        ExecutorService executorService = new ThreadPoolExecutor(10, 3000,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                executorService.shutdown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        return executorService;
    }
}
