package com.example.service1.controller;

import com.example.service1.service.DistributedLockService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;

@RestController
@RequiredArgsConstructor
public class LockController {

    @Autowired
    private final DistributedLockService lockService;

    @PostMapping("/lock")
    public String lock() {
        String lockName = "test-lock";
        lockService.doWorkWithLock(lockName);
        return "Complete";
    }
}