package pl.com.przepiora.parkiva.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class ThreadPoolTaskSchedulerRemoveAccountsConfig {
    private static final int QUANTITY_OF_THREAD_POOL = 5;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskSchedulerRemoveAccount() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler
                = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(QUANTITY_OF_THREAD_POOL);
        threadPoolTaskScheduler.setThreadNamePrefix("task-scheduler-remove-nonactive-account");
        return threadPoolTaskScheduler;
    }
}
