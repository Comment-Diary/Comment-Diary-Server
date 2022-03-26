//package com.commentdiary.batch.scheduler;
//
//import com.commentdiary.batch.job.JobConfig;
//import com.commentdiary.src.delivery.dto.DeliveryRequest;
//import com.commentdiary.src.delivery.repository.DeliveryRepository;
//import com.commentdiary.src.diary.domain.Diary;
//import com.commentdiary.src.diary.repository.DiaryRepository;
//import com.commentdiary.src.member.domain.Member;
//import com.commentdiary.src.member.repository.MemberRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.JobExecutionException;
//import org.springframework.batch.core.JobParameter;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class Scheduler {
//    private JobLauncher jobLauncher;
//    private JobConfig jobConfig;
//    private final DiaryRepository diaryRepository;
//    private final MemberRepository memberRepository;
//    private final DeliveryRepository deliveryRepository;
//
//    //0 0 7 1/1 * ? *
//
////    @Scheduled(cron = "0 */1 * * * *")
////    public void runJob() {
////
////        Map<String, JobParameter> confMap = new HashMap<>();
////        confMap.put("time", new JobParameter(System.currentTimeMillis()));
////        JobParameters jobParameters = new JobParameters(confMap);
////
////        try {
////            jobLauncher.run(jobConfig.simpleJob(), jobParameters);
////        }
////        catch (JobExecutionException e) {
////            System.out.println(e.getMessage());
////            e.printStackTrace();
////        }
////    }
//
//    @Scheduled(cron = "0 0/1 * 1/1 * ? *")
//    @Transactional
//    public void delivery() {
//        DeliveryRequest deliveryRequest = new DeliveryRequest();
//
//        Date today = new Date();
//        Date yesterday = new Date(today.getTime()+(1000*60*60*24*-1));
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
//
//        // 어제 일기를 찾아와서, 오늘 전달하는데, 전달 테이블에는 오늘 날짜를 저장해야 됨. 클라에서 오늘 날짜가 들어온다.
//        List<Member> members = memberRepository.findAll();
//        List<Diary> diaries = diaryRepository.findAllByDateContains(simpleDateFormat.format(yesterday));
//
//        int memberSum = members.size();
//        int diarySum = diaries.size();
//
//        System.out.println("memberSum: " + memberSum);
//        System.out.println("diarySum: " + diarySum);
//
//        int idx = 0;
//
//        for (int i = 0; i < members.size(); i++) {
//            if (members.get(i).getId() == diaries.get(idx).getMember().getId()) {
//                idx = (idx + 1) % diaries.size();
//            }
//            deliveryRepository.save(deliveryRequest.toEntity(members.get(i), diaries.get(idx), simpleDateFormat.format(today)));
//            idx = (idx + 1) % diaries.size();
//        }
//    }
//}
