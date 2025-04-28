package com.example.service2.controller;

import com.example.service2.service.DistributedLockService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LockController {

    @Autowired
    private final DistributedLockService lockService;

    @GetMapping("/lock")
    public String lock(@RequestParam String lockName) {
        lockService.doWorkWithLock(lockName);
        return "Lock attempt completed for: " + lockName;
    }
}