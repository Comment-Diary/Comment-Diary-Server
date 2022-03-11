package com.commentdiary.src.delivery.repository;

import com.commentdiary.src.delivery.domain.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Optional<Delivery> findByReceiverIdAndDateContains(long memberId, String date);
}
