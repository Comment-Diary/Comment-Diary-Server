package com.commentdiary.src.emailAuth.repository;

import com.commentdiary.src.emailAuth.domain.EmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailAuthRepository extends JpaRepository<EmailAuth, Long> {
    boolean existsByEmailAndCode(String email, int code);
    boolean existsByEmail(String email);
    Optional<EmailAuth> findByEmail(String email);
}