package com.commentdiary.src.delivery.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeliveryStatus {
    ACTIVE("활성"),
    BLOCKED("차단");

    private String description;
}
