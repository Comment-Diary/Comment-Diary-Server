package com.commentdiary.src.delivery.controller;

import com.commentdiary.common.response.CommonResponse;
import com.commentdiary.src.delivery.dto.DeliveryResponse;
import com.commentdiary.src.delivery.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/delivery")
public class DeliveryRestController {
    private final DeliveryService deliveryService;

    /**
     * 도착한 일기 조회
     */
    @GetMapping("")
    public CommonResponse<DeliveryResponse> getDeliveredDiary(@RequestParam(name = "date") String date) {
        DeliveryResponse result = deliveryService.getDeliveredDiary(date);
        return new CommonResponse<>(result);
    }
}
