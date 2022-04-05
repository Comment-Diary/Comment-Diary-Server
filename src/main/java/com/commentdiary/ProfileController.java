package com.commentdiary;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/health")
public class ProfileController {
    @GetMapping("")
    public String checkHealth() {
        return "0406!";
    }
}
