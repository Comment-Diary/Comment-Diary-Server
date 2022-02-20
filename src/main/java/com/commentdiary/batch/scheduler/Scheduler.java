//package com.commentdiary.batch.scheduler;
//
//import com.commentdiary.batch.job.JobConfig;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.JobExecutionException;
//import org.springframework.batch.core.JobParameter;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class Scheduler {
//    private JobLauncher jobLauncher;
//    private JobConfig jobConfig;
//
//    @Scheduled(cron = "*/10 * * * * *")
//    public void runJob() {
//
//        Map<String, JobParameter> confMap = new HashMap<>();
//        confMap.put("time", new JobParameter(System.currentTimeMillis()));
//        JobParameters jobParameters = new JobParameters(confMap);
//
//        try {
//            jobLauncher.run(jobConfig.job(), jobParameters);
//        }
//        catch (JobExecutionException e) {
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//        }
//
//    }
//}
